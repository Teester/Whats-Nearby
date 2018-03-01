package com.teester.whatsnearby.data;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class AnswersTest {

	private QuestionObject questionObject = new QuestionObject(1, 1, 1, 1, "", "", "", "");
	private OsmObject osmObject = new OsmObject(1, "", "", "", 1, 1, 1);

	@Before
	public void setUp() {
		Map<String, String> changesetTags = new HashMap<>();
		changesetTags.put("", "");

		Answers.setChangesetTags(changesetTags);
		Answers.setPoiDetails(osmObject);

		String answerTag = questionObject.getAnswer("yes");
		String questionTag = questionObject.getTag();
		Answers.addAnswer(questionTag, answerTag);
	}

	@After
	public void tearDown() {
		Answers.clearAnswerList();
	}

	@Test
	public void getKeyFromTag() {
		String tag = this.questionObject.getTag();

		String expectedValue = this.questionObject.getAnswerYes();
		String actualValue = Answers.getAnswerMap().get(tag);

		assertEquals(expectedValue, actualValue);
	}

	@Test
	public void getPoiDetails() {
		long expectedId = 1;
		long actualID = Answers.getPoiId();

		String expectedName = "";
		String actualName = Answers.getPoiName();

		String expectedPoiType = "";
		String actualPoiType = Answers.getPoiType();

		assertEquals(expectedId, actualID);
		assertEquals(expectedName, actualName);
		assertEquals(expectedPoiType, actualPoiType);
	}

	@Test
	public void checkAnswerListIsCleared() {
		Answers.clearAnswerList();
		Map<String, String> actualResult = Answers.getAnswerMap();
		assertEquals(0, actualResult.size());
	}

	@Test
	public void checkChangesetTagsAreSet() {
		Map<String, String> actualTags = Answers.getChangesetTags();
		Map<String, String> expectedTags = new HashMap<>();
		expectedTags.put("", "");

		assertEquals(expectedTags, actualTags);
	}
}