package com.teester.whatsnearby.data.pois;

import com.teester.whatsnearby.R;
import com.teester.whatsnearby.data.questions.Cash;
import com.teester.whatsnearby.data.questions.Cheque;
import com.teester.whatsnearby.data.questions.Contactless;
import com.teester.whatsnearby.data.questions.CreditCard;
import com.teester.whatsnearby.data.questions.DebitCard;
import com.teester.whatsnearby.data.questions.Halal;
import com.teester.whatsnearby.data.questions.Kosher;
import com.teester.whatsnearby.data.questions.Organic;
import com.teester.whatsnearby.data.questions.Wheelchair;

public class ShopButcher extends Poi {

	public ShopButcher() {
		objectName = "butcher";
		objectClass = "shop";
		objectIcon = R.drawable.ic_butcher;

		questions.add(new Wheelchair());
		questions.add(new Cash());
		questions.add(new Cheque());
		questions.add(new CreditCard());
		questions.add(new DebitCard());
		questions.add(new Contactless());
		questions.add(new Halal());
		questions.add(new Kosher());
		questions.add(new Organic());
	}
}
