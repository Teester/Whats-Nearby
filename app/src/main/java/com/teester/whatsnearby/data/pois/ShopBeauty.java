package com.teester.whatsnearby.data.pois;

import com.teester.whatsnearby.R;

public class ShopBeauty extends Poi {

	public ShopBeauty() {
		objectName = "beauty";
		objectClass = "shop";
		objectIcon = R.drawable.ic_beauty;
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
