package com.teester.whatsnearby.data.pois;

import com.teester.whatsnearby.R;

public class ShopPerfumery extends Poi {

	public ShopPerfumery() {
		objectName = "optician";
		objectClass = "shop";
		objectIcon = R.drawable.ic_optician;
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
