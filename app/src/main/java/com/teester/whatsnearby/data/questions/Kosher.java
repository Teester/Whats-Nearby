package com.teester.whatsnearby.data.questions;

import com.teester.whatsnearby.R;
import com.teester.whatsnearby.data.QuestionObject;

/**
 * Created by mark on 05/02/18.
 */

public class Kosher implements QuestionsContract {
	private int id = 14;
	private int question = R.string.kosher;
	private int drawable = R.drawable.ic_kosher;
	private int color = R.color.red;
	private String tag = "kosher";
	private String answer_yes = "yes";
	private String answer_no = "no";
	private String answer_unsure = "";

	public QuestionObject getQuestionObject() {
		return new QuestionObject(id, question, drawable, color, tag, answer_yes, answer_no, answer_unsure);
	}
}
