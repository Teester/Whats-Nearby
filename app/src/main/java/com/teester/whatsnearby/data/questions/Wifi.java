package com.teester.whatsnearby.data.questions;

import com.teester.whatsnearby.R;
import com.teester.whatsnearby.data.QuestionObject;

/**
 * Created by mark on 05/02/18.
 */

public class Wifi implements QuestionsContract {

	private int id = 14;
	private int question = R.string.wifi;
	private int drawable = R.drawable.ic_wifi;
	private int color = R.color.orange;
	private String tag = "internet_access";
	private String answer_yes = "wlan";
	private String answer_no = "no";
	private String answer_unsure = "";

	public QuestionObject getQuestionObject() {
		return new QuestionObject(id, question, drawable, color, tag, answer_yes, answer_no, answer_unsure);
	}
}
