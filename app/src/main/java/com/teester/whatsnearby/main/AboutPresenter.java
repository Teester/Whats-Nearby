package com.teester.whatsnearby.main;

import com.teester.whatsnearby.BuildConfig;

public class AboutPresenter implements MainActivityContract.AboutPresenter {

	private MainActivityContract.AboutView view;

    AboutPresenter(MainActivityContract.AboutView view) {
		this.view = view;
	}

	@Override
	public void findVersion() {
		String version = BuildConfig.VERSION_NAME;
		view.setVersion(version);
	}

	@Override
	public void getLicence() {
		String uri = "http://www.gnu.org/licenses/gpl-3.0.html";
		view.visitUri(uri);
	}

	@Override
	public void getGitHub() {
		String uri = "https://github.com/Teester/Whats-Nearby";
		view.visitUri(uri);
	}
}
