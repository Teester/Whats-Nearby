package com.teester.whatsnearby.data.pois;

import com.teester.whatsnearby.R;
import com.teester.whatsnearby.data.questions.Wheelchair;

/**
 * Created by mark on 07/03/18.
 */

public class AmenityDentist extends Poi {

	public AmenityDentist() {
		objectName = "dentist";
		objectClass = "amenity";
		objectIcon = R.drawable.ic_dentist;

		questions.add(new Wheelchair());
	}
}
