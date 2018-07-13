package com.teester.whatsnearby.data.location;

import android.location.Location;

public interface LocationContract {

	interface Presenter {

		void processLocation(Location location);

	}

	interface Notifier {
	}


	interface LocationJobService {

		void locationCallback();

	}
}
