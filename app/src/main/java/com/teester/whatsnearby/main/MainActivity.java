package com.teester.whatsnearby.main;

import android.Manifest;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.teester.whatsnearby.BuildConfig;
import com.teester.whatsnearby.R;
import com.teester.whatsnearby.data.PreferenceList;
import com.teester.whatsnearby.data.location.LocationJobService;
import com.teester.whatsnearby.data.source.OAuth;
import com.teester.whatsnearby.data.source.Preferences;
import com.teester.whatsnearby.data.source.SourceContract;

import java.net.URI;
import java.net.URISyntaxException;

public class MainActivity extends AppCompatActivity implements
		View.OnClickListener,
		SharedPreferences.OnSharedPreferenceChangeListener,
		MainActivityContract.View {

	private TextView textView;
	private Button button;
	private MenuItem debugMenuItem;
	private SharedPreferences sharedPreferences;
	private MainActivityContract.Presenter mainPresenter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		SourceContract.Preferences preferences = new Preferences(getApplicationContext());
		mainPresenter = new MainActivityPresenter(this, preferences);
		this.textView = this.findViewById(R.id.textView);
		this.button = this.findViewById(R.id.button);
		Toolbar toolbar = findViewById(R.id.toolbar);

		setSupportActionBar(toolbar);

		this.button.setOnClickListener(this);
		checkPermission();
		mainPresenter.showIfLoggedIn();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main_menu, menu);

		this.debugMenuItem = menu.findItem(R.id.action_debug_mode);

		if (!BuildConfig.DEBUG) {
			this.debugMenuItem.setVisible(false);
		}
		toggleDebugMode(sharedPreferences.getBoolean(PreferenceList.DEBUG_MODE, false));

		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.action_see_debug_data:
				Fragment fragment = new FragmentDebug();
				getSupportFragmentManager()
						.beginTransaction()
						.replace(R.id.activity_main, fragment)
						.addToBackStack("debug")
						.commit();
				return true;
			case R.id.action_debug_mode:
				mainPresenter.toggleDebugMode();
				return true;
			case R.id.action_about:
				Fragment aboutFragment = new FragmentAbout();
				getSupportFragmentManager()
						.beginTransaction()
						.replace(R.id.activity_main, aboutFragment)
						.addToBackStack("debug")
						.commit();
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}

	@Override
	protected void onNewIntent(Intent intent) {
		System.out.println("In MainActivity.onNewIntent");
		super.onNewIntent(intent);
		URI url = null;
		try {
			if (intent.getData() != null) {
				url = new URI(intent.getData().toString());
				mainPresenter.checkIfOauth(url);
			}
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}

	}

	private void checkPermission() {
		if (ActivityCompat.checkSelfPermission(getApplicationContext(),
				Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
				ActivityCompat.requestPermissions(
						this,
						new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
						0);
			} else {
				startLocationService();
			}
		} else {
			startLocationService();
		}
	}

	private void startLocationService() {

		JobScheduler jobScheduler = (JobScheduler) getApplicationContext().getSystemService(Context.JOB_SCHEDULER_SERVICE);
		ComponentName jobService = new ComponentName(getApplicationContext().getPackageName(), LocationJobService.class.getName());
		JobInfo jobInfo = new JobInfo.Builder(1, jobService)
				.setPeriodic(60000)
				.build();
		jobScheduler.schedule(jobInfo);
	}

	@Override
	public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
		JobScheduler jobScheduler = (JobScheduler) getApplicationContext().getSystemService(Context.JOB_SCHEDULER_SERVICE);
		ComponentName jobService = new ComponentName(getApplicationContext().getPackageName(), LocationJobService.class.getName());
		JobInfo jobInfo = new JobInfo.Builder(1, jobService)
				.setPeriodic(60000)
				.build();
		jobScheduler.schedule(jobInfo);
	}

	@Override
	protected void onResume() {
		super.onResume();
		sharedPreferences.registerOnSharedPreferenceChangeListener(this);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	@Override
	protected void onPause() {
		super.onPause();
		sharedPreferences.unregisterOnSharedPreferenceChangeListener(this);
	}

	@Override
	public void onClick(View view) {
		if (view == findViewById(R.id.button)) {
			mainPresenter.onButtonClicked();
		}
	}

	@Override
	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
		if (s.equals(PreferenceList.LOGGED_IN_TO_OSM)) {
			mainPresenter.showIfLoggedIn();
		}
		if (PreferenceList.DEBUG_MODE.equals(s)) {
			toggleDebugMode(sharedPreferences.getBoolean(PreferenceList.DEBUG_MODE, false));
		}
	}

	public void toggleDebugMode(boolean state) {
		if (state) {
			this.debugMenuItem.setTitle("Debug Mode is on");
		} else {
			this.debugMenuItem.setTitle("Debug Mode is off");
		}
	}

	@Override
	public void showIfLoggedIn(int messageStringId, int buttonStringId) {
		textView.setText(getString(messageStringId));
		button.setText(getString(buttonStringId));
	}

	@Override
	public void startOAuth() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				new OAuth(getApplicationContext());
			}
		}).start();
	}
}
