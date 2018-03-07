package com.teester.whatsnearby.data.pois;

import com.teester.whatsnearby.R;

public class ShopClothes extends Poi {

	public ShopClothes() {
		objectName = "clothes";
		objectClass = "shop";
		objectIcon = R.drawable.ic_clothes;
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
