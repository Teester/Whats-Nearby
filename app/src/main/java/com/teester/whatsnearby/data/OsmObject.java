package com.teester.whatsnearby.data;

import android.location.Location;

public class OsmObject {

	private final int id;
	private final String osmType;
	private final String name;
	private final Location location;
	private final String type;
	private final float distance;

	public OsmObject(int id, String osmType, String name, String type, double latitude, double longitude, float distance) {
		this.id = id;
		this.osmType = osmType;
		this.name = name;
		this.location = new Location("dummyprovider");
		this.location.setLatitude(latitude);
		this.location.setLongitude(longitude);
		this.type = type;
		this.distance = distance;
	}

	public int getId() {
		return this.id;
	}

	public String getOsmType() {
		return this.osmType;
	}

	public String getName() {
		return this.name;
	}

	public double getLatitude() {
		return this.location.getLatitude();
	}

	public double getLongitude() {
		return this.location.getLongitude();
	}

	public Location getLocation() {
		return this.location;
	}

	public String getType() {
		return this.type;
	}

	public float getDistance() {
		return this.distance;
	}
}
