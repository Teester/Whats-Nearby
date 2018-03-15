package com.teester.whatsnearby.data.pois;

import com.teester.whatsnearby.R;

public class ShopConvenience extends Poi {

	public ShopConvenience() {
		objectName = "convenience";
		objectClass = "shop";
		objectIcon = R.drawable.ic_convenience;
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
