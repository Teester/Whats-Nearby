package com.teester.whatsnearby.data.location;

import android.location.Location;

public interface LocationServiceContract {

	interface Presenter {

		void processLocation(Location location);

		void createLostClient();

		void queryResult();

		void updateLastQueryTime();
	}

	interface Service {

		void cancelNotifications();

		void createLostClient(int interval);

		void performOverpassQuery(Location location);

		void createNotification(String name, int drawable);
	}
}
