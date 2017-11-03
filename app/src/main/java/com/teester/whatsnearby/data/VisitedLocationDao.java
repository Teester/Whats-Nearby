package com.teester.whatsnearby.data;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

@Dao
public interface VisitedLocationDao {

	@Query("SELECT * FROM VisitedLocation where osm_id == :osm_id")
	VisitedLocation findByOsmId(long osm_id);

	@Insert
	void insert(VisitedLocation visitedLocation);

	@Delete
	void delete(VisitedLocation visitedLocation);
}
