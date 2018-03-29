package com.teester.whatsnearby.data.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(entities = {VisitedLocation.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

	private static AppDatabase INSTANCE;

	public static AppDatabase getAppDatabase(Context context) {
		if (INSTANCE == null) {
			INSTANCE =	Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "user-database").build();
		}
		return INSTANCE;
	}

	public abstract VisitedLocationDao visitedLocationDao();
}