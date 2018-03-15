package com.teester.whatsnearby.data.pois;

import com.teester.whatsnearby.R;

public class ShopSports extends Poi {

	public ShopSports() {
		objectName = "sports";
		objectClass = "shop";
		objectIcon = R.drawable.ic_sports;
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
