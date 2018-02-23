package com.teester.whatsnearby.data.questions;

import com.teester.whatsnearby.R;

public class Kosher extends Question {

	public Kosher() {
		question = R.string.kosher;
		drawable = R.drawable.ic_kosher;
		color = R.color.red;
		tag = "butcher";
		answer_yes = "kosher";
		answer_no = "";
	}

	@Override
	public String checkPreviousAnswer(String answer) {
		String response;
		switch (answer) {
			case "kosher":
				response = "yes";
				break;
			case "":
				response = "unsure";
				break;
			default:
				response = "no";
		}
		return super.checkPreviousAnswer(response);
	}
}
