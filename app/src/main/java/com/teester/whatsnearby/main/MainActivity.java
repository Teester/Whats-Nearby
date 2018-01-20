package com.teester.whatsnearby.main;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.teester.whatsnearby.R;
import com.teester.whatsnearby.data.location.LocationService;
import com.teester.whatsnearby.data.source.OAuth;
import com.teester.whatsnearby.data.source.Preferences;
import com.teester.whatsnearby.data.source.SourceContract;

import java.net.URI;
import java.net.URISyntaxException;

public class MainActivity extends AppCompatActivity implements
		View.OnClickListener,
		SharedPreferences.OnSharedPreferenceChangeListener,
		MainActivityContract.View {

	private static final String TAG = MainActivity.class.getSimpleName();
	private final String LOGGED_IN_PREF = "logged_in_to_osm";

	private TextView textView;
	private Button button;
	private SharedPreferences sharedPreferences;
	private MainActivityContract.Presenter mainPresenter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		SourceContract.Preferences preferences = new Preferences(getApplicationContext());
		mainPresenter = new MainActivityPresenter(this, preferences);
		mainPresenter.init();
		this.textView = this.findViewById(R.id.textView);
		this.button = this.findViewById(R.id.button);

		this.button.setOnClickListener(this);
		checkPermission();
    }

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		URI url = null;
		try {
			url = new URI(intent.getData().toString());
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		mainPresenter.checkIfOauth(url);
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
		Intent intent = new Intent(this, LocationService.class);
		startService(intent);
	}

	@Override
	public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
		Intent intent = new Intent(this, LocationService.class);
		startService(intent);
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
		if (s == LOGGED_IN_PREF) {
			mainPresenter.showIfLoggedIn();
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

	@Override
	public void setPresenter(MainActivityContract.Presenter presenter) {
		mainPresenter = presenter;
	}
}
