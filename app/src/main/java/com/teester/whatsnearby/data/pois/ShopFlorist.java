package com.teester.whatsnearby.data.pois;

import com.teester.whatsnearby.R;

public class ShopFlorist extends Poi {

	public ShopFlorist() {
		objectName = "florist";
		objectClass = "shop";
		objectIcon = R.drawable.ic_florist;
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
