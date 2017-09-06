package com.teester.mapquestions;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

	private static final String TAG = MainActivity.class.getSimpleName();

	private TextView textView;
	private Button button;
	private SharedPreferences sharedPreferences;

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
	protected void onStart() {
		super.onStart();

		this.sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		String token = sharedPreferences.getString("oauth_token", "");

		if (token == "") {
			logIn();
		} else {
			logOut();
		}

	}

	void logIn() {
		this.textView.setText(getResources().getString(R.string.not_logged_in));
		this.button.setText(getResources().getString(R.string.authorise_openstreetmap));

		this.button.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				SharedPreferences.Editor editor = sharedPreferences.edit();
				editor.putString("oauth_verifier", "");
				editor.putString("oauth_token", "");
				editor.putString("oauth_token_secret", "");
				editor.apply();

				OAuth oAuth = new OAuth(getApplicationContext());
				oAuth.execute();
			}
		});
	}

	void logOut() {
		this.textView.setText(getResources().getString(R.string.logged_in_as));
		this.button.setText(getResources().getString(R.string.log_out));

		this.button.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				SharedPreferences.Editor editor = sharedPreferences.edit();
				editor.putString("oauth_verifier", "");
				editor.putString("oauth_token", "");
				editor.putString("oauth_token_secret", "");
				editor.apply();
			}
		});
	}
}
