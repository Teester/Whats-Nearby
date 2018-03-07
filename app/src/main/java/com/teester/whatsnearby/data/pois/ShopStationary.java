package com.teester.whatsnearby.data.pois;

import com.teester.whatsnearby.R;

public class ShopStationary extends Poi {

	public ShopStationary() {
		objectName = "stationary";
		objectClass = "shop";
		objectIcon = R.drawable.ic_stationery;
		questions = new String[]{
				"wheelchair",
				"cash",
				"cheques",
				"credit_card",
				"debit_card",
				"contactless"
		};
	}
}
