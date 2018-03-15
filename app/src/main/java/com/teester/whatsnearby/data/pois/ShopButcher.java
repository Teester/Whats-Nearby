package com.teester.whatsnearby.data.pois;

import com.teester.whatsnearby.R;

public class ShopButcher extends Poi {

	public ShopButcher() {
		objectName = "butcher";
		objectClass = "shop";
		objectIcon = R.drawable.ic_butcher;
		questions = new String[]{
				"wheelchair",
				"cash",
				"cheques",
				"credit_card",
				"debit_card",
				"contactless",
				"halal",
				"kosher",
				"organic"
		};
	}
}
