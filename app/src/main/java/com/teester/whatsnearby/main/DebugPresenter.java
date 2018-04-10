package com.teester.whatsnearby.main;

import com.teester.whatsnearby.R;
import com.teester.whatsnearby.data.PreferenceList;
import com.teester.whatsnearby.data.source.SourceContract;

import java.text.SimpleDateFormat;
import java.util.Date;
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
		getLocation();
		getReason();
	}

	private void getQueryDistance() {
		float preference = preferences.getFloatPreference(PreferenceList.DISTANCE_TO_LAST_QUERY);
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
		float preference = preferences.getFloatPreference(PreferenceList.DISTANCE_TO_LAST_LOCATION);
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

    private void getLocation() {
		double latitude = preferences.getDoublePreference(PreferenceList.LATITUDE);
		double longitude = preferences.getDoublePreference(PreferenceList.LONGITUDE);
	    float accuracy = preferences.getFloatPreference(PreferenceList.LOCATION_ACCURACY);
	    long time = preferences.getLongPreference(PreferenceList.LAST_LOCATION_TIME);
	    Date date = new Date(time);
	    SimpleDateFormat dateFormat = new SimpleDateFormat("d MMM HH:mm", Locale.getDefault());
	    String dateString = dateFormat.format(date);
	    String location = String.format(Locale.getDefault(), "%f, %f ± %.0fm at %s", latitude, longitude, accuracy, dateString);
	    view.setLocation(location);
	}

	private void getReason() {
		String reason = preferences.getStringPreference(PreferenceList.NOT_QUERY_REASON);
		view.setReason(reason);
	}
}
