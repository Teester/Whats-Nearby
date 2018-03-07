package com.teester.whatsnearby.data.pois;

import com.teester.whatsnearby.R;

public class ShopOptician extends Poi {

	public ShopOptician() {
		objectName = "optician";
		objectClass = "shop";
		objectIcon = R.drawable.ic_optician;
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
