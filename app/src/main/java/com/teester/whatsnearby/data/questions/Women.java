package com.teester.whatsnearby.data.questions;

import com.teester.whatsnearby.R;
import com.teester.whatsnearby.data.QuestionObject;


public class Women implements QuestionsContract {

	private int id = 8;
	private int question = R.string.women;
	private int drawable = R.drawable.ic_women;
	private int color = R.color.red;
	private String tag = "women";
	private String answer_yes = "yes";
	private String answer_no = "no";
	private String answer_unsure = "";

	public QuestionObject getQuestionObject() {
		return new QuestionObject(id, question, drawable, color, tag, answer_yes, answer_no, answer_unsure);
	}
}
