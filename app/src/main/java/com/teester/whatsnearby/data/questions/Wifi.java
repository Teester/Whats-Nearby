package com.teester.whatsnearby.data.questions;

import com.teester.whatsnearby.R;

public class Wifi extends Question {

	public Wifi() {
		question = R.string.wifi;
		drawable = R.drawable.ic_wifi;
		color = R.color.orange;
		tag = "internet_access";
		answer_yes = "wlan";
	}

	@Override
	public String checkPreviousAnswer(String answer) {
		String response;
		switch (answer) {
			case "wlan":
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
