package com.teester.whatsnearby.data.pois;

import com.teester.whatsnearby.R;
import com.teester.whatsnearby.data.questions.Cash;
import com.teester.whatsnearby.data.questions.Cheque;
import com.teester.whatsnearby.data.questions.Contactless;
import com.teester.whatsnearby.data.questions.CreditCard;
import com.teester.whatsnearby.data.questions.DebitCard;
import com.teester.whatsnearby.data.questions.OutdoorSeating;
import com.teester.whatsnearby.data.questions.Wheelchair;
import com.teester.whatsnearby.data.questions.WheelchairToilets;
import com.teester.whatsnearby.data.questions.Wifi;
import com.teester.whatsnearby.data.questions.WifiFee;

public class AmenityBar extends Poi {

	public AmenityBar() {
		objectName = "bar";
		objectClass = "amenity";
		objectIcon = R.drawable.ic_bar;

		questions.add(new Wheelchair());
		questions.add(new OutdoorSeating());
		questions.add(new Wifi());
		questions.add(new WifiFee());
		questions.add(new Cash());
		questions.add(new Cheque());
		questions.add(new CreditCard());
		questions.add(new DebitCard());
		questions.add(new Contactless());
		questions.add(new WheelchairToilets());
	}
}
