package com.teester.whatsnearby.data.questions;

import com.teester.whatsnearby.R;

public class Halal extends Question {

	public Halal() {
		question = R.string.halal;
		drawable = R.drawable.ic_halal;
		color = R.color.red;
		tag = "butcher";
		answer_yes = "halal";
		answer_no = "";
	}

	@Override
	public String checkPreviousAnswer(String answer) {
		String response;
		switch (answer) {
			case "halal":
				response = "yes";
				break;
			case "":
				response = "unsure";
				break;
			default:
				response = "no";
				break;
		}
		return super.checkPreviousAnswer(response);
	}
}
