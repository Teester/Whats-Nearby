package com.teester.whatsnearby.data.pois;

import com.teester.whatsnearby.R;

public class ShopChemist extends Poi {

	public ShopChemist() {
		objectName = "chemist";
		objectClass = "shop";
		objectIcon = R.drawable.ic_chemist;
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
