package com.teester.whatsnearby.data;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(entities = {VisitedLocation.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

	private static AppDatabase INSTANCE;

	public static AppDatabase getAppDatabase(Context context) {
		if (INSTANCE == null) {
			INSTANCE =
					Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "user-database")
							// allow queries on the main thread.
							// Don't do this on a real app! See PersistenceBasicSample for an example.
							.allowMainThreadQueries()
							.build();
		}
		return INSTANCE;
	}

	public static void destroyInstance() {
		INSTANCE = null;
	}

	public abstract VisitedLocationDao visitedLocationDao();
}