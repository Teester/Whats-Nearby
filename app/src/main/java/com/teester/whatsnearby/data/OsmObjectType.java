package com.teester.whatsnearby.data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class OsmObjectType {

	private String objectName;
	private String objectClass;
	private int objectIcon;
	private String[] questions;

	public OsmObjectType(String objectName, String objectClass, int objectIcon, String[] questions) {
		this.objectName = objectName;
		this.objectClass = objectClass;
		this.objectIcon = objectIcon;
		this.questions = questions;
	}

	public String getObjectName() {
		return this.objectName;
	}

	public String getObjectClass() {
		return this.objectClass;
	}

	public OsmObjectType getObjectType() {
		return PoiTypes.getPoiType(this.objectClass);
	}

	public int getObjectIcon() {
		return this.objectIcon;
	}

	public String[] getQuestions() {
		return this.questions;
	}

	public void setQuestions(String[] questions) {
		this.questions = questions;
	}

	public void shuffleQuestions() {
		String[] questions = this.questions;
		List<String> strList = Arrays.asList(questions);
		Collections.shuffle(strList);
		questions = strList.toArray(new String[strList.size()]);
		this.questions = questions;;
	}

	public int getNoOfQuestions() {
		return this.questions.length;
	}

	public List<QuestionObject> getQuestionObjects() {
		List<QuestionObject> k = new ArrayList<QuestionObject>();
		for (int i=0; i < questions.length; i++) {
			QuestionObject question = Questions.getQuestion(questions[i]);
			k.add(question);
		}
		return k;
	}
}
