package com.teester.whatsnearby.data.location;

import android.Manifest;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;

import com.mapzen.android.lost.api.LocationListener;
import com.mapzen.android.lost.api.LocationRequest;
import com.mapzen.android.lost.api.LocationServices;
import com.mapzen.android.lost.api.LostApiClient;
import com.teester.whatsnearby.data.source.Preferences;
import com.teester.whatsnearby.data.source.QueryOverpass;
import com.teester.whatsnearby.data.source.SourceContract;
import com.teester.whatsnearby.main.MainActivity;

public class LocationService extends Service implements LocationServiceContract.Service {

	private static final int PRIORITY = LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY;

	private LocationServiceContract.Presenter locationPresenter;
	private LocationListener listener = new LocationListener() {
		@Override
		public void onLocationChanged(Location location) {
			locationPresenter.processLocation(location);
		}
	};
	private LostApiClient client;

	@Nullable
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		Context context = getApplicationContext();
		SourceContract.Preferences preferences = new Preferences(context);
		locationPresenter = new LocationPresenter(this, preferences);
		locationPresenter.init();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		super.onStartCommand(intent, flags, startId);
		return START_STICKY;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		if (client.isConnected()) {
			LocationServices.FusedLocationApi.removeLocationUpdates(client, listener);
			client.disconnect();
		}
	}

	@Override
	public void setPresenter(LocationServiceContract.Presenter presenter) {
		locationPresenter = presenter;
	}

	@Override
	public void cancelNotifications() {
		Notifier.cancelNotifictions(getApplicationContext());
	}

	@Override
	public void createLostClient(final int interval) {
		client = new LostApiClient.Builder(this).addConnectionCallbacks(new LostApiClient.ConnectionCallbacks() {
			@Override
			public void onConnected() {
				LocationRequest request = LocationRequest.create();
				request.setPriority(PRIORITY);
				request.setInterval(interval);

				checkLocationPermission();

				LocationServices.FusedLocationApi.requestLocationUpdates(client, request, listener);
			}

			@Override
			public void onConnectionSuspended() {
				// required empty method
			}
		}).build();
		client.connect();
	}

	@Override
	public void performOverpassQuery(final Location location) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				SourceContract.Overpass overpassQuery = new QueryOverpass(getApplicationContext());
				overpassQuery.queryOverpass(location.getLatitude(), location.getLongitude(), location.getAccuracy());
			}
		}).start();
	}

	public void checkLocationPermission() {
		if (ActivityCompat.checkSelfPermission(getApplicationContext(),
				Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

			Intent intent = new Intent(getBaseContext(), MainActivity.class);
			startActivity(intent);

			return;
		}
	}

	@Override
	public void createNotification(String name, int drawable) {
		Notifier.createNotification(getApplicationContext(), name, drawable);
	}
}
