package com.teester.whatsnearby.data.pois;

import com.teester.whatsnearby.R;

public class ShopDoItYourself extends Poi {

	public ShopDoItYourself() {
		objectName = "doityourself";
		objectClass = "shop";
		objectIcon = R.drawable.ic_doityourself;
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
