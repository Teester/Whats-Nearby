package com.teester.whatsnearby.main;

import com.teester.whatsnearby.R;
import com.teester.whatsnearby.Utilities;
import com.teester.whatsnearby.data.source.SourceContract;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.util.List;
import java.util.Map;

/**
 * Presenter for MainActivity
 */

public class MainActivityPresenter implements MainActivityContract.Presenter {

	private static final String TAG = MainActivityPresenter.class.getSimpleName();

	private SourceContract.Preferences preferences;
	private MainActivityContract.View view;

	public MainActivityPresenter(MainActivityContract.View view, SourceContract.Preferences preferences) {
		this.view = view;
		this.preferences = preferences;
		//this.view.setPresenter(this);
	}

	@Override
	public void init() {
	}

	@Override
	public void destroy() {
	}

	/**
	 * Decide on text to be displayed based in the logged_in_to_osm preference
	 */
	@Override
	public void showIfLoggedIn() {
		boolean logged_in = preferences.getBooleanPreference("logged_in_to_osm");
		int message;
		int button;
		if (logged_in == true) {
			message = R.string.logged_in_as;
			button = R.string.log_out;
		} else {
			message = R.string.not_logged_in;
			button = R.string.authorise_openstreetmap;
		}
		view.showIfLoggedIn(message, button);
	}

	/**
	 * Continue oAuth flow if we got back a uri in the intent
	 *
	 * @param uri The uri obtained from openstreetmap
	 */
	@Override
	public void checkIfOauth(URI uri) {
		if (uri != null) {
			try {
				Map<String, List<String>> list = Utilities.splitQuery(uri);
				String verifier = list.get("oauth_verifier").get(0);
				String token = list.get("oauth_token").get(0);
				preferences.setStringPreference("oauth_verifier", verifier);
				preferences.setStringPreference("oauth_token", token);
				view.startOAuth();

			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * When the authorise or log out button is clicked, clear the oauth preferences
	 * then either do nothing if logging out or start oAuth if logging in.
	 */
	public void onButtonClicked() {
		preferences.setStringPreference("oauth_verifier", "");
		preferences.setStringPreference("oauth_token", "");
		preferences.setStringPreference("oauth_token_secret", "");

		if (preferences.getBooleanPreference("logged_in_to_osm") == true) {
			preferences.setBooleanPreference("logged_in_to_osm", false);
		} else {
			view.startOAuth();
		}
		showIfLoggedIn();
	}
}
