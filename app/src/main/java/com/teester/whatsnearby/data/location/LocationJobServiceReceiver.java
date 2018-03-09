package com.teester.whatsnearby.data.location;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.Location;

import com.mapzen.android.lost.api.LocationResult;
import com.mapzen.android.lost.api.LostApiClient;
import com.teester.whatsnearby.data.source.Preferences;
import com.teester.whatsnearby.data.source.QueryOverpass;
import com.teester.whatsnearby.data.source.SourceContract;

import java.util.List;

public class LocationJobServiceReceiver extends BroadcastReceiver implements LocationJobServiceContract.Receiver {
	public static String PROCESS_UPDATES = "";
	PendingIntent pendingIntent;
	LostApiClient client;

	@Override
	public void onReceive(Context context, Intent intent) {
		LocationJobServiceContract.Presenter locationPresenter = new LocationJobPresenter(context, this, new Preferences(context));
		System.out.println("In LocationJobServiceReceiver.onReceive");
		final LocationResult locationResult = LocationResult.extractResult(intent);

//		pendingIntent = PendingIntent.getBroadcast(context, 10000, intent, PendingIntent.FLAG_NO_CREATE);
//		if (PROCESS_UPDATES.equals(action)) {
		if (locationResult != null) {
			Location location;
			List<Location> locations = locationResult.getLocations();
			if (locations.size() > 0) {
				location = locations.get(0);
			} else {
				location = locationResult.getLastLocation();
			}
			locationPresenter.processLocation(location);
			System.out.println("Location: " + location.getLatitude() + " ," + location.getLongitude());
		}

//		}
//		client.removeLocationUpdates(pendingIntent);
	}

	public void performOverpassQuery(final Context context, final Location location) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				SourceContract.Overpass overpassQuery = new QueryOverpass(context);
				overpassQuery.queryOverpass(location.getLatitude(), location.getLongitude(), location.getAccuracy());
			}
		}).start();
	}

	public void createNotification(Context context, String name, int drawable) {
		LocationJobNotifier.createNotification(context, name, drawable);
	}

	@Override
	public void createNotification(String name, int drawable) {

	}

	@Override
	public void performOverpassQuery(Location location) {

	}
}
