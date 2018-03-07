package com.teester.whatsnearby.data.pois;

import com.teester.whatsnearby.R;

public class ShopJewelry extends Poi {

	public ShopJewelry() {
		objectName = "jewelry";
		objectClass = "shop";
		objectIcon = R.drawable.ic_jewelry;
		questions = new String[]{
				"wheelchair",
				"men",
				"women",
				"cash",
				"cheques",
				"credit_card",
				"debit_card",
				"contactless"
		};
	}
}
