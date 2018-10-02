package com.teester.whatsnearby.data.pois;

import com.teester.whatsnearby.R;
import com.teester.whatsnearby.data.questions.Cash;
import com.teester.whatsnearby.data.questions.Cheque;
import com.teester.whatsnearby.data.questions.Contactless;
import com.teester.whatsnearby.data.questions.CreditCard;
import com.teester.whatsnearby.data.questions.DebitCard;
import com.teester.whatsnearby.data.questions.Wheelchair;
import com.teester.whatsnearby.data.questions.WheelchairToilets;

/**
 * Created by mark on 07/03/18.
 */

public class AmenityCinema extends Poi {

	public AmenityCinema() {
		objectName = "cinema";
		objectClass = "amenity";
		objectIcon = R.drawable.ic_cinema;

		questions.add(new Wheelchair());
		questions.add(new Cash());
		questions.add(new Cheque());
		questions.add(new CreditCard());
		questions.add(new DebitCard());
		questions.add(new Contactless());
		questions.add(new WheelchairToilets());
	}
}
