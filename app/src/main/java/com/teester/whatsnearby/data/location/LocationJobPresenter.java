package com.teester.whatsnearby.data.location;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.v4.app.ActivityCompat;

import com.mapzen.android.lost.api.LocationListener;
import com.mapzen.android.lost.api.LocationRequest;
import com.mapzen.android.lost.api.LocationServices;
import com.mapzen.android.lost.api.LostApiClient;
import com.teester.whatsnearby.BuildConfig;
import com.teester.whatsnearby.data.PreferenceList;
import com.teester.whatsnearby.data.source.Preferences;
import com.teester.whatsnearby.data.source.QueryOverpass;
import com.teester.whatsnearby.data.source.SourceContract;
import com.teester.whatsnearby.main.MainActivity;

import java.util.Locale;

import static android.support.v4.content.ContextCompat.startActivity;

public class LocationJobPresenter
		implements
		LocationContract.Presenter,
		LostApiClient.ConnectionCallbacks,
		LocationListener,
		Runnable {

	private static final int MINQUERYINTERVAL = 60 * 60 * 1000;
	private static final double MINQUERYDISTANCE = 20;
	private static final int MINLOCATIONACCURACY = 100;

	private Location location;
	private Location lastLocation;
	private Location lastQueryLocation;
	private LocationContract.LocationJobService locationJobServiceCallback;
	private LostApiClient client;
	private Context context;
	private SourceContract.Preferences preferences;

	public LocationJobPresenter(Context context, LocationContract.LocationJobService locationCallback) {
		this.context = context;
		this.preferences = new Preferences(context);
		this.locationJobServiceCallback = locationCallback;
	}

	/**
	 *  Creates a LostApiClient with a listener and connects to it
	 */
	public void getLocation() {
		client = new LostApiClient.Builder(context).addConnectionCallbacks(this).build();
		client.connect();
	}

	/**
	 *  Sets preferences for the debug screen, updates the recent detected and queried locations
	 *  and initiates an overpass query
	 *
	 *  @param location the queried location
	 */
	@Override
	public void processLocation(Location location) {
		this.location = location;
		if (lastLocation == null) {
			lastLocation = setPreviousLocation(PreferenceList.LAST_LOCATION_LATITUDE, PreferenceList.LAST_LOCATION_LONGITUDE);
		}
		if (lastQueryLocation == null) {
			lastQueryLocation = setPreviousLocation(PreferenceList.LAST_QUERY_LOCATION_LATITUDE, PreferenceList.LAST_QUERY_LOCATION_LONGITUDE);
		}

		boolean queried = decideWhetherToQuery();

		if (queried) {
			performOverpassQuery();
		}

		setPreferences(queried);
	}

	/**
	 *  Returns a location, depending on whether there are stored preferences or not
	 *
	 *  @param location The current location
	 *  @param latitudePreference A previously stored latitudePreference
	 *  @param longitudePreference A previously stored longitude
	 *
	 *  @return a location
	 */
	private Location setPreviousLocation(String latitudePreference, String longitudePreference) {
		double latitude = preferences.getDoublePreference(latitudePreference);
		double longitude = preferences.getDoublePreference(longitudePreference);

		Location newLocation = new Location("dummyprovider");
		newLocation.setLatitude(latitude);
		newLocation.setLongitude(longitude);

		return newLocation;
	}

	/**
	 *  Set preferences relating to current location to persist them for the next location
	 *
	 *  @param queried Whether or not an overpass query was performed
	 */
	private void setPreferences(boolean queried) {
		preferences.setFloatPreference(PreferenceList.LOCATION_ACCURACY, location.getAccuracy());
		preferences.setFloatPreference(PreferenceList.DISTANCE_TO_LAST_QUERY, location.distanceTo(lastQueryLocation));
		preferences.setLongPreference(PreferenceList.QUERY_INTERVAL, System.currentTimeMillis() - preferences.getLongPreference(PreferenceList.LAST_QUERY_TIME));
		preferences.setFloatPreference(PreferenceList.DISTANCE_TO_LAST_LOCATION, location.distanceTo(lastLocation));
		preferences.setDoublePreference(PreferenceList.LATITUDE, location.getLatitude());
		preferences.setDoublePreference(PreferenceList.LONGITUDE, location.getLongitude());
		preferences.setStringPreference(PreferenceList.LOCATION_PROVIDER, location.getProvider());
		preferences.setDoublePreference(PreferenceList.LAST_LOCATION_LATITUDE, location.getLatitude());
		preferences.setDoublePreference(PreferenceList.LAST_LOCATION_LONGITUDE, location.getLongitude());

		if (queried) {
			preferences.setDoublePreference(PreferenceList.LAST_QUERY_LOCATION_LATITUDE, location.getLatitude());
			preferences.setDoublePreference(PreferenceList.LAST_QUERY_LOCATION_LONGITUDE, location.getLongitude());
		}
	}

	/**
	 *  Logic dictating whether or not to query the Overpass api for a given location based on
	 *  location accuracy, time since last notification and distance since last query.  If the app is in
	 *  debug mode, it always returns true
	 *
	 *  @param location The queried location
	 *
	 *  @return a boolean indicating whether or not to query
	 */
	private boolean decideWhetherToQuery() {
		boolean query = true;
		boolean debug_mode = preferences.getBooleanPreference(PreferenceList.DEBUG_MODE);
		long lastNotificationTime = preferences.getLongPreference(PreferenceList.LAST_NOTIFICATION_TIME);

		String notQueryReason = "";

		// Don't query Overpass if the location is less accurate than 100m
		if (location.getAccuracy() > MINLOCATIONACCURACY) {
			notQueryReason += String.format(Locale.getDefault(), "Accuracy is not good enough: %.0fm\n", location.getAccuracy());
			query = false;
		}

		// Don't query Overpass if less than 1 hour has passed since the last notification
		if (System.currentTimeMillis() - lastNotificationTime < MINQUERYINTERVAL) {
			notQueryReason += String.format(Locale.getDefault(), "Not long enough since last notification: %dmins\n", ((System.currentTimeMillis() - lastNotificationTime) / 60000));
			query = false;
		}

		// Don't query Overpass is you've moved more than 20m from the last location query (5 mins ago)
		// (indicates you're probably not in the same place as 5 mins ago)
		if (location.distanceTo(lastLocation) > MINQUERYDISTANCE) {
			notQueryReason += String.format(Locale.getDefault(), "Too far enough from last location: %.0fm\n", location.distanceTo(lastLocation));
			query = false;
		}

		// Don't query Overpass is youre still within 20m of the last location query that you were
		// notified about (indicates you've probably still in the same place)
		if (location.distanceTo(lastQueryLocation) < MINQUERYDISTANCE) {
			notQueryReason += String.format(Locale.getDefault(), "Not far enough from location of last query: %.0fm\n", location.distanceTo(lastQueryLocation));
			query = false;
		}

		// If we're in debug mode, query every time
		if (debug_mode && BuildConfig.DEBUG) {
			query = true;
		}

		if (query) {
			notQueryReason += "Queried";
		}

		preferences.setStringPreference(PreferenceList.NOT_QUERY_REASON, notQueryReason);
		System.out.println(notQueryReason);

		return query;
	}

	/**
	 *  Initiates an overpass query on a new thread
	 */
	@Override
	public void performOverpassQuery() {
		new Thread(this).start();
	}

    /**
	 *  When connected to the location client, ensure we have permissions and request location
	 *  updates
	 */
	@Override
	public void onConnected() {
		LocationRequest request = LocationRequest.create();
		request.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
		request.setInterval(60000);
		request.setFastestInterval(60000);

		checkLocationPermission();

		LocationServices.FusedLocationApi.requestLocationUpdates(client, request, this);

	}

	/**
	 *  When the connection is suspended, we don't need to worry about it.  The job will just be
	 *  skipped
	 */
	@Override
	public void onConnectionSuspended() {
		// required empty method
	}

	/**
	 *  When the location changes, process it, disconnect from the client and inform the jobservice
	 *  that the job is finished
	 */
	@Override
	public void onLocationChanged(Location location) {
		processLocation(location);
		LocationServices.FusedLocationApi.removeLocationUpdates(client, this);
		client.disconnect();
		locationJobServiceCallback.locationCallback();
	}

	/**
	 *  Ensures we have android permissions for location and starts the main activity if we don't
	 */
	private void checkLocationPermission() {

		if (ActivityCompat.checkSelfPermission(context,
				Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

			Intent intent = new Intent(context, MainActivity.class);
			startActivity(context, intent, null);

			return;
		}
	}

	/**
	 * Runs an overpass query on a background thread
	 */
	@Override
	public void run() {
		SourceContract.Overpass overpassQuery = new QueryOverpass(context);
		overpassQuery.queryOverpass(location.getLatitude(), location.getLongitude(), location.getAccuracy());
	}
}
