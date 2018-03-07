package com.teester.whatsnearby.data.pois;

import com.teester.whatsnearby.R;

public class AmenityCafe extends Poi {

	public AmenityCafe() {
		objectName = "cafe";
		objectClass = "amenity";
		objectIcon = R.drawable.ic_cafe;
		questions = new String[]{
				"wheelchair",
				"outdoor_seating",
				"wifi",
				"wifi_fee",
				"cash",
				"cheques",
				"credit_card",
				"debit_card",
				"contactless",
				"wheelchair_toilets"
		};
	}
}
