package com.teester.whatsnearby.data.pois;

import com.teester.whatsnearby.R;
import com.teester.whatsnearby.data.questions.Wheelchair;

public class AmenityBank extends Poi {

	public AmenityBank() {
		objectName = "bank";
		objectClass = "amenity";
		objectIcon = R.drawable.ic_bank;

		questions.add(new Wheelchair());
	}
}
