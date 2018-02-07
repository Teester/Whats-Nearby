package com.teester.whatsnearby.data;

import com.teester.whatsnearby.data.questions.Cash;
import com.teester.whatsnearby.data.questions.Cheque;
import com.teester.whatsnearby.data.questions.Contactless;
import com.teester.whatsnearby.data.questions.CreditCard;
import com.teester.whatsnearby.data.questions.DebitCard;
import com.teester.whatsnearby.data.questions.Deliver;
import com.teester.whatsnearby.data.questions.Dispensing;
import com.teester.whatsnearby.data.questions.DriveThrough;
import com.teester.whatsnearby.data.questions.Halal;
import com.teester.whatsnearby.data.questions.Kosher;
import com.teester.whatsnearby.data.questions.Men;
import com.teester.whatsnearby.data.questions.Organic;
import com.teester.whatsnearby.data.questions.OutdoorSeating;
import com.teester.whatsnearby.data.questions.Question;
import com.teester.whatsnearby.data.questions.Reservation;
import com.teester.whatsnearby.data.questions.Takeaway;
import com.teester.whatsnearby.data.questions.Vegan;
import com.teester.whatsnearby.data.questions.Vegetarian;
import com.teester.whatsnearby.data.questions.Wheelchair;
import com.teester.whatsnearby.data.questions.WheelchairToilets;
import com.teester.whatsnearby.data.questions.Wifi;
import com.teester.whatsnearby.data.questions.WifiFee;
import com.teester.whatsnearby.data.questions.Women;

import java.util.HashMap;
import java.util.Map;

/**
 * The list of questions available
 */

public class Questions {

	private static Questions INSTANCE;
	private Map<String, Question> map = new HashMap<>();

	private Questions() {
		map.put("cash", new Cash());
		map.put("cheques", new Cheque());
		map.put("contactless", new Contactless());
		map.put("credit_card", new CreditCard());
		map.put("debit_card", new DebitCard());
		map.put("deliver", new Deliver());
		map.put("dispensing", new Dispensing());
		map.put("drive_through", new DriveThrough());
		map.put("halal", new Halal());
		map.put("kosher", new Kosher());
		map.put("men", new Men());
		map.put("organic", new Organic());
		map.put("outdoor_seating", new OutdoorSeating());
		map.put("reservation", new Reservation());
		map.put("takeaway", new Takeaway());
		map.put("vegan", new Vegan());
		map.put("vegetarian", new Vegetarian());
		map.put("wheelchair", new Wheelchair());
		map.put("wheelchair_toilets", new WheelchairToilets());
		map.put("wifi", new Wifi());
		map.put("wifi_fee", new WifiFee());
		map.put("women", new Women());
	}
	private static synchronized Questions getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new Questions();
		}
		return INSTANCE;
	}

	public static Question getQuestion(String question) {
		getInstance();
		return INSTANCE.map.get(question);
	}
}
