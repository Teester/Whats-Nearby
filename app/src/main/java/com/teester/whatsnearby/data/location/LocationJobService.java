package com.teester.whatsnearby.data.location;

import android.Manifest;
import android.app.PendingIntent;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.wifi.WifiManager;
import android.support.v4.app.ActivityCompat;

import com.mapzen.android.lost.api.LocationRequest;
import com.mapzen.android.lost.api.LocationServices;
import com.mapzen.android.lost.api.LostApiClient;
import com.teester.whatsnearby.main.MainActivity;

public class LocationJobService extends JobService implements LocationJobServiceContract.Service {

	private LostApiClient client;
	private JobParameters jobParameters;

	@Override
	public boolean onStartJob(JobParameters jobParameters) {
		final Context context = getApplicationContext();
		this.jobParameters = jobParameters;

		client = new LostApiClient.Builder(this)
				.addConnectionCallbacks(new LostApiClient.ConnectionCallbacks() {
					@Override
					public void onConnected() {
						int priority;
						if (checkWifi()) {
							priority = LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY;
						} else {
							priority = LocationRequest.PRIORITY_HIGH_ACCURACY;
						}
						LocationRequest request = LocationRequest.create();
						request.setPriority(priority);
						request.setInterval(60000);
						request.setFastestInterval(60000);

						Intent resultIntent = new Intent(context, LocationJobServiceReceiver.class);
						PendingIntent callbackIntent = PendingIntent.getBroadcast(context, 10000, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);

						checkLocationPermission();

						LocationServices.FusedLocationApi.requestLocationUpdates(client, request, callbackIntent);
					}

					@Override
					public void onConnectionSuspended() {
						// required empty method
					}
				}).build();
		client.connect();
		jobFinished(jobParameters, true);
		return false;
	}

	@Override
	public boolean onStopJob(JobParameters jobParameters) {
		return false;
	}

	public void checkLocationPermission() {

		if (ActivityCompat.checkSelfPermission(getApplicationContext(),
				Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

			Intent intent = new Intent(getBaseContext(), MainActivity.class);
			startActivity(intent);

			return;
		}
	}

	private boolean checkWifi() {
		WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
		switch (wifiManager.getWifiState()) {
			case WifiManager.WIFI_STATE_DISABLED:
			case WifiManager.WIFI_STATE_DISABLING:
				return false;
			case WifiManager.WIFI_STATE_ENABLED:
			case WifiManager.WIFI_STATE_ENABLING:
			case WifiManager.WIFI_STATE_UNKNOWN:
			default:
				return true;
		}
	}

}
