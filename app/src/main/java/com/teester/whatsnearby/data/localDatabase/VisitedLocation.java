package com.teester.whatsnearby.data.localDatabase;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.teester.whatsnearby.data.OsmObject;

@Entity
public class VisitedLocation {

	@PrimaryKey(autoGenerate = true)
	private int uid;
	@ColumnInfo(name = "osm_id")
	private long osmId;
	@ColumnInfo(name = "name")
	private String name;
	@ColumnInfo(name = "latitude")
	private double latitude;
	@ColumnInfo(name = "longitude")
	private double longitude;
	@ColumnInfo(name = "time_visited")
	private long timeVisited;

	public VisitedLocation(int uid, long osmId, String name, double latitude, double longitude, long timeVisited) {
		this.uid = uid;
		this.osmId = osmId;
		this.name = name;
		this.latitude = latitude;
		this.longitude = longitude;
		this.timeVisited = timeVisited;
	}

	public VisitedLocation(OsmObject osmObject) {
		this.osmId = osmObject.getId();
		this.name = osmObject.getName();
		this.latitude = osmObject.getLatitude();
		this.longitude = osmObject.getLongitude();
		this.timeVisited = System.currentTimeMillis();
	}

	public int getUid() {
		return uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}

	public long getOsmId() {
		return osmId;
	}

	public void setOsmId(long osmId) {
		this.osmId = osmId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public long getTimeVisited() {
		return timeVisited;
	}

	public void setTimeVisited(long timeVisited) {
		this.timeVisited = timeVisited;
	}
}
