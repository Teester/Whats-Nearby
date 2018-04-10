package com.teester.whatsnearby.data.pois;

import com.teester.whatsnearby.data.PoiTypes;
import com.teester.whatsnearby.data.Questions;
import com.teester.whatsnearby.data.questions.QuestionsContract;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Poi implements PoiContract {

	public String objectName;
	public String objectClass;
	public int objectIcon;
	public String[] questions;

	@Override
	public String getObjectName() {
		return this.objectName;
	}

	@Override
	public String getObjectClass() {
		return this.objectClass;
	}

	@Override
	public PoiContract getObjectType() {
		return PoiTypes.getPoiType(this.objectClass);
	}

	@Override
	public int getObjectIcon() {
		return this.objectIcon;
	}

	@Override
	public String[] getQuestions() {
		return this.questions;
	}

	@Override
	public void setQuestions(String[] questions) {
		this.questions = questions;
	}

	@Override
	public void shuffleQuestions() {
		String[] questions = this.questions;
		List<String> strList = Arrays.asList(questions);
		Collections.shuffle(strList);
		questions = strList.toArray(new String[strList.size()]);
		this.questions = questions;
	}

	@Override
	public int getNoOfQuestions() {
		return this.questions.length;
	}

	@Override
	public List<QuestionsContract> getQuestion() {
        List<QuestionsContract> k = new ArrayList<>();
        for (String question1 : questions) {
            QuestionsContract question = Questions.getQuestion(question1);
			k.add(question);
		}
		return k;
	}
}
