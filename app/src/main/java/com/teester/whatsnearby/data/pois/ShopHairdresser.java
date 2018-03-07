package com.teester.whatsnearby.data.pois;

import com.teester.whatsnearby.R;

public class ShopHairdresser extends Poi {

	public ShopHairdresser() {
		objectName = "hairdresser";
		objectClass = "shop";
		objectIcon = R.drawable.ic_hairdresser;
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
