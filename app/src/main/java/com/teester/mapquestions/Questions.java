package com.teester.mapquestions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * The list of questions available
 */

public class Questions {

	public QuestionObject cash = new QuestionObject(13, R.string.cash, R.drawable.ic_unsure, R.color.green, "payment:cash", "yes", "no", "");
	public QuestionObject cheques = new QuestionObject(14, R.string.cheques, R.drawable.ic_unsure, R.color.green, "payment:cheque", "yes", "no", "");
	public QuestionObject contactless = new QuestionObject(17, R.string.contactless, R.drawable.ic_unsure, R.color.green, "payment:contactless", "yes", "no", "");
	public QuestionObject credit_card = new QuestionObject(15, R.string.credit_card, R.drawable.ic_unsure, R.color.green, "payment:credit_cards", "yes", "no", "");
	public QuestionObject debit_card = new QuestionObject(16, R.string.debit_card, R.drawable.ic_unsure, R.color.green, "payment:debit_cards", "yes", "no", "");
	public QuestionObject deliver = new QuestionObject(11, R.string.deliver, R.drawable.ic_unsure, R.color.purple, "deliver", "yes", "no", "");
	public QuestionObject dispensing = new QuestionObject(18, R.string.dispensing, R.drawable.ic_unsure, R.color.turquoise, "dispensing", "yes", "no", "");
	public QuestionObject drive_through = new QuestionObject(9, R.string.drive_through, R.drawable.ic_unsure, R.color.purple, "drive_through", "yes", "no", "");
	public QuestionObject halal = new QuestionObject(18, R.string.halal, R.drawable.ic_unsure, R.color.red, "halal", "yes", "no", "");
	public QuestionObject kosher = new QuestionObject(18, R.string.kosher, R.drawable.ic_unsure, R.color.red, "kosher", "yes", "no", "");
	public QuestionObject men = new QuestionObject(7, R.string.men, R.drawable.ic_men, R.color.red, "men", "yes", "no", "");
	public QuestionObject organic = new QuestionObject(7, R.string.organic, R.drawable.ic_unsure, R.color.red, "organic", "yes", "no", "");
	public QuestionObject outdoor_seating = new QuestionObject(2, R.string.outdoor_seating, R.drawable.ic_unsure, R.color.red, "outdoor_seating", "yes", "no", "");
	public QuestionObject reservation = new QuestionObject(12, R.string.reservation, R.drawable.ic_unsure, R.color.purple, "reservation", "yes", "no", "");
	public QuestionObject takeaway = new QuestionObject(10, R.string.takeaway, R.drawable.ic_unsure, R.color.purple, "takeaway", "yes", "no", "");
	public QuestionObject vegan = new QuestionObject(6, R.string.vegan, R.drawable.ic_unsure, R.color.blue, "vegan", "yes", "no", "");
	public QuestionObject vegetarian = new QuestionObject(5, R.string.vegetarian, R.drawable.ic_unsure, R.color.blue, "vegetarian", "yes", "no", "");
	public QuestionObject wheelchair = new QuestionObject(0, R.string.wheelchair, R.drawable.ic_wheelchair, R.color.orange, "wheelchair", "yes", "no", "");
	public QuestionObject wheelchair_toilets = new QuestionObject(1, R.string.wheelchair_toilets, R.drawable.ic_wheelchair, R.color.orange, "wheelchair_toilets", "yes", "no", "");
	public QuestionObject wifi = new QuestionObject(3, R.string.wifi, R.drawable.ic_wifi, R.color.orange, "wifi", "yes", "no", "");
	public QuestionObject wifi_fee = new QuestionObject(4, R.string.wifi_fee, R.drawable.ic_wifi, R.color.orange, "wifi:fee", "yes", "no", "");
	public QuestionObject women = new QuestionObject(8, R.string.women, R.drawable.ic_women, R.color.red, "women", "yes", "no", "");

	Map<String, QuestionObject> map = new HashMap<>();

	public Questions() {
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

	public QuestionObject getQuestion(String question) {
		return map.get(question);
	}
}
