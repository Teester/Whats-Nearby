package com.teester.whatsnearby.questions.nothere;

import com.teester.whatsnearby.data.OsmObject;
import com.teester.whatsnearby.data.PoiList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class NotHerePresenterTest {

	NotHereFragmentContract.Presenter notHerePresenter;

	@Mock
	NotHereFragmentContract.View view;

	@Before
	public void setUp() {
		List<OsmObject> poiList = new ArrayList<>();
		poiList.add(new OsmObject(1, "node", "Kitchen", "restaurant", 1, 1, 1));
		poiList.add(new OsmObject(1, "node", "", "restaurant", 1, 1, 1));
		PoiList.getInstance().setPoiList(poiList);

		notHerePresenter = new NotHerePresenter(view);
	}

	@After
	public void tearDown() {
		PoiList.getInstance().clearPoiList();
	}

	@Test
	public void addPoiToTextview() {
		notHerePresenter.getPoiDetails();

		verify(view).setTextview("Kitchen");
		verify(view).setAdapter((List<OsmObject>) any());
	}

	@Test
	public void onClick() {
		notHerePresenter.onItemClicked(0);

		verify(view).startActivity();
	}
}