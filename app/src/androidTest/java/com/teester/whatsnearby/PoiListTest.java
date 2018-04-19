package com.teester.whatsnearby;

import android.content.Intent;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.teester.whatsnearby.data.OsmObject;
import com.teester.whatsnearby.data.PoiList;
import com.teester.whatsnearby.main.MainActivity;
import com.teester.whatsnearby.questions.QuestionsActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class PoiListTest {
	@Rule
	public ActivityTestRule<MainActivity> mainActivity = new ActivityTestRule<>(MainActivity.class, false, false);

	@Rule
	public ActivityTestRule<QuestionsActivity> questionsActivity = new ActivityTestRule<>(QuestionsActivity.class, false, false);

	private List<OsmObject> testList;

	@Before
	public void setUp() {
		PoiList.getInstance().clearPoiList();
		OsmObject object1 = new OsmObject(2, "", "a", "", 3, 3, 1);
		OsmObject object2 = new OsmObject(3, "", "b", "", 1, 1, 1);
		OsmObject object3 = new OsmObject(4, "", "c", "", 2, 2, 1);
		testList = new ArrayList<>();
		testList.add(object1);
		testList.add(object2);
		testList.add(object3);

		PoiList.getInstance().setPoiList(testList);
	}

	@After
	public void tearDown() {
		PoiList.getInstance().clearPoiList();
	}

	@Test
	public void checkThatPoiListIsSavedAndRestoredCorrectlyinMainActivity() {

		Intent intent = new Intent();
		List<OsmObject> initialPoiList = PoiList.getInstance().getPoiList();
		mainActivity.launchActivity(intent);

		for (int i = 0; i < initialPoiList.size(); i++) {
			assertEquals(testList.get(i).getId(), initialPoiList.get(i).getId());
		}
		mainActivity.finishActivity();
		PoiList.getInstance().clearPoiList();
		List<OsmObject> emptyList = new ArrayList<>();
		assertEquals(emptyList, PoiList.getInstance().getPoiList());

		mainActivity.launchActivity(intent);
		List<OsmObject> finalPoiList = PoiList.getInstance().getPoiList();

		for (int i = 0; i < finalPoiList.size(); i++) {
			assertEquals(testList.get(i).getId(), finalPoiList.get(i).getId());
		}
	}

	@Test
	public void checkThatPoiListIsSavedAndRestoredCorrectlyinQuestionsActivity() {

		Intent intent = new Intent();
		List<OsmObject> initialPoiList = PoiList.getInstance().getPoiList();
		questionsActivity.launchActivity(intent);

		for (int i = 0; i < initialPoiList.size(); i++) {
			assertEquals(testList.get(i).getId(), initialPoiList.get(i).getId());
		}
		questionsActivity.finishActivity();
		PoiList.getInstance().clearPoiList();
		List<OsmObject> emptyList = new ArrayList<>();
		assertEquals(emptyList, PoiList.getInstance().getPoiList());

		questionsActivity.launchActivity(intent);
		List<OsmObject> finalPoiList = PoiList.getInstance().getPoiList();

		for (int i = 0; i < finalPoiList.size(); i++) {
			assertEquals(testList.get(i).getId(), finalPoiList.get(i).getId());
		}
	}
}
