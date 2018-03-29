package com.teester.whatsnearby.main;

import com.teester.whatsnearby.R;
import com.teester.whatsnearby.data.PreferenceList;
import com.teester.whatsnearby.data.source.SourceContract;

import java.util.Locale;

public class DebugPresenter implements MainActivityContract.DebugPresenter {

	private MainActivityContract.DebugView view;
	private SourceContract.Preferences preferences;

    DebugPresenter(MainActivityContract.DebugView view, SourceContract.Preferences preferences) {
		this.view = view;
		this.preferences = preferences;
	}

	@Override
	public void getDetails() {
		getLastQuery();
		getCheckDistance();
		getLastNotificationTime();
		getLastQueryTime();
		getQueryDistance();
		getAccuracy();
		getLocation();
	}

	private void getQueryDistance() {
		float preference = preferences.getFloatPreference(PreferenceList.DISTANCE_TO_LAST_LOCATION);
		String querydistance = String.format(Locale.getDefault(), "%.0fm", preference);
		int color = R.color.green;
		if (preference < 20) {
			color = R.color.red;
		}
		view.setQuerydistance(querydistance, color);
	}

	private void getLastQueryTime() {
		long lastQueryTime = preferences.getLongPreference(PreferenceList.LAST_QUERY_TIME);
		long ago = (System.currentTimeMillis() - lastQueryTime) / 60000;
		String lastQueryTime2 = String.format(Locale.getDefault(), "%d mins ago", ago);
		int color = R.color.green;
		if (ago < 60) {
			color = R.color.red;
		}
		view.setLastQueryTime(lastQueryTime2, color);
	}

	private void getLastNotificationTime() {
		long lastNotificationTime = preferences.getLongPreference(PreferenceList.LAST_NOTIFICATION_TIME);
		long ago = (System.currentTimeMillis() - lastNotificationTime) / 60000;
		int color = R.color.green;
		if (ago < 60) {
			color = R.color.red;
		}
		String lastNotificationTimeString = String.format(Locale.getDefault(), "%d mins ago", ago);
		view.setLastNotificationTime(lastNotificationTimeString, color);
	}

	private void getCheckDistance() {
		float preference = preferences.getFloatPreference(PreferenceList.DISTANCE_TO_LAST_QUERY);
		String checkdistance = String.format(Locale.getDefault(), "%.0fm", preference);
		int color = R.color.green;
		if (preference > 20) {
			color = R.color.red;
		}
		view.setCheckdistance(checkdistance, color);
	}

	private void getLastQuery() {
		String lastQuery = preferences.getStringPreference(PreferenceList.LAST_QUERY);
		view.setLastQuery(lastQuery);
	}

	private void getAccuracy() {
		float accuracy = preferences.getFloatPreference(PreferenceList.LOCATION_ACCURACY);
		String provider = preferences.getStringPreference(PreferenceList.LOCATION_PROVIDER);
		String accuracyString = String.format(Locale.getDefault(), "%s, %.0fm", provider, accuracy);
		int accuracyColor = R.color.green;
		if (accuracy > 50) {
			accuracyColor = R.color.red;
		}
		view.setAccuracy(accuracyString, accuracyColor);
	}

    private void getLocation() {
		double latitude = preferences.getDoublePreference(PreferenceList.LATITUDE);
		double longitude = preferences.getDoublePreference(PreferenceList.LONGITUDE);
		view.setLocation(latitude, longitude);
	}
}
