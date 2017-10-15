package com.teester.whatsnearby.data;

import java.util.ArrayList;
import java.util.List;

/**
 * A Singleton to store answers in preparation for uploading
 */
public class Answers {

	private static Answers INSTANCE;
	private static OsmObject osmObject;
	private static int id;
	private static String name;
	private static List<Answer> answerList = new ArrayList<Answer>();
	private static String objectType;

	private Answers() {

	}

	private static synchronized Answers getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new Answers();
		}
		return INSTANCE;
	}

	/**
	 * Get the list of answers
	 *
	 * @return
	 */
	public static List<Answer> getAnswerList() {
		getInstance();
		return answerList;
	}

	/**
	 * Add an answer to the list
	 *
	 * @param answer
	 */
	public static void addAnswer(Answer answer) {
		getInstance();
		answerList.add(answer);
	}

	/**
	 * Clear the list of answers
	 */
	public static void clearAnswerList() {
		INSTANCE = null;
		answerList = new ArrayList<Answer>();
	}

	public static void setPoiDetails(OsmObject osmObject) {
		getInstance();
		Answers.osmObject = osmObject;
	}

	public static int getPoiId() {
		getInstance();
		return osmObject.getId();
	}

	public static String getPoiName() {
		getInstance();
		return osmObject.getName();
	}

	public static String getPoiType() {
		getInstance();
		return osmObject.getOsmType();
	}
}
