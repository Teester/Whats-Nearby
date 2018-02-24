package com.teester.whatsnearby.data;

import com.teester.whatsnearby.R;

import java.util.HashMap;
import java.util.Map;

/**
 * The list of questions available
 */

public class Questions {

	private static Questions INSTANCE;
	private QuestionObject cash = new QuestionObject(13, R.string.cash, R.drawable.ic_unsure, R.color.green, "payment:cash", "yes", "no", "");
	private QuestionObject cheques = new QuestionObject(14, R.string.cheques, R.drawable.ic_unsure, R.color.green, "payment:cheque", "yes", "no", "");
	private QuestionObject contactless = new QuestionObject(17, R.string.contactless, R.drawable.ic_unsure, R.color.green, "payment:contactless", "yes", "no", "");
	private QuestionObject credit_card = new QuestionObject(15, R.string.credit_card, R.drawable.ic_unsure, R.color.green, "payment:credit_cards", "yes", "no", "");
	private QuestionObject debit_card = new QuestionObject(16, R.string.debit_card, R.drawable.ic_unsure, R.color.green, "payment:debit_cards", "yes", "no", "");
	private QuestionObject deliver = new QuestionObject(11, R.string.deliver, R.drawable.ic_unsure, R.color.purple, "delivery", "yes", "no", "");
	private QuestionObject dispensing = new QuestionObject(18, R.string.dispensing, R.drawable.ic_unsure, R.color.cyan, "dispensing", "yes", "no", "");
	private QuestionObject drive_through = new QuestionObject(9, R.string.drive_through, R.drawable.ic_unsure, R.color.purple, "drive_through", "yes", "no", "");
	private QuestionObject halal = new QuestionObject(18, R.string.halal, R.drawable.ic_halal, R.color.red, "butcher", "halal", "", "");
	private QuestionObject kosher = new QuestionObject(18, R.string.kosher, R.drawable.ic_kosher, R.color.red, "butcher", "kosher", "", "");
	private QuestionObject men = new QuestionObject(7, R.string.men, R.drawable.ic_men, R.color.red, "men", "yes", "no", "");
	private QuestionObject organic = new QuestionObject(7, R.string.organic, R.drawable.ic_unsure, R.color.red, "organic", "yes", "no", "");
	private QuestionObject outdoor_seating = new QuestionObject(2, R.string.outdoor_seating, R.drawable.ic_unsure, R.color.red, "outdoor_seating", "yes", "no", "");
	private QuestionObject reservation = new QuestionObject(12, R.string.reservation, R.drawable.ic_unsure, R.color.purple, "reservation", "yes", "no", "");
	private QuestionObject takeaway = new QuestionObject(10, R.string.takeaway, R.drawable.ic_unsure, R.color.purple, "takeaway", "yes", "no", "");
	private QuestionObject vegan = new QuestionObject(6, R.string.vegan, R.drawable.ic_unsure, R.color.blue, "diet:vegan", "yes", "no", "");
	private QuestionObject vegetarian = new QuestionObject(5, R.string.vegetarian, R.drawable.ic_unsure, R.color.blue, "diet:vegetarian", "yes", "no", "");
	private QuestionObject wheelchair = new QuestionObject(0, R.string.wheelchair, R.drawable.ic_wheelchair, R.color.orange, "wheelchair", "yes", "no", "");
	private QuestionObject wheelchair_toilets = new QuestionObject(1, R.string.wheelchair_toilets, R.drawable.ic_wheelchair, R.color.orange, "toilets:wheelchair", "yes", "no", "");
	private QuestionObject wifi = new QuestionObject(3, R.string.wifi, R.drawable.ic_wifi, R.color.orange, "internet_access", "wlan", "no", "");
	private QuestionObject wifi_fee = new QuestionObject(4, R.string.wifi_fee, R.drawable.ic_wifi, R.color.orange, "internet_access:fee", "yes", "no", "");
	private QuestionObject women = new QuestionObject(8, R.string.women, R.drawable.ic_women, R.color.red, "women", "yes", "no", "");
	private Map<String, QuestionObject> map = new HashMap<>();

	private Questions() {
		map.put("cash", this.cash);
		map.put("cheques", this.cheques);
		map.put("contactless", this.contactless);
		map.put("credit_card", this.credit_card);
		map.put("debit_card", this.debit_card);
		map.put("deliver", this.deliver);
		map.put("dispensing", this.dispensing);
		map.put("drive_through", this.drive_through);
		map.put("halal", this.halal);
		map.put("kosher", this.kosher);
		map.put("men", this.men);
		map.put("organic", this.organic);
		map.put("outdoor_seating", this.outdoor_seating);
		map.put("reservation", this.reservation);
		map.put("takeaway", this.takeaway);
		map.put("vegan", this.vegan);
		map.put("vegetarian", this.vegetarian);
		map.put("wheelchair", this.wheelchair);
		map.put("wheelchair_toilets", this.wheelchair_toilets);
		map.put("wifi", this.wifi);
		map.put("wifi_fee", this.wifi_fee);
		map.put("women", this.women);
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
