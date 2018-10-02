package com.teester.whatsnearby.data.pois;

import com.teester.whatsnearby.R;
import com.teester.whatsnearby.data.questions.Wheelchair;

public class AmenityDoctors extends Poi {

	public AmenityDoctors() {
		objectName = "doctors";
		objectClass = "amenity";
		objectIcon = R.drawable.ic_doctors;

		questions.add(new Wheelchair());
	}
}
