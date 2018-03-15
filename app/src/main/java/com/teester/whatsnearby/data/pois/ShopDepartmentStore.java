package com.teester.whatsnearby.data.pois;

import com.teester.whatsnearby.R;

public class ShopDepartmentStore extends Poi {

	public ShopDepartmentStore() {
		objectName = "department_store";
		objectClass = "shop";
		objectIcon = R.drawable.ic_department_store;
		questions = new String[]{
				"wheelchair",
				"wheelchair_toilets",
				"cash",
				"cheques",
				"credit_card",
				"debit_card",
				"contactless"
		};
	}
}
