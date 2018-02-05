package com.teester.whatsnearby.data.questions;

import com.teester.whatsnearby.R;
import com.teester.whatsnearby.data.QuestionObject;

public class Cheque implements QuestionsContract {

	private int id = 14;
	private int question = R.string.cheques;
	private int drawable = R.drawable.ic_unsure;
	private int color = R.color.green;
	private String tag = "payment:cheque";
	private String answer_yes = "yes";
	private String answer_no = "no";
	private String answer_unsure = "";

	public QuestionObject getQuestionObject() {
		return new QuestionObject(id, question, drawable, color, tag, answer_yes, answer_no, answer_unsure);
	}
}
