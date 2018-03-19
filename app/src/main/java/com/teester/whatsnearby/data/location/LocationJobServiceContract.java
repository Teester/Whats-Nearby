package com.teester.whatsnearby.data.location;

import android.content.Context;
import android.location.Location;

public interface LocationJobServiceContract {

	interface Presenter {

		void processLocation(Location location);

		void performOverpassQuery(Context context, Location location);

		void createNotification(Context context, String name, int drawable);
	}

	interface Notifier {
	}
}
