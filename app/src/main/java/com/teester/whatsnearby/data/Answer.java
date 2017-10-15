package com.teester.whatsnearby.data;

/**
 * Answers to questions
 */

public class Answer {

	QuestionObject question;
	String answer;

	public Answer(QuestionObject question, String answer) {
		this.question = question;
		this.answer = answer;
	}

	public QuestionObject getQuestion() {
		return question;
	}

	public String getAnswer() {
		return answer;
	}

}
