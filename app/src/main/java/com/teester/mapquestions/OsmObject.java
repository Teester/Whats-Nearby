package com.teester.mapquestions;

import android.content.res.Resources;
import android.location.Location;
import android.os.Parcel;
import android.os.Parcelable;

public class OsmObject implements Parcelable {

	public static final Parcelable.Creator<OsmObject> CREATOR = new Parcelable.Creator<OsmObject>() {
		@Override
		public OsmObject createFromParcel(Parcel parcel) {
			return new OsmObject(parcel);
		}

		@Override
		public OsmObject[] newArray(int size) {
			return new OsmObject[size];
		}
	};

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

	public OsmObject(int id, String osmType, String name, String type, Location location, float distance) {
		this.id = id;
		this.osmType = osmType;
		this.name = name;
		this.location = location;
		this.type = type;
		this.distance = distance;
	}

	private OsmObject(Parcel in) {
		this.id = in.readInt();
		this.osmType = in.readString();
		this.name = in.readString();
		double latitude = in.readDouble();
		double longitude = in.readDouble();
		this.location = new Location("dummyprovider");
		this.location.setLatitude(latitude);
		this.location.setLongitude(longitude);
		this.type = in.readString();
		this.distance = in.readFloat();
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
	@Override
	public void writeToParcel(Parcel parcel, int i) {
		parcel.writeInt(this.id);
		parcel.writeString(this.osmType);
		parcel.writeString(this.name);
		parcel.writeDouble(this.location.getLatitude());
		parcel.writeDouble(this.location.getLongitude());
		parcel.writeString(this.type);
		parcel.writeFloat(this.distance);
	}

	@Override
	public int describeContents() {
		return 0;
	}
}
