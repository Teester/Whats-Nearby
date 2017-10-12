package com.teester.whatsnearby.data;

/**
 * Answers to questions
 */

public class Answer {

	QuestionObject question;
	String answer;

	// TODO: Need an array of questions and answers: {[Q1, A1], [Q2, A2]} --> ArrayList<Answer>?
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
