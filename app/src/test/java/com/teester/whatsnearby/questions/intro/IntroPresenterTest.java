package com.teester.whatsnearby.questions.intro;

import com.teester.whatsnearby.R;
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

import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class IntroPresenterTest {

	private IntroFragmentContract.Presenter introPresenter;

	@Mock
	private IntroFragmentContract.View view;

	@Before
	public void setUp() {
		List<OsmObject> poiList = new ArrayList<>();
		poiList.add(new OsmObject(1, "node", "Kitchen", "restaurant", 1, 1, 1));
		poiList.add(new OsmObject(1, "node", "", "restaurant", 1, 1, 1));
		PoiList.getInstance().setPoiList(poiList);

		introPresenter = new IntroPresenter(view);
	}

	@After
	public void tearDown() {
			PoiList.getInstance().clearPoiList();
		}

	@Test
	public void addPoiToTextview() {
		introPresenter.getDetails();
		verify(view).showDetails("Kitchen", "  ", R.drawable.ic_restaurant);
	}
}
