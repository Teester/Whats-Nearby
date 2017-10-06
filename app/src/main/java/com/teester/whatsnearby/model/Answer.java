package com.teester.whatsnearby.model;

/**
 * Answers to questions
 */

public class Answer {

	long objectId;
	String objectName;
	QuestionObject question;
	String answer;
	String objectType;

	// TODO: Need an array of questions and answers: {[Q1, A1], [Q2, A2]} --> ArrayList<Answer>?
	public Answer(OsmObject osmObject, QuestionObject question, String answer) {
		this.objectId = osmObject.getId();
		this.objectName = osmObject.getName();
		this.objectType = osmObject.getOsmType();
		this.question = question;
		this.answer = answer;
	}

	public long getObjectId() {
		return objectId;
	}

	public String getObjectType() {
		return objectType;
	}

	public QuestionObject getQuestion() {
		return question;
	}

	public String getAnswer() {
		return answer;
	}

	public String getObjectName() {
		return objectName;
	}
}
