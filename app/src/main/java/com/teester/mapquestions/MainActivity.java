package com.teester.mapquestions;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, SharedPreferences.OnSharedPreferenceChangeListener {

	private static final String TAG = MainActivity.class.getSimpleName();

	private TextView textView;
	private Button button;
	private SharedPreferences sharedPreferences;

	private boolean loggedIn;
	private final String LOGGED_IN_PREF = "logged_in_to_osm";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		this.textView = (TextView) this.findViewById(R.id.textView);
		this.button = (Button) this.findViewById(R.id.button);

		this.button.setOnClickListener(this);
		this.sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

		Intent intent = new Intent(this, LocationService.class);
        startService(intent);
    }

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		setIntent(intent);
	}

	@Override
	protected void onResume() {
		super.onResume();
		sharedPreferences.registerOnSharedPreferenceChangeListener(this);

		Intent intent = getIntent();
		Uri uri = intent.getData();
		if (uri != null) {
			SharedPreferences.Editor editor = this.sharedPreferences.edit();
			editor.putString("oauth_verifier", uri.getQueryParameter("oauth_verifier"));
			editor.putString("oauth_token", uri.getQueryParameter("oauth_token"));
			editor.apply();

			OAuth oAuth = new OAuth(getApplicationContext());
			oAuth.execute();
		}
	}

	@Override
	protected void onPause() {
		super.onPause();
		sharedPreferences.unregisterOnSharedPreferenceChangeListener(this);
	}

	@Override
	public void onClick(View view) {
		if (view == findViewById(R.id.button)) {
			SharedPreferences.Editor editor = sharedPreferences.edit();
			editor.putString("oauth_verifier", "");
			editor.putString("oauth_token", "");
			editor.putString("oauth_token_secret", "");
			editor.apply();

			if (sharedPreferences.getBoolean(LOGGED_IN_PREF, false) == true) {
				editor.putBoolean(LOGGED_IN_PREF, false);
				editor.apply();
			} else {
				OAuth oAuth = new OAuth(getApplicationContext());
				oAuth.execute();
			}
		}
	}

	@Override
	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
		if (s == LOGGED_IN_PREF) {
			if (sharedPreferences.getBoolean(LOGGED_IN_PREF, false) == true) {
				this.textView.setText(getResources().getString(R.string.logged_in_as));
				this.button.setText(getResources().getString(R.string.log_out));
			} else {
				this.textView.setText(getResources().getString(R.string.not_logged_in));
				this.button.setText(getResources().getString(R.string.authorise_openstreetmap));
			}
		}
	}
}
