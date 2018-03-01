package com.teester.whatsnearby.data.questions;

import com.teester.whatsnearby.R;

public class Contactless extends Question {

	public Contactless() {
		question = R.string.contactless;
		drawable = R.drawable.ic_credit_card;
		color = R.color.green;
		tag = "payment:contactless";
	}

}
