package com.teester.whatsnearby.data.pois;

import com.teester.whatsnearby.R;

public class ShopCosmetics extends Poi {

	public ShopCosmetics() {
		objectName = "cosmetics";
		objectClass = "shop";
		objectIcon = R.drawable.ic_cosmetics;
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
