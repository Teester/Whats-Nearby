package com.teester.whatsnearby.data.pois;

import com.teester.whatsnearby.R;
import com.teester.whatsnearby.data.questions.Cash;
import com.teester.whatsnearby.data.questions.Cheque;
import com.teester.whatsnearby.data.questions.Contactless;
import com.teester.whatsnearby.data.questions.CreditCard;
import com.teester.whatsnearby.data.questions.DebitCard;
import com.teester.whatsnearby.data.questions.Men;
import com.teester.whatsnearby.data.questions.Wheelchair;
import com.teester.whatsnearby.data.questions.Women;

public class ShopHairdresser extends Poi {

	public ShopHairdresser() {
		objectName = "hairdresser";
		objectClass = "shop";
		objectIcon = R.drawable.ic_hairdresser;

		questions.add(new Wheelchair());
		questions.add(new Cash());
		questions.add(new Cheque());
		questions.add(new CreditCard());
		questions.add(new DebitCard());
		questions.add(new Contactless());
		questions.add(new Men());
		questions.add(new Women());
	}
}
