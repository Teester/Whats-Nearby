package com.teester.whatsnearby.data.location;

import android.content.Context;
import android.location.Location;

import com.teester.whatsnearby.BuildConfig;
import com.teester.whatsnearby.data.PreferenceList;
import com.teester.whatsnearby.data.source.SourceContract;

public class LocationJobPresenter implements LocationJobServiceContract.Presenter {

	private static final int MINQUERYINTERVAL = 60 * 60 * 1000;
	private static final double MINQUERYDISTANCE = 20;
	private static final int MINLOCATIONACCURACY = 100;

	private Location lastLocation;
	private Location lastQueryLocation;

	private Context context;
	private LocationJobServiceContract.Receiver receiver;
	private SourceContract.Preferences preferences;

	public LocationJobPresenter(Context context, LocationJobServiceContract.Receiver service, SourceContract.Preferences preferences) {
		this.context = context;
		this.receiver = service;
		this.preferences = preferences;

		double lastLatitude = preferences.getDoublePreference(PreferenceList.LAST_LOCATION_LATITUDE);
		double lastLongitude = preferences.getDoublePreference(PreferenceList.LAST_LOCATION_LONGITUDE);
		lastLocation = new Location("dummyprovider");
		lastLocation.setLatitude(lastLatitude);
		lastLocation.setLongitude(lastLongitude);

		double lastQueryLatitude = preferences.getDoublePreference(PreferenceList.LAST_QUERY_LOCATION_LATITUDE);
		double lastQueryLongitude = preferences.getDoublePreference(PreferenceList.LAST_QUERY_LOCATION_LONGITUDE);
		lastQueryLocation = new Location("dummyprovider");
		lastQueryLocation.setLatitude(lastLatitude);
		lastQueryLocation.setLongitude(lastLongitude);
	}

	@Override
	public void processLocation(Location location) {
		if (lastLocation == null) {
			lastLocation = location;
		}
		if (lastQueryLocation == null) {
			lastQueryLocation = location;
		}

		preferences.setFloatPreference(PreferenceList.LOCATION_ACCURACY, location.getAccuracy());
		preferences.setFloatPreference(PreferenceList.DISTANCE_TO_LAST_QUERY, location.distanceTo(lastQueryLocation));
		preferences.setLongPreference(PreferenceList.QUERY_INTERVAL, System.currentTimeMillis() - preferences.getLongPreference(PreferenceList.LAST_QUERY_TIME));
		preferences.setFloatPreference(PreferenceList.DISTANCE_TO_LAST_LOCATION, location.distanceTo(lastLocation));
		preferences.setDoublePreference(PreferenceList.LATITUDE, location.getLatitude());
		preferences.setDoublePreference(PreferenceList.LONGITUDE, location.getLongitude());

		if (decideWhetherToQuery(location)) {
			lastQueryLocation = location;
			preferences.setDoublePreference(PreferenceList.LAST_QUERY_LOCATION_LATITUDE, location.getLatitude());
			preferences.setDoublePreference(PreferenceList.LAST_QUERY_LOCATION_LONGITUDE, location.getLongitude());
			receiver.performOverpassQuery(context, location);
		}
		lastLocation = location;
		preferences.setDoublePreference(PreferenceList.LAST_LOCATION_LATITUDE, location.getLatitude());
		preferences.setDoublePreference(PreferenceList.LAST_LOCATION_LONGITUDE, location.getLongitude());
	}

	private boolean decideWhetherToQuery(Location location) {
		boolean query = true;
		boolean debug_mode = preferences.getBooleanPreference(PreferenceList.DEBUG_MODE);
		long lastQueryTime = preferences.getLongPreference(PreferenceList.LAST_QUERY_TIME);

		// Don't query Overpass if the location is less accurate than 100m
		if (location.getAccuracy() > MINLOCATIONACCURACY) {
			query = false;
		}

		// Don't query Overpass if less than 1 hour has passed since the last query
		if (System.currentTimeMillis() - lastQueryTime < MINQUERYINTERVAL) {
			query = false;
		}

		// Don't query Overpass is you've moved more than 20m from the last location query (5 mins ago)
		// (indicates you're probably not in the same place as 5 mins ago)
		if (location.distanceTo(lastLocation) > MINQUERYDISTANCE) {
			query = false;
		}

		// Don't query Overpass is youre still within 20m of the last location query that you were
		// notified about (indicates you've probably still in the same place)
		if (location.distanceTo(lastQueryLocation) < MINQUERYDISTANCE) {
			query = false;
		}

		// If we're in debug mode, query every time
		if (debug_mode && BuildConfig.DEBUG) {
			query = true;
		}
		System.out.println("query = " + query);
		return query;
	}

}
