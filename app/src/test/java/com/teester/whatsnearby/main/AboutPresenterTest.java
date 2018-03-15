package com.teester.whatsnearby.main;

import com.teester.whatsnearby.BuildConfig;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class AboutPresenterTest {

	private MainActivityContract.AboutPresenter aboutPresenter;

	@Mock
	private MainActivityContract.AboutView view;

	@Before
	public void setUp() {
		aboutPresenter = new AboutPresenter(view);
	}

	@Test
	public void checkVersionNameIsSet() {
		aboutPresenter.findVersion();
		verify(view).setVersion(BuildConfig.VERSION_NAME);
	}

	@Test
	public void checkLicenceDetailsAreSet() {
		aboutPresenter.getLicence();
		verify(view).visitUri("http://www.gnu.org/licenses/gpl-3.0.html");
	}

	@Test
	public void checkSourceDetailsAreSet() {
		aboutPresenter.getGitHub();
		verify(view).visitUri("https://github.com/Teester/Whats-Nearby");
	}

}
