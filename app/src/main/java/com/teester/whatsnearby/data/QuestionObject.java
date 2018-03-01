package com.teester.whatsnearby.data;

/**
 * Details about the specific question
 */

public class QuestionObject {

	private int id;
	private int question;
	private int icon;
	private int color;
	private String tag;
	private String answerYes;
	private String answerNo;
	private String answerUnsure;

	public QuestionObject(int id, int question, int icon, int color, String tag, String answer_yes, String answer_no, String answer_unsure) {
		this.id = id;
		this.question = question;
		this.icon = icon;
		this.color = color;
		this.tag = tag;
		this.answerYes = answer_yes;
		this.answerNo = answer_no;
		this.answerUnsure = answer_unsure;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public String getAnswerYes() {
		return answerYes;
	}

	public void setAnswerYes(String answer) {
		this.answerYes = answer;
	}

	public String getAnswerNo() {
		return answerNo;
	}

	public void setAnswerNo(String answer) {
		this.answerNo = answer;
	}

	public String getAnswerUnsure() {
		return answerUnsure;
	}

	public void setAnswerUnsure(String answer) {
		this.answerUnsure = answer;
	}

	public int getId() {
		return this.id;
	}

	public int getQuestion() {
		return this.question;
	}

	public int getIcon() {
		return this.icon;
	}

	public int getColor() {
		return this.color;
	}

	public String getAnswer(String response) {
		String answer;
		switch (response) {
			case "yes":
				answer = this.answerYes;
				break;
			case "no":
				answer = this.answerNo;
				break;
			case "unsure":
				answer = this.answerUnsure;
				break;
			default:
				answer = "";
				break;
		}
		return answer;
	}
}
