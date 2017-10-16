package com.teester.whatsnearby.data;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class AnswersTest {

	QuestionObject questionObject = new QuestionObject(1, 1, 1, 1, "", "", "", "");
	OsmObject osmObject = new OsmObject(1, "", "", "", 1, 1, 1);

	@Before
	public void setUp() {
		Answer answer = new Answer(questionObject, "yes");
		Map<String, String> changesetTags = new HashMap<>();
		changesetTags.put("", "");

		Answers.setChangesetTags(changesetTags);
		Answers.setPoiDetails(osmObject);
		Answers.addAnswer(answer);
	}

	@After
	public void tearDown() {
		Answers.clearAnswerList();
	}

	@Test
	public void get_answer_from_answers() {
		QuestionObject expectedQuestion = this.questionObject;
		QuestionObject actualQuestion = Answers.getAnswerList().get(0).getQuestion();

		String expectedAnswer = "yes";
		String actualAnswer = Answers.getAnswerList().get(0).getAnswer();

		assertEquals(expectedQuestion.getQuestion(), actualQuestion.getQuestion());
		assertEquals(expectedAnswer, actualAnswer);
	}

	@Test
	public void get_poi_details() {
		int expectedId = 1;
		int actualID = Answers.getPoiId();

		String expectedName = "";
		String actualName = Answers.getPoiName();

		String expectedPoiType = "";
		String actualPoiType = Answers.getPoiType();

		assertEquals(expectedId, actualID);
		assertEquals(expectedName, actualName);
		assertEquals(expectedPoiType, actualPoiType);
	}

	@Test
	public void check_answer_list_is_cleared() {
		Answers.clearAnswerList();
		List<Answer> actualResult = Answers.getAnswerList();

		assertEquals(0, actualResult.size());
	}

	@Test
	public void check_changeset_tags_are_set() {
		Map<String, String> actualTags = Answers.getChangesetTags();
		Map<String, String> expectedTags = new HashMap<>();
		expectedTags.put("", "");

		assertEquals(expectedTags, actualTags);
	}
}