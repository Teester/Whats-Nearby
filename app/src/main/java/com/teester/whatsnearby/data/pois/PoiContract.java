package com.teester.whatsnearby.data.pois;

import com.teester.whatsnearby.data.questions.QuestionsContract;

import java.util.List;

public interface PoiContract {
	String getObjectName();

	String getObjectClass();

	PoiContract getObjectType();

	int getObjectIcon();

	String[] getQuestions();

	void setQuestions(String[] questions);

	void shuffleQuestions();

	int getNoOfQuestions();

	List<QuestionsContract> getQuestion();
}
