package com.teester.whatsnearby.data.pois;

import com.teester.whatsnearby.R;

public class AmenityRestaurant extends Poi {

	public AmenityRestaurant() {
		objectName = "restaurant";
		objectClass = "amenity";
		objectIcon = R.drawable.ic_restaurant;
		questions = new String[]{
				"wheelchair",
				"wheelchair_toilets",
				"wifi",
				"wifi_fee",
				"outdoor_seating",
				"vegetarian",
				"vegan",
				"takeaway",
				"deliver",
				"reservation",
				"cash",
				"cheques",
				"credit_card",
				"debit_card",
				"contactless"
		};
	}
}
