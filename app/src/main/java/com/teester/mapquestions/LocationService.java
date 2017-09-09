package com.teester.mapquestions;

import android.Manifest;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.mapzen.android.lost.api.LocationListener;
import com.mapzen.android.lost.api.LocationRequest;
import com.mapzen.android.lost.api.LocationServices;
import com.mapzen.android.lost.api.LostApiClient;

public class LocationService extends Service {

	private static final String TAG = LocationService.class.getSimpleName();

	LostApiClient client;
	private Location lastLocation;
	private static final int PRIORITY = LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY;
	private static final int INTERVAL = 1 * 60 * 1000;
	private static final int MINQUERYINTERVAL = 3 * 60 * 1000;
	private static final double MINQUERYDISTANCE = 20;
	private long lastQueryTime;
	private Location lastQueryLocation;

	LocationListener listener = new LocationListener() {
		@Override
		public void onLocationChanged(Location location) {
			if (lastLocation == null) { lastLocation = location; }
			if (lastQueryLocation == null) { lastQueryLocation = location; }
			Log.d(TAG, "Location Changed");
			Log.d(TAG, "lat: "+location.getLatitude()+ " lon: " + location.getLongitude() + " distance: " + location.distanceTo(lastLocation) + "m");

			// Only query Overpass if the location is accurate to within 100m
			if (location.getAccuracy() < 100) {
				Log.d(TAG, "Location accuracy is " + location.getAccuracy() + "m");
				// Only query Overpass if over 1 hour has passed since the last query
				long timeInterval = System.currentTimeMillis() - lastQueryTime;
				if (timeInterval > MINQUERYINTERVAL) {
					Log.d(TAG, "Time since last query is " + timeInterval/60000 + " mins");
					// Only query Overpass is youre still withim 20m of the last location query (5 mins ago)
					// (indicates you've probably been in the same place for over 5 mins)
					double distance = location.distanceTo(lastLocation);
					if (location.distanceTo(lastLocation) < MINQUERYDISTANCE) {
						Log.d(TAG, "Distance from last location is " + distance + " m");
						if (location.distanceTo(lastQueryLocation) > MINQUERYDISTANCE) {
							lastQueryTime = System.currentTimeMillis();
							lastQueryLocation = location;
							Log.d(TAG, "Distance from last location query is " + lastQueryLocation.distanceTo(location) + " m");
							Log.d(TAG, "Querying Overpass");

							// Cancel all notifications before we run a new query.  If we're querying,
							// Overpass, we're no longer in the same place as the last notification.
							getApplicationContext();
							NotificationManager notificationManager= (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
							notificationManager.cancelAll();
							new QueryOverpass(location, getApplicationContext());
						} else {
							Log.d(TAG, "Not querying Overpass, distance from last location query is " + lastQueryLocation.distanceTo(location) + " m");
						}
					} else {
						Log.d(TAG, "Not querying Overpass, distance from last location is " + distance + " m");
					}
				}  else {
					Log.d(TAG, "Not querying Overpass, time since last query is " + timeInterval/60000 + " mins");
				}
			} else {
				Log.d(TAG, "Not querying Overpass, location accuracy is " + location.getAccuracy() + "m");
			}
			lastLocation = location;
		}
	};

	@Nullable
	@Override
	public IBinder onBind(Intent intent) {
		Log.d(TAG, "In inBind");
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		client = new LostApiClient.Builder(this).addConnectionCallbacks(new LostApiClient.ConnectionCallbacks() {
					@Override
					public void onConnected() {
						LocationRequest request = LocationRequest.create();
						request.setPriority(PRIORITY);
						request.setInterval(INTERVAL);

						if (ActivityCompat.checkSelfPermission(getApplicationContext(),
								Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
							// TODO: Handle missing permission
							return;
						}
						LocationServices.FusedLocationApi.requestLocationUpdates(client, request, listener);
					}

					@Override public void onConnectionSuspended() {

					}
				}).build();
		client.connect();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		return START_STICKY;
	}

	@Override public void onDestroy() {
		super.onDestroy();
		LocationServices.FusedLocationApi.removeLocationUpdates(client, listener);
		client.disconnect();
	}

}
