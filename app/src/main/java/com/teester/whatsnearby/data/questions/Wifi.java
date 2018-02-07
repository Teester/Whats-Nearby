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
}
