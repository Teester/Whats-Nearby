package com.teester.whatsnearby.data;

public class OsmObject {

	private final int id;
	private final String osmType;
	private final String name;
	private final String type;
	private final float distance;
	private double latitude;
	private double longitude;

	public OsmObject(int id, String osmType, String name, String type, double latitude, double longitude, float distance) {
		this.id = id;
		this.osmType = osmType;
		this.name = name;
		this.latitude = latitude;
		this.longitude = longitude;
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
		return this.latitude;
	}

	public double getLongitude() {
		return this.longitude;
	}

	public String getType() {
		return this.type;
	}

	public float getDistance() {
		return this.distance;
	}
}
