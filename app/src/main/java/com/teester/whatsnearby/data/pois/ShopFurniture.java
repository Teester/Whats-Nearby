package com.teester.whatsnearby.data.pois;

import com.teester.whatsnearby.R;

public class ShopFurniture extends Poi {

	public ShopFurniture() {
		objectName = "furniture";
		objectClass = "shop";
		objectIcon = R.drawable.ic_furniture;
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
