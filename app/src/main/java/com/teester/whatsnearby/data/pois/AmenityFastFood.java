package com.teester.whatsnearby.data.pois;

import com.teester.whatsnearby.R;

public class AmenityFastFood extends Poi {

	public AmenityFastFood() {
		objectName = "fast_food";
		objectClass = "amenity";
		objectIcon = R.drawable.ic_fast_food;
		questions = new String[]{
				"wheelchair",
				"wheelchair_toilets",
				"drive_through",
				"takeaway",
				"deliver",
				"cash",
				"cheques",
				"credit_card",
				"debit_card",
				"contactless"
		};
	}
}
