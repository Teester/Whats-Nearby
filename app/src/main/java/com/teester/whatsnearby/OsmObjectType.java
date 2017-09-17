package com.teester.whatsnearby;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

class OsmObjectType implements Parcelable {
	private String objectName;
	private String objectClass;
	private int onjectIcon;
	private String[] questions;

	public OsmObjectType(String objectName, String objectClass, int objectIcon, String[] questions) {
		this.objectName = objectName;
		this.objectClass = objectClass;
		this.onjectIcon = objectIcon;
		this.questions = questions;
	}

	protected OsmObjectType(Parcel in) {
		objectName = in.readString();
		objectClass = in.readString();
		questions = in.createStringArray();
	}

	public static final Creator<OsmObjectType> CREATOR = new Creator<OsmObjectType>() {
		@Override
		public OsmObjectType createFromParcel(Parcel in) {
			return new OsmObjectType(in);
		}

		@Override
		public OsmObjectType[] newArray(int size) {
			return new OsmObjectType[size];
		}
	};

	public String getObjectName() {
		return this.objectName;
	}

	public String getObjectClass() {
		return this.objectClass;
	}

	public int getObjectIcon() {
		return this.onjectIcon;
	}

	public void setQuestions(String[] questions) {
		this.questions = questions;
	}

	public String[] getQuestions() {
		return this.questions;
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

	public ArrayList<QuestionObject> getQuestionObjects() {
		ArrayList<QuestionObject> k = new ArrayList<QuestionObject>();
		for (int i=0; i < questions.length; i++) {
			String question = questions[i];
			Questions questionslist = new Questions();
			QuestionObject newquestion = questionslist.getQuestion(question);
			k.add(newquestion);
		}
		return k;
	}

	public int getDrawable(Context context) {
		String drawableId = "ic_" + this.objectName;
		return  context.getResources().getIdentifier(drawableId, "drawable", context.getPackageName());
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel parcel, int i) {
		parcel.writeString(objectName);
		parcel.writeString(objectClass);
		parcel.writeStringArray(questions);
	}
}
