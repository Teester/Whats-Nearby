package com.teester.whatsnearby.data.pois;

import com.teester.whatsnearby.R;

public class ShopAlcohol extends Poi {

	public ShopAlcohol() {
		objectName = "alcohol";
		objectClass = "shop";
		objectIcon = R.drawable.ic_alcohol;
		questions = new String[]{
				"wheelchair",
				"cash",
				"cheques",
				"credit_card",
				"debit_card", "contactless"
		};
	}
}
