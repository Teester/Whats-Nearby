package com.teester.whatsnearby.data.pois;

import com.teester.whatsnearby.data.PoiTypes;
import com.teester.whatsnearby.data.questions.QuestionsContract;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Poi implements PoiContract {

	public String objectName;
	public String objectClass;
	public int objectIcon;
	public ArrayList<QuestionsContract> questions = new ArrayList<>();

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
	public ArrayList<QuestionsContract> getQuestions() {
		return this.questions;
	}

	@Override
	public void setQuestions(ArrayList<QuestionsContract> questions) {
		this.questions = questions;
	}

	@Override
	public void shuffleQuestions() {
		Collections.shuffle(this.questions);
	}

	@Override
	public int getNoOfQuestions() {
		return this.questions.size();
	}

	@Override
	public List<QuestionsContract> getQuestion() {
		return this.questions;
	}
}
