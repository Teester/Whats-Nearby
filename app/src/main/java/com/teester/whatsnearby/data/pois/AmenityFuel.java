package com.teester.whatsnearby.data.pois;

import com.teester.whatsnearby.R;

public class AmenityFuel extends Poi {

	public AmenityFuel() {
		objectName = "fuel";
		objectClass = "amenity";
		objectIcon = R.drawable.ic_fuel;
		questions = new String[]{
				"wheelchair",
				"wheelchair_toilets",
				"cash",
				"cheques",
				"credit_card",
				"debit_card",
				"contactless"
		};
	}
}
