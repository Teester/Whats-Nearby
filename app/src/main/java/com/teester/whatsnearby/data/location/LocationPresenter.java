package com.teester.whatsnearby.data.location;

import android.location.Location;

import com.teester.whatsnearby.BuildConfig;
import com.teester.whatsnearby.data.source.SourceContract;

public class LocationPresenter implements LocationServiceContract.Presenter {

	private static final int INTERVAL = 1 * 60 * 1000;
	private static final int MINQUERYINTERVAL = 60 * 60 * 1000;
	private static final double MINQUERYDISTANCE = 20;
	private static final int MINLOCATIONACCURACY = 100;
	private static final String OVERPASSLASTQUERYTIMEPREF = "last_overpass_query_time";

	private Location lastLocation;
	private Location lastQueryLocation;

	private LocationServiceContract.Service service;
	private SourceContract.Preferences preferences;

	public LocationPresenter(LocationServiceContract.Service service, SourceContract.Preferences preferences) {
		this.service = service;
		this.preferences = preferences;
	}

	@Override
	public void processLocation(Location location) {
		if (lastLocation == null) {
			lastLocation = location;
		}
		if (lastQueryLocation == null) {
			lastQueryLocation = location;
		}

		preferences.setFloatPreference("location_accuracy", location.getAccuracy());
		preferences.setFloatPreference("distance_to_last_query", location.distanceTo(lastQueryLocation));
		preferences.setLongPreference("query_interval", System.currentTimeMillis() - preferences.getLongPreference(OVERPASSLASTQUERYTIMEPREF));
		preferences.setFloatPreference("distance_to_last_location", location.distanceTo(lastLocation));
		preferences.setDoublePreference("latitude", location.getLatitude());
		preferences.setDoublePreference("longitude", location.getLongitude());

		if (decideWhetherToQuery(location)) {
			lastQueryLocation = location;
			service.performOverpassQuery(location);
		}
		lastLocation = location;
	}

	private boolean decideWhetherToQuery(Location location) {
		boolean query = true;
		boolean debug_mode = preferences.getBooleanPreference("debug_mode");
		long lastQueryTime = preferences.getLongPreference(OVERPASSLASTQUERYTIMEPREF);

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
		return query;
	}

	@Override
	public void queryResult() {
		service.createNotification("", 0);
	}

	@Override
	public void updateLastQueryTime() {
		// required empty method
	}

	@Override
	public void createLostClient() {
		// required empty method
	}

	@Override
	public void init() {
		this.service.createLostClient(INTERVAL);
	}

	@Override
	public void destroy() {
		// required empty method
	}
}
