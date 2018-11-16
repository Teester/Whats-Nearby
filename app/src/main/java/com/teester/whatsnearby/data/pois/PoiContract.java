package com.teester.whatsnearby.data.pois;

import com.teester.whatsnearby.data.questions.QuestionsContract;

import java.util.ArrayList;
import java.util.List;

public interface PoiContract {
	String getObjectName();

	String getObjectClass();

	PoiContract getObjectType();

	int getObjectIcon();

	ArrayList<QuestionsContract> getQuestions();

	void setQuestions(ArrayList<QuestionsContract> questions);

	void shuffleQuestions();

	int getNoOfQuestions();

	List<QuestionsContract> getQuestion();
}
