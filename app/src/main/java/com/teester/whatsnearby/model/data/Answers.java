package com.teester.whatsnearby.model.data;

import com.teester.whatsnearby.model.Answer;

import java.util.ArrayList;

/**
 * A Singleton to store answers in preparation for uploading
 */
public class Answers {

	private static Answers INSTANCE;
	private ArrayList<Answer> answerList = new ArrayList<>();

	private Answers() {

	}

	public static synchronized Answers getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new Answers();
		}
		return INSTANCE;
	}

	/**
	 * Get the list of answers
	 *
	 * @return
	 */
	public ArrayList<Answer> getAnswerList() {
		return answerList;
	}

	/**
	 * Add an answer to the list
	 *
	 * @param answer
	 */
	public void addAnswer(Answer answer) {
		answerList.add(answer);
	}

	/**
	 * Clear the list of answers
	 */
	public void clearAnswerList() {
		INSTANCE = null;
	}

}
