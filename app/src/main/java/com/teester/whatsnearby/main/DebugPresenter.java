package com.teester.whatsnearby.main;

import com.teester.whatsnearby.R;
import com.teester.whatsnearby.data.source.SourceContract;

import java.util.Locale;

public class DebugPresenter implements MainActivityContract.DebugPresenter {

	private MainActivityContract.DebugView view;
	private SourceContract.Preferences preferences;

	public DebugPresenter(MainActivityContract.DebugView view, SourceContract.Preferences preferences) {
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
		float preference = preferences.getFloatPreference("distance_to_last_location");
		String querydistance = String.format(Locale.getDefault(), "%.0fm", preference);
		int color = R.color.green;
		if (preference < 20) {
			color = R.color.red;
		}
		view.setQuerydistance(querydistance, color);
	}

	private void getLastQueryTime() {
		long lastQueryTime = preferences.getLongPreference("last_query_time");
		long ago = (System.currentTimeMillis() - lastQueryTime) / 60000;
		String lastQueryTime2 = String.format(Locale.getDefault(), "%d mins ago", ago);
		int color = R.color.green;
		if (ago < 60) {
			color = R.color.red;
		}
		view.setLastQueryTime(lastQueryTime2, color);
	}

	private void getLastNotificationTime() {
		long lastNotificationTime = preferences.getLongPreference("last_notification_time");
		long ago = (System.currentTimeMillis() - lastNotificationTime) / 60000;
		int color = R.color.green;
		if (ago < 60) {
			color = R.color.red;
		}
		String lastNotificationTimeString = String.format(Locale.getDefault(), "%d mins ago", ago);
		view.setLastNotificationTime(lastNotificationTimeString, color);
	}

	private void getCheckDistance() {
		float preference = preferences.getFloatPreference("distance_to_last_query");
		String checkdistance = String.format(Locale.getDefault(), "%.0fm", preference);
		int color = R.color.green;
		if (preference > 20) {
			color = R.color.red;
		}
		view.setCheckdistance(checkdistance, color);
	}

	private void getLastQuery() {
		String lastQuery = preferences.getStringPreference("last_query");
		view.setLastQuery(lastQuery);
	}

	private void getAccuracy() {
		float accuracy = preferences.getFloatPreference("location_accuracy");
		String accuracyString = String.format(Locale.getDefault(), "%.0fm", accuracy);
		int accuracyColor = R.color.green;
		if (accuracy > 50) {
			accuracyColor = R.color.red;
		}
		view.setAccuracy(accuracyString, accuracyColor);
	}

	public void getLocation() {
		double latitude = preferences.getDoublePreference("latitude");
		double longitude = preferences.getDoublePreference("longitude");
		view.setLocation(latitude, longitude);
	}
}
