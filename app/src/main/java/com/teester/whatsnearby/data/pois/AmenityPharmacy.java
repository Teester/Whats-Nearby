package com.teester.whatsnearby.data.pois;

import com.teester.whatsnearby.R;
import com.teester.whatsnearby.data.questions.Cash;
import com.teester.whatsnearby.data.questions.Cheque;
import com.teester.whatsnearby.data.questions.Contactless;
import com.teester.whatsnearby.data.questions.CreditCard;
import com.teester.whatsnearby.data.questions.DebitCard;
import com.teester.whatsnearby.data.questions.Dispensing;
import com.teester.whatsnearby.data.questions.Wheelchair;

public class AmenityPharmacy extends Poi {

	public AmenityPharmacy() {
		objectName = "pharmacy";
		objectClass = "amenity";
		objectIcon = R.drawable.ic_pharmacy;

		questions.add(new Wheelchair());
		questions.add(new Dispensing());
		questions.add(new Cash());
		questions.add(new Cheque());
		questions.add(new CreditCard());
		questions.add(new DebitCard());
		questions.add(new Contactless());
	}
}
