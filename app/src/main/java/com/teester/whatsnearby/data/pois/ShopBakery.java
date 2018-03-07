package com.teester.whatsnearby.data.pois;

import com.teester.whatsnearby.R;

public class ShopBakery extends Poi {

	public ShopBakery() {
		objectName = "bakery";
		objectClass = "shop";
		objectIcon = R.drawable.ic_bakery;
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
