package com.teester.whatsnearby.data.pois;

import com.teester.whatsnearby.R;

public class ShopLaundry extends Poi {

	public ShopLaundry() {
		objectName = "laundry";
		objectClass = "shop";
		objectIcon = R.drawable.ic_laundry;
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
