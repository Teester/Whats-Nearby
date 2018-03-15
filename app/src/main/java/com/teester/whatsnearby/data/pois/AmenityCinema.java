package com.teester.whatsnearby.data.pois;

import com.teester.whatsnearby.R;

/**
 * Created by mark on 07/03/18.
 */

public class AmenityCinema extends Poi {

	public AmenityCinema() {
		objectName = "cinema";
		objectClass = "amenity";
		objectIcon = R.drawable.ic_cinema;
		questions = new String[]{
				"wheelchair",
				"cash",
				"cheques",
				"credit_card",
				"debit_card",
				"contactless",
				"wheelchair_toilets"
		};
	}
}
