package com.teester.whatsnearby.data.pois;

import com.teester.whatsnearby.R;

public class AmenityDoctors extends Poi {

	public AmenityDoctors() {
		objectName = "doctors";
		objectClass = "amenity";
		objectIcon = R.drawable.ic_doctors;
		questions = new String[]{
				"wheelchair"
		};
	}
}
