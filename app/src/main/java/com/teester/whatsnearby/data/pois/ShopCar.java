package com.teester.whatsnearby.data.pois;

import com.teester.whatsnearby.R;

public class ShopCar extends Poi {

	public ShopCar() {
		objectName = "car";
		objectClass = "shop";
		objectIcon = R.drawable.ic_car;
		questions = new String[]{
				"wheelchair",
				"cash",
				"cheques",
				"credit_card",
				"debit_card"
		};
	}
}
