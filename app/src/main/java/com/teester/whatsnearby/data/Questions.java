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

	private Map<String, QuestionObject> map = new HashMap<>();

	private Questions() {
		map.put("cash", new Cash().getQuestionObject());
		map.put("cheques", new Cheque().getQuestionObject());
		map.put("contactless", new Contactless().getQuestionObject());
		map.put("credit_card", new CreditCard().getQuestionObject());
		map.put("debit_card", new DebitCard().getQuestionObject());
		map.put("deliver", new Deliver().getQuestionObject());
		map.put("dispensing", new Dispensing().getQuestionObject());
		map.put("drive_through", new DriveThrough().getQuestionObject());
		map.put("halal", new Halal().getQuestionObject());
		map.put("kosher", new Kosher().getQuestionObject());
		map.put("men", new Men().getQuestionObject());
		map.put("organic", new Organic().getQuestionObject());
		map.put("outdoor_seating", new OutdoorSeating().getQuestionObject());
		map.put("reservation", new Reservation().getQuestionObject());
		map.put("takeaway", new Takeaway().getQuestionObject());
		map.put("vegan", new Vegan().getQuestionObject());
		map.put("vegetarian", new Vegetarian().getQuestionObject());
		map.put("wheelchair", new Wheelchair().getQuestionObject());
		map.put("wheelchair_toilets", new WheelchairToilets().getQuestionObject());
		map.put("wifi", new Wifi().getQuestionObject());
		map.put("wifi_fee", new WifiFee().getQuestionObject());
		map.put("women", new Women().getQuestionObject());
	}

	private static synchronized Questions getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new Questions();
		}
		return INSTANCE;
	}

	public static QuestionObject getQuestion(String question) {
		getInstance();
		return INSTANCE.map.get(question);
	}
}
