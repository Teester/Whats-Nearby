package com.teester.whatsnearby.data.pois;

import com.teester.whatsnearby.R;

public class AmenityBank extends Poi {

	public AmenityBank() {
		objectName = "bank";
		objectClass = "amenity";
		objectIcon = R.drawable.ic_bank;
		questions = new String[]{
				"wheelchair"
		};
	}
}
