package com.teester.whatsnearby.data;

import com.teester.whatsnearby.data.questions.Halal;
import com.teester.whatsnearby.data.questions.Kosher;
import com.teester.whatsnearby.data.questions.Question;
import com.teester.whatsnearby.data.questions.QuestionsContract;
import com.teester.whatsnearby.data.questions.Wifi;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class AnswersTest {

	private QuestionsContract questionObject = new SampleQuestion();
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

	@Test
	public void testForCustomResponsesHalal() {
		Answers.clearAnswerList();
		QuestionsContract halal = new Halal();
		String answerTag = halal.getAnswer("yes");
		String questionTag = halal.getTag();
		Answers.addAnswer(questionTag, answerTag);

		String expectedValue = halal.getAnswerYes();
		String actualValue = Answers.getAnswerMap().get(halal.getTag());

		assertEquals(expectedValue, actualValue);
	}

	@Test
	public void testForCustomResponsesKosher() {
		Answers.clearAnswerList();
		QuestionsContract kosher = new Kosher();
		String answerTag = kosher.getAnswer("yes");
		String questionTag = kosher.getTag();
		Answers.addAnswer(questionTag, answerTag);

		String expectedValue = kosher.getAnswerYes();
		String actualValue = Answers.getAnswerMap().get(kosher.getTag());

		assertEquals(expectedValue, actualValue);
	}

	@Test
	public void testForCustomResponsesWifi() {
		Answers.clearAnswerList();
		QuestionsContract wifi = new Wifi();
		String answerTag = wifi.getAnswer("yes");
		String questionTag = wifi.getTag();
		Answers.addAnswer(questionTag, answerTag);

		String expectedValue = wifi.getAnswerYes();
		String actualValue = Answers.getAnswerMap().get(wifi.getTag());

		assertEquals(expectedValue, actualValue);
	}

	// A sample blank Question to use for tests
	private class SampleQuestion extends Question {
	}
}