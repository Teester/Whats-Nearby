package com.teester.whatsnearby.main;

import com.teester.whatsnearby.data.source.SourceContract;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.net.URI;
import java.net.URISyntaxException;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class MainActivityPresenterTest {

	@Mock
	private MainActivityContract.View view;

	@Mock
	private SourceContract.Preferences preferences;

	private MainActivityContract.Presenter presenter;

	@Before
	public void setUp() {
		presenter = new MainActivityPresenter(view, preferences);
	}

	@Test
	public void logoutButtonClicked() {
		presenter.onButtonClicked();

		verify(preferences).setStringPreference(eq("oauth_verifier"), eq(""));
		verify(preferences).setStringPreference(eq("oauth_token"), eq(""));
		verify(preferences).setStringPreference(eq("oauth_token_secret"), eq(""));
		verify(view).startOAuth();
		verify(view).showIfLoggedIn(anyInt(), anyInt());
	}

	@Test
	public void checkIfInOAuthFlow() {
		URI url = null;
		try {
			url = new URI("http://www.teester.com/data=?oauth_verifier=1&oauth_token=2");
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		presenter.checkIfOauth(url);

		verify(view).startOAuth();
	}
}