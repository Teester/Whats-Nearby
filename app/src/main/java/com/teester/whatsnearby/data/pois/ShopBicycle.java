package com.teester.whatsnearby.data.pois;

import com.teester.whatsnearby.R;

public class ShopBicycle extends Poi {

	public ShopBicycle() {
		objectName = "bicycle";
		objectClass = "shop";
		objectIcon = R.drawable.ic_bicycle;
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
