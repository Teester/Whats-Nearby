package com.teester.whatsnearby.model.data.location;

import android.location.Location;

public class LocationPresenter implements LocationServiceContract.Presenter {

	private static final String TAG = LocationPresenter.class.getSimpleName();

	private static final int INTERVAL = 1 * 60 * 1000;
	private static final int MINQUERYINTERVAL = 60 * 60 * 1000;
	private static final double MINQUERYDISTANCE = 20;
	private static final int MINLOCATIONACCURACY = 100;

	private Location lastLocation;
	private long lastQueryTime;
	private Location lastQueryLocation;

	private LocationServiceContract.Service service;

	public LocationPresenter(LocationServiceContract.Service service) {
		this.service = service;
	}

	@Override
	public void processLocation(Location location) {
		if (lastLocation == null) {
			lastLocation = location;
		}
		if (lastQueryLocation == null) {
			lastQueryLocation = location;
		}

		boolean query = true;

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

		if (query == true) {
			lastQueryTime = System.currentTimeMillis();
			lastQueryLocation = location;

			// Cancel all notifications before we run a new query.  If we're querying,
			// Overpass, we're no longer in the same place as the last notification.
			service.cancelNotifications();
			service.performOverpassQuery(location);
		}

		lastLocation = location;
	}

	@Override
	public void createLostClient() {
	}

	@Override
	public void init() {
		this.service.createLostClient(INTERVAL);
	}

	@Override
	public void destroy() {

	}
}
