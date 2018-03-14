package com.teester.whatsnearby.questions.osmlogin;

import com.teester.whatsnearby.data.source.SourceContract;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class OsmLoginPresenterTest {

	private OsmLoginFragmentContract.Presenter presenter;

	@Mock
	private OsmLoginFragmentContract.View view;

	@Mock
	private SourceContract.Preferences preferences;

	@Before
	public void setUp() {
		presenter = new OsmLoginPresenter(view, preferences);
	}

	@Test
	public void checkPreferencesAreCleared() {
		presenter.clickedOsmLoginButton();

		verify(view).startOAuth();
	}
}
