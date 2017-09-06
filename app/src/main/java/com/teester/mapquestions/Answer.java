package com.teester.mapquestions;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Answers to questions
 */

public class Answer implements Parcelable {

	long objectId;
	QuestionObject question;
	String answer;
	String objectType;

	// TODO: Need an array of questions and answers: {[Q1, A1], [Q2, A2]} --> ArrayList<Answer>?
	public Answer(long objectId, String objectType, QuestionObject question, String answer) {
		this.objectId = objectId;
		this.objectType = objectType;
		this.question = question;
		this.answer = answer;
	}

	protected Answer(Parcel in) {
		objectId = in.readLong();
		question = in.readParcelable(QuestionObject.class.getClassLoader());
		answer = in.readString();
		objectType = in.readString();
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeLong(objectId);
		dest.writeParcelable(question, flags);
		dest.writeString(answer);
		dest.writeString(objectType);
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


}
