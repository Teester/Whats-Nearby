package com.teester.whatsnearby.data;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by mark on 15/10/17.
 */
public class AnswersTest {

	@Before
	public void setUp() {
		QuestionObject questionObject = new QuestionObject(1, 1, 1, 1, "", "", "", "");
		OsmObject osmObject = new OsmObject(1, "", "", "", 1, 1, 1);
		Answer answer = new Answer(questionObject, "yes");

		Answers.setPoiDetails(osmObject);
		Answers.addAnswer(answer);
	}

	@Test
	public void add_answer_to_new_set_of_answers() {
		QuestionObject expectedQuestion = new QuestionObject(1, 1, 1, 1, "", "", "", "");
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
}