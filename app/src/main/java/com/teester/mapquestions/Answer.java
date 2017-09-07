package com.teester.mapquestions;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Map;

/**
 * Answers to questions
 */

public class Answer implements Parcelable {

	long objectId;
	String objectName;
	QuestionObject question;
	String answer;
	String objectType;

	// TODO: Need an array of questions and answers: {[Q1, A1], [Q2, A2]} --> ArrayList<Answer>?
	public Answer(long objectId, String objectName, String objectType, QuestionObject question, String answer) {
		this.objectId = objectId;
		this.objectName = objectName;
		this.objectType = objectType;
		this.question = question;
		this.answer = answer;
	}

	public Answer(OsmObject osmObject, QuestionObject question, String answer) {
		this.objectId = osmObject.getId();
		this.objectName = osmObject.getName();
		this.objectType = osmObject.getOsmType();
		this.question = question;
		this.answer = answer;
	}

	protected Answer(Parcel in) {
		objectId = in.readLong();
		objectName = in.readString();
		objectType = in.readString();
		question = in.readParcelable(QuestionObject.class.getClassLoader());
		answer = in.readString();
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeLong(objectId);
		dest.writeString(objectName);
		dest.writeString(objectType);
		dest.writeParcelable(question, flags);
		dest.writeString(answer);
	}

	@Override
	public int describeContents() {
		return 0;
	}

	public static final Creator<Answer> CREATOR = new Creator<Answer>() {
		@Override
		public Answer createFromParcel(Parcel in) {
			return new Answer(in);
		}

		@Override
		public Answer[] newArray(int size) {
			return new Answer[size];
		}
	};

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
