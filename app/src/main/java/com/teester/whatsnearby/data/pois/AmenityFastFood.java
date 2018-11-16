package com.teester.whatsnearby.data.pois;

import com.teester.whatsnearby.R;
import com.teester.whatsnearby.data.questions.Cash;
import com.teester.whatsnearby.data.questions.Cheque;
import com.teester.whatsnearby.data.questions.Contactless;
import com.teester.whatsnearby.data.questions.CreditCard;
import com.teester.whatsnearby.data.questions.DebitCard;
import com.teester.whatsnearby.data.questions.Deliver;
import com.teester.whatsnearby.data.questions.DriveThrough;
import com.teester.whatsnearby.data.questions.Takeaway;
import com.teester.whatsnearby.data.questions.Wheelchair;
import com.teester.whatsnearby.data.questions.WheelchairToilets;

public class AmenityFastFood extends Poi {

	public AmenityFastFood() {
		objectName = "fast_food";
		objectClass = "amenity";
		objectIcon = R.drawable.ic_fast_food;

		questions.add(new Wheelchair());
		questions.add(new DriveThrough());
		questions.add(new Takeaway());
		questions.add(new Deliver());
		questions.add(new Cash());
		questions.add(new Cheque());
		questions.add(new CreditCard());
		questions.add(new DebitCard());
		questions.add(new Contactless());
		questions.add(new WheelchairToilets());
	}
}
