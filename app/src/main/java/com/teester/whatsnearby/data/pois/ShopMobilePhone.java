package com.teester.whatsnearby.data.pois;

import com.teester.whatsnearby.R;

public class ShopMobilePhone extends Poi {

	public ShopMobilePhone() {
		objectName = "mobile_phone";
		objectClass = "shop";
		objectIcon = R.drawable.ic_mobile_phone;
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
