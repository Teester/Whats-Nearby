package com.teester.whatsnearby.data.questions;

public interface QuestionsContract {

	int getQuestion();

	int getDrawable();

	String getTag();

	void setTag(String tag);

	String getAnswerYes();

	void setAnswerYes(String answer_yes);

	String getAnswerNo();

	void setAnswerNo(String answer_no);

	String getAnswerUnsure();

	void setAnswerUnsure(String answer_unsure);

	int getIcon();

	int getColor();

	String getAnswer(String response);

	int getAnswerInt(String response);

	String checkPreviousAnswer(String answer);
}
