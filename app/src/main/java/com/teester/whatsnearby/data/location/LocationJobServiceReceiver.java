package com.teester.whatsnearby.data.location;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.Location;

import com.mapzen.android.lost.api.LocationResult;
import com.teester.whatsnearby.data.source.Preferences;
import com.teester.whatsnearby.data.source.QueryOverpass;
import com.teester.whatsnearby.data.source.SourceContract;

import java.util.List;

public class LocationJobServiceReceiver extends BroadcastReceiver implements LocationJobServiceContract.Receiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		LocationJobServiceContract.Presenter locationPresenter = new LocationJobPresenter(context, this, new Preferences(context));
		final LocationResult locationResult = LocationResult.extractResult(intent);

		if (locationResult != null) {
			Location location;
			List<Location> locations = locationResult.getLocations();
			if (locations.size() > 0) {
				location = locations.get(0);
			} else {
				location = locationResult.getLastLocation();
			}
			locationPresenter.processLocation(location);
		}
	}

	@Override
	public void performOverpassQuery(final Context context, final Location location) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				SourceContract.Overpass overpassQuery = new QueryOverpass(context);
				overpassQuery.queryOverpass(location.getLatitude(), location.getLongitude(), location.getAccuracy());
			}
		}).start();
	}

	@Override
	public void createNotification(Context context, String name, int drawable) {
		LocationJobNotifier.createNotification(context, name, drawable);
	}
}
