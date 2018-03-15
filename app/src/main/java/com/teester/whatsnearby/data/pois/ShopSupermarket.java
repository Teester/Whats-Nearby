package com.teester.whatsnearby.data.pois;

import com.teester.whatsnearby.R;

public class ShopSupermarket extends Poi {

	public ShopSupermarket() {
		objectName = "supermarket";
		objectClass = "shop";
		objectIcon = R.drawable.ic_supermarket;
		questions = new String[]{
				"wheelchair",
				"cash",
				"cheques",
				"credit_card",
				"debit_card",
				"contactless",
				"organic"
		};
	}
}
