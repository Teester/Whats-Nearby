package com.teester.whatsnearby.data.questions;

public interface QuestionsContract {

	int getQuestion();

	int getDrawable();

	String getTag();

	void setTag(String tag);

	String getAnswer_yes();

	void setAnswer_yes(String answer_yes);

	String getAnswer_no();

	void setAnswer_no(String answer_no);

	String getAnswer_unsure();

	void setAnswer_unsure(String answer_unsure);

	int getIcon();

	int getColor();

	String getAnswer(String response);
}
