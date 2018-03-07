package com.teester.whatsnearby.data.pois;

import com.teester.whatsnearby.R;

public class ShopShoes extends Poi {

	public ShopShoes() {
		objectName = "shoes";
		objectClass = "shop";
		objectIcon = R.drawable.ic_shoes;
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
