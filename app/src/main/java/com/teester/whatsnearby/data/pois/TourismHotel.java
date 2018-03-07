package com.teester.whatsnearby.data.pois;

import com.teester.whatsnearby.R;

public class TourismHotel extends Poi {

	public TourismHotel() {
		objectName = "hotel";
		objectClass = "tourism";
		objectIcon = R.drawable.ic_hotel;
		questions = new String[]{
				"wheelchair",
				"wheelchair_toilets",
				"wifi",
				"wifi_fee",
				"cash",
				"cheques",
				"credit_card",
				"debit_card",
				"contactless"
		};
	}
}
