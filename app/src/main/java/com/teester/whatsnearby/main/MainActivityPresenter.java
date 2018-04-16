package com.teester.whatsnearby.main;

import com.teester.whatsnearby.R;
import com.teester.whatsnearby.Utilities;
import com.teester.whatsnearby.data.PoiList;
import com.teester.whatsnearby.data.PreferenceList;
import com.teester.whatsnearby.data.source.SourceContract;
import com.teester.whatsnearby.data.source.UploadToOSM;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.util.List;
import java.util.Map;

/**
 * Presenter for MainActivity
 */

public class MainActivityPresenter implements MainActivityContract.Presenter {

	private SourceContract.Preferences preferences;
	private MainActivityContract.View view;

	MainActivityPresenter(MainActivityContract.View view, SourceContract.Preferences preferences) {
		this.view = view;
		this.preferences = preferences;
	}

	/**
	 * Decide on text to be displayed based in the logged_in_to_osm preference
	 */
	@Override
	public void showIfLoggedIn() {
		boolean loggedIn = preferences.getBooleanPreference(PreferenceList.LOGGED_IN_TO_OSM);
		int message;
		int button;
		String userName = getUserName();
		if (loggedIn) {
			message = R.string.logged_in_as;
			button = R.string.log_out;
		} else {
			message = R.string.not_logged_in;
			button = R.string.authorise_openstreetmap;
		}
		view.showIfLoggedIn(message, button, userName);
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
				String verifier = list.get(PreferenceList.OAUTH_VERIFIER).get(0);
				String token = list.get(PreferenceList.OAUTH_TOKEN).get(0);
				preferences.setStringPreference(PreferenceList.OAUTH_VERIFIER, verifier);
				preferences.setStringPreference(PreferenceList.OAUTH_TOKEN, token);
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
		preferences.setStringPreference(PreferenceList.OAUTH_VERIFIER, "");
		preferences.setStringPreference(PreferenceList.OAUTH_TOKEN, "");
		preferences.setStringPreference(PreferenceList.OAUTH_TOKEN_SECRET, "");

		if (preferences.getBooleanPreference(PreferenceList.LOGGED_IN_TO_OSM)) {
			preferences.setBooleanPreference(PreferenceList.LOGGED_IN_TO_OSM, false);
		} else {
			view.startOAuth();
		}
		showIfLoggedIn();
	}

	@Override
	public void toggleDebugMode() {
		String preference = PreferenceList.DEBUG_MODE;
		boolean debug = preferences.getBooleanPreference(preference);
		if (debug) {
			preferences.setBooleanPreference(preference, false);
		} else {
			preferences.setBooleanPreference(preference, true);
		}
	}

	@Override
	public String getUserName() {
		boolean loggedIn = preferences.getBooleanPreference(PreferenceList.LOGGED_IN_TO_OSM);
		if (loggedIn) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					SourceContract.upload upload = new UploadToOSM(preferences);
					upload.setUsername();
				}
			}).start();
		}

		String preference = preferences.getStringPreference(PreferenceList.OSM_USER_NAME);
		return preference;
	}

	@Override
	public void savePoiList() {
		String json = PoiList.getInstance().serializePoiList();
		preferences.setStringPreference(PreferenceList.POILIST, json);
	}

	@Override
	public void restorePoiList() {
		String json = preferences.getStringPreference(PreferenceList.POILIST);
		if (json != null) {
			PoiList.getInstance().decodePoiList(json);
		}
	}
}
