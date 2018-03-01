package com.teester.whatsnearby.data.questions;

import com.teester.whatsnearby.R;

public class Cheque extends Question {

	public Cheque() {
		question = R.string.cheques;
		drawable = R.drawable.ic_check;
		color = R.color.green;
		tag = "payment:cheque";
	}
}
