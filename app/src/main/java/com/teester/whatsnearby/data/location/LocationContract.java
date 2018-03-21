package com.teester.whatsnearby.data.location;

import android.location.Location;

public interface LocationContract {

	interface Presenter {

		void processLocation(Location location);

		void performOverpassQuery();

		void createNotification(String name, int drawable);
	}

	interface Notifier {
	}


	interface LocationJobService {

		void locationCallback();

	}
}
