package com.teester.whatsnearby.data;

import java.util.HashMap;
import java.util.Map;

public class OsmObject {

	private final long id;
	private final String osmType;
	private final String name;
	private final String type;
	private final float distance;
	private double latitude;
	private double longitude;
	private Map<String, String> tags = new HashMap<>();

	public OsmObject(long id, String osmType, String name, String type, double latitude, double longitude, float distance) {
		this.id = id;
		this.osmType = osmType;
		this.name = name;
		this.latitude = latitude;
		this.longitude = longitude;
		this.type = type;
		this.distance = distance;
	}

	public long getId() {
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

	public void addTag(String key, String value) {
		this.tags.put(key, value);
	}

	public String getTag(String key) {
		return this.tags.get(key);
	}

	public int getDrawable() {
		OsmObjectType objectType = PoiTypes.getPoiType(this.type);
		int drawable = objectType.getObjectIcon();
		return drawable;
	}
}
