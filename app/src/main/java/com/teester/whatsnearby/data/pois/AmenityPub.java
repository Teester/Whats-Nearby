package com.teester.whatsnearby.data.pois;

import com.teester.whatsnearby.R;

public class AmenityPub extends Poi {

	public AmenityPub() {
		objectName = "pub";
		objectClass = "amenity";
		objectIcon = R.drawable.ic_pub;
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
