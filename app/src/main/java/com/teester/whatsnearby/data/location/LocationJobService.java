package com.teester.whatsnearby.data.location;

import android.Manifest;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.v4.app.ActivityCompat;

import com.mapzen.android.lost.api.LocationListener;
import com.mapzen.android.lost.api.LocationRequest;
import com.mapzen.android.lost.api.LocationServices;
import com.mapzen.android.lost.api.LostApiClient;
import com.teester.whatsnearby.data.source.Preferences;
import com.teester.whatsnearby.main.MainActivity;

public class LocationJobService extends JobService implements LostApiClient.ConnectionCallbacks, LocationListener {

	private LostApiClient client;
	private LocationListener locationListener;
	private JobParameters jobParameters;

	@Override
	public boolean onStartJob(final JobParameters jobParameters) {
		this.jobParameters = jobParameters;
		client = new LostApiClient.Builder(this).addConnectionCallbacks(this).build();
		client.connect();
		return false;
	}

	@Override
	public boolean onStopJob(JobParameters jobParameters) {
		return false;
	}

	private void checkLocationPermission() {

		if (ActivityCompat.checkSelfPermission(getApplicationContext(),
				Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

			Intent intent = new Intent(getBaseContext(), MainActivity.class);
			startActivity(intent);

			return;
		}
	}

	@Override
	public void onConnected() {
		LocationRequest request = LocationRequest.create();
		request.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
		request.setInterval(60000);
		request.setFastestInterval(60000);

		checkLocationPermission();

		LocationServices.FusedLocationApi.requestLocationUpdates(client, request, this);

	}

	@Override
	public void onConnectionSuspended() {
		// required empty method
	}

	@Override
	public void onLocationChanged(Location location) {
		LocationJobServiceContract.Presenter locationPresenter = new LocationJobPresenter(getApplicationContext(), new Preferences(getApplicationContext()));
		locationPresenter.processLocation(location);
		client.disconnect();
		jobFinished(jobParameters, true);
	}
}
