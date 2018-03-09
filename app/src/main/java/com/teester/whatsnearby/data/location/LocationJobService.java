package com.teester.whatsnearby.data.location;

import android.Manifest;
import android.app.PendingIntent;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.v4.app.ActivityCompat;

import com.mapzen.android.lost.api.LocationListener;
import com.mapzen.android.lost.api.LocationRequest;
import com.mapzen.android.lost.api.LocationServices;
import com.mapzen.android.lost.api.LostApiClient;
import com.teester.whatsnearby.data.source.Preferences;
import com.teester.whatsnearby.data.source.SourceContract;
import com.teester.whatsnearby.main.MainActivity;

public class LocationJobService extends JobService implements LocationJobServiceContract.Service {

	private LostApiClient client;
	private JobParameters jobParameters;
	private LocationJobServiceContract.Presenter locationPresenter;
	private LocationListener listener = new LocationListener() {
		@Override
		public void onLocationChanged(Location location) {

			System.out.println("LocationJobservice.LocationListener.onLocationChanged");
			locationPresenter.processLocation(location);
		}
	};

	@Override
	public boolean onStartJob(JobParameters jobParameters) {
		System.out.println("In LocationJobservice.onStartJob");
		final Context context = getApplicationContext();
		SourceContract.Preferences preferences = new Preferences(context);

		this.jobParameters = jobParameters;

		client = new LostApiClient.Builder(this)
				.addConnectionCallbacks(new LostApiClient.ConnectionCallbacks() {
					@Override
					public void onConnected() {
						LocationRequest request = LocationRequest.create();
						request.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
						request.setInterval(300000);
						request.setFastestInterval(60000);

						Intent resultIntent = new Intent(context, LocationJobServiceReceiver.class);
						//resultIntent.setAction(LocationJobServiceReceiver.PROCESS_UPDATES);
						PendingIntent callbackIntent = PendingIntent.getBroadcast(context, 10000, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);

						checkLocationPermission();

						LocationServices.FusedLocationApi.requestLocationUpdates(client, request, callbackIntent);
						System.out.println("Connected");
					}

					@Override
					public void onConnectionSuspended() {
						System.out.println("Connection Suspended");

						// required empty method
					}
				}).build();
		client.connect();
		jobFinished(jobParameters, true);
		return false;
	}

	@Override
	public boolean onStopJob(JobParameters jobParameters) {
		System.out.println("In LocationJobService.onStopJob");
		return false;
	}

	public void checkLocationPermission() {
		System.out.println("In LocationJobService.checkLocationPermission");

		if (ActivityCompat.checkSelfPermission(getApplicationContext(),
				Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

			Intent intent = new Intent(getBaseContext(), MainActivity.class);
			startActivity(intent);

			return;
		}
	}

	private void onJobFinished() {
		System.out.println("In LocationJobService.onJobFinished");

		jobFinished(jobParameters, true);
	}


	@Override
	public void cancelNotifications() {

	}

	@Override
	public void createLostClient(int interval) {

	}
}
