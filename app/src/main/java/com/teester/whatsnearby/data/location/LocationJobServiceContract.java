package com.teester.whatsnearby.data.location;

import android.location.Location;

public interface LocationJobServiceContract {

	interface Presenter {

		void processLocation(Location location);

		void createLostClient();

		void queryResult();

		void updateLastQueryTime();
	}

	interface Service {

		void cancelNotifications();

		void createLostClient(int interval);

	}

	interface Receiver {
		void createNotification(String name, int drawable);

		void performOverpassQuery(Location location);

	}

	interface Notifier {
	}
}
