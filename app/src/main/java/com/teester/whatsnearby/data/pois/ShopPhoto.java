package com.teester.whatsnearby.data.pois;

import com.teester.whatsnearby.R;

public class ShopPhoto extends Poi {

	public ShopPhoto() {
		objectName = "photo";
		objectClass = "shop";
		objectIcon = R.drawable.ic_photo;
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
