package com.teester.mapquestions;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

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

		// Start the location service
        Intent intent = new Intent(this, LocationService.class);
        startService(intent);
    }

	@Override
	protected void onResume() {
		super.onResume();
		this.sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		this.loggedIn = sharedPreferences.getBoolean(LOGGED_IN_PREF, false);

		if (loggedIn == true) {
			this.textView.setText(getResources().getString(R.string.logged_in_as));
			this.button.setText(getResources().getString(R.string.log_out));
		} else {
			this.textView.setText(getResources().getString(R.string.not_logged_in));
			this.button.setText(getResources().getString(R.string.authorise_openstreetmap));
		}
	}

	@Override
	public void onClick(View view) {
		if (view == findViewById(R.id.button)) {
			SharedPreferences.Editor editor = sharedPreferences.edit();
			editor.putString("oauth_verifier", "");
			editor.putString("oauth_token", "");
			editor.putString("oauth_token_secret", "");
			editor.apply();

			if (loggedIn == true) {
				editor.putBoolean(LOGGED_IN_PREF, false);
				editor.apply();
			} else {
				OAuth oAuth = new OAuth(getApplicationContext());
				oAuth.execute();
			}
		}
	}
}
