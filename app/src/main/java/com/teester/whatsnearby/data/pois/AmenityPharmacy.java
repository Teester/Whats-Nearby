package com.teester.whatsnearby.data.pois;

import com.teester.whatsnearby.R;

public class AmenityPharmacy extends Poi {

	public AmenityPharmacy() {
		objectName = "pharmacy";
		objectClass = "amenity";
		objectIcon = R.drawable.ic_pharmacy;
		questions = new String[]{
				"wheelchair",
				"cash",
				"cheques",
				"credit_card",
				"debit_card",
				"contactless",
				"dispensing"
		};
	}
}
