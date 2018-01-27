package com.teester.whatsnearby.questions;

import com.teester.whatsnearby.data.OsmObject;
import com.teester.whatsnearby.data.PoiList;
import com.teester.whatsnearby.data.source.SourceContract;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class QuestionsPresenterTest {

	QuestionsActivityContract.Presenter questionsPresenter;

	@Mock
	SourceContract.Preferences preferences;

	@Mock
	QuestionsActivityContract.View view;

	@Before
	public void setUp() {
		questionsPresenter = new QuestionsPresenter(view, preferences);
	}

	@Test
	public void AddPoiNameToTextViewTestSinglePoiInList() {
		List<OsmObject> poiList = new ArrayList<>();
		poiList.add(new OsmObject(1, "node", "", "restaurant", 1, 1, 1));
		PoiList.getInstance().setPoiList(poiList);

		when(preferences.getBooleanPreference(anyString())).thenReturn(true);

		questionsPresenter.addPoiNameToTextview();
		verify(view).makeTextViewInvisible();
	}

	@Test
	public void AddPoiNameToTextViewTestMultiplePoiInList() {
		List<OsmObject> poiList = new ArrayList<>();
		poiList.add(new OsmObject(1, "node", "Kitchen", "restaurant", 1, 1, 1));
		poiList.add(new OsmObject(1, "node", "", "restaurant", 1, 1, 1));
		PoiList.getInstance().setPoiList(poiList);

		when(preferences.getBooleanPreference(anyString())).thenReturn(true);

		questionsPresenter.addPoiNameToTextview();
		verify(view).setTextviewText("Kitchen");
	}

	@Test
	public void AddPoiNameToTextViewTestNotLoggedIn() {
		List<OsmObject> poiList = new ArrayList<>();
		poiList.add(new OsmObject(1, "node", "Kitchen", "restaurant", 1, 1, 1));
		poiList.add(new OsmObject(1, "node", "", "restaurant", 1, 1, 1));
		PoiList.getInstance().setPoiList(poiList);

		when(preferences.getBooleanPreference(anyString())).thenReturn(false);

		questionsPresenter.addPoiNameToTextview();
		verify(view).startNewActivity();
	}

	@Test
	public void assessIntentData() {
		URI uri = null;
		try {
			uri = new URI("http://www.openstreetmap.org/data?oauth_verifier=1&oauth_token=1");
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		questionsPresenter.assessIntentData(uri);

		verify(preferences).setStringPreference("oauth_verifier", "1");
		verify(preferences).setStringPreference("oauth_token", "1");
		verify(view).startOAuth();
	}
}