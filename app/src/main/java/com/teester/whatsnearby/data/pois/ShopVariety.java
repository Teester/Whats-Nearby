package com.teester.whatsnearby.data.pois;

import com.teester.whatsnearby.R;

public class ShopVariety extends Poi {

	public ShopVariety() {
		objectName = "variety";
		objectClass = "shop";
		objectIcon = R.drawable.ic_variety_store;
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
