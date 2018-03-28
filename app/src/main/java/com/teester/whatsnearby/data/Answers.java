package com.teester.whatsnearby.data;

import java.util.HashMap;
import java.util.Map;

/**
 * A Singleton to store answers in preparation for uploading
 */
public class Answers {

	private static Answers INSTANCE;
	private static OsmObject osmObject;
	private static Map<String, String> answerMap = new HashMap<>();
	private static Map<String, String> changesetTags;

	private Answers() {

	}

	private static synchronized void getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new Answers();
		}
	}

	/**
	 * Get the list of answers
	 *
	 * @return the list of answers
	 */
	public static Map<String, String> getAnswerMap() {
		getInstance();
		return answerMap;
	}

	/**
	 * Add an answer to the list
	 *
	 * @param answer the answer (yes, no, unsure) to be added to the list
	 */
	public static void addAnswer(String question, String answer) {
		getInstance();
		answerMap.put(question, answer);
	}

	/**
	 * Clear the list of answers
	 */
	public static void clearAnswerList() {
		INSTANCE = null;
		answerMap = new HashMap<>();
	}

	public static void setPoiDetails(OsmObject osmObject) {
		getInstance();
		Answers.osmObject = osmObject;
	}

	public static long getPoiId() {
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

	public static Map<String, String> getChangesetTags() {
		getInstance();
		return changesetTags;
	}

	public static void setChangesetTags(Map<String, String> changesetTags) {
		getInstance();
		Answers.changesetTags = changesetTags;
	}
}
