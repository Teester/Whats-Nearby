package com.teester.whatsnearby.data.pois;

import com.teester.whatsnearby.R;

public class ShopVeterinary extends Poi {

	public ShopVeterinary() {
		objectName = "veterinary";
		objectClass = "shop";
		objectIcon = R.drawable.ic_veterinary;
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
