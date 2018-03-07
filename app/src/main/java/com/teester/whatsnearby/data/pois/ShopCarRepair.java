package com.teester.whatsnearby.data.pois;

import com.teester.whatsnearby.R;

public class ShopCarRepair extends Poi {

	public ShopCarRepair() {
		objectName = "car_repair";
		objectClass = "shop";
		objectIcon = R.drawable.ic_car_repair;
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
