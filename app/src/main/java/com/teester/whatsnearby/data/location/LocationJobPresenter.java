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

import static android.support.v4.content.ContextCompat.startActivity;

public class LocationJobPresenter implements LocationJobServiceContract.Presenter, LostApiClient.ConnectionCallbacks, LocationListener {

	private static final int MINQUERYINTERVAL = 60 * 60 * 1000;
	private static final double MINQUERYDISTANCE = 20;
	private static final int MINLOCATIONACCURACY = 100;

	private Location lastLocation;
	private Location lastQueryLocation;
	private LocationJobServiceContract.LocationJobService locationJobServiceCallback;
	private LostApiClient client;
	private Context context;
	private SourceContract.Preferences preferences;

	public LocationJobPresenter(Context context, LocationJobServiceContract.LocationJobService locationCallback) {
		this.context = context;
		this.preferences = new Preferences(context);
		this.locationJobServiceCallback = locationCallback;
	}

	/*
	 *  Creates a LostApiClient with a listener and connects to it
	 */
	public void getLocation() {
		client = new LostApiClient.Builder(context).addConnectionCallbacks(this).build();
		client.connect();
	}

	/*
	 *  Sets preferences for the debug screen, updates the recent detected and queried locations
	 *  and initiates an overpass query
	 *
	 *  @location the queried location
	 */
	@Override
	public void processLocation(Location location) {
		if (lastLocation == null) {

			double lastLatitude = preferences.getDoublePreference(PreferenceList.LAST_LOCATION_LATITUDE);
			double lastLongitude = preferences.getDoublePreference(PreferenceList.LAST_LOCATION_LONGITUDE);
			if (lastLatitude == 0 && lastLongitude == 0) {
				lastLocation = location;
			} else {
				lastLocation = new Location("dummyprovider");
				lastLocation.setLatitude(lastLatitude);
				lastLocation.setLongitude(lastLongitude);
			}
		}
		if (lastQueryLocation == null) {
			double lastQueryLatitude = preferences.getDoublePreference(PreferenceList.LAST_QUERY_LOCATION_LATITUDE);
			double lastQueryLongitude = preferences.getDoublePreference(PreferenceList.LAST_QUERY_LOCATION_LONGITUDE);
			if (lastQueryLatitude == 0 && lastQueryLongitude == 0) {
				lastQueryLocation = location;
			} else {
				lastQueryLocation = new Location("dummyprovider");
				lastQueryLocation.setLatitude(lastQueryLatitude);
				lastQueryLocation.setLongitude(lastQueryLongitude);
			}
		}

		preferences.setFloatPreference(PreferenceList.LOCATION_ACCURACY, location.getAccuracy());
		preferences.setFloatPreference(PreferenceList.DISTANCE_TO_LAST_QUERY, location.distanceTo(lastQueryLocation));
		preferences.setLongPreference(PreferenceList.QUERY_INTERVAL, System.currentTimeMillis() - preferences.getLongPreference(PreferenceList.LAST_QUERY_TIME));
		preferences.setFloatPreference(PreferenceList.DISTANCE_TO_LAST_LOCATION, location.distanceTo(lastLocation));
		preferences.setDoublePreference(PreferenceList.LATITUDE, location.getLatitude());
		preferences.setDoublePreference(PreferenceList.LONGITUDE, location.getLongitude());
		preferences.setStringPreference(PreferenceList.LOCATION_PROVIDER, location.getProvider());

		if (decideWhetherToQuery(location)) {
			lastQueryLocation = location;
			preferences.setDoublePreference(PreferenceList.LAST_QUERY_LOCATION_LATITUDE, location.getLatitude());
			preferences.setDoublePreference(PreferenceList.LAST_QUERY_LOCATION_LONGITUDE, location.getLongitude());
			performOverpassQuery(context, location);
		}
		lastLocation = location;
		preferences.setDoublePreference(PreferenceList.LAST_LOCATION_LATITUDE, location.getLatitude());
		preferences.setDoublePreference(PreferenceList.LAST_LOCATION_LONGITUDE, location.getLongitude());
	}

	/*
	 *  Logic dictating whether or not to query the Overpass api for a given location based on
	 *  location accuracy, time since last query and distance since last query.  If the app is in
	 *  debug mode, it always returns true
	 *
	 *  @location The queried location
	 *  @return a boolean indicating whether or not to query
	 */
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
		return query;
	}

	/*
	 *  Initiates an overpass query on a new thread
	 *
	 *  @context the application context
	 *  @location the queried location
	 */
	@Override
	public void performOverpassQuery(final Context context, final Location location) {
		SourceContract.Overpass overpassQuery = new QueryOverpass(context);
		overpassQuery.queryOverpass(location.getLatitude(), location.getLongitude(), location.getAccuracy());

	}

	/*
	 *  Initiates the creation of a notification
	 *
	 *  @context the application context
	 *  @name the location name for the notification
	 *  @drawable the drawable associated with the location type
	 */
	@Override
	public void createNotification(Context context, String name, int drawable) {
		LocationJobNotifier.createNotification(context, name, drawable);
	}

	/*
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

	/*
	 *  When the connection is suspended, we don't need to worry about it.  The job will just be
	 *  skipped
	 */
	@Override
	public void onConnectionSuspended() {
		// required empty method
	}

	/*
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

	/*
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

}
