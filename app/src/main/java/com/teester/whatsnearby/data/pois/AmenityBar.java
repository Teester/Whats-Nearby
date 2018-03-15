package com.teester.whatsnearby.data.pois;

import com.teester.whatsnearby.R;

public class AmenityBar extends Poi {

	public AmenityBar() {
		objectName = "bar";
		objectClass = "amenity";
		objectIcon = R.drawable.ic_bar;
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
