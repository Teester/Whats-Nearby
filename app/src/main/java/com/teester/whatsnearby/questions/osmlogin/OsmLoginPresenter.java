package com.teester.whatsnearby.questions.osmlogin;

import com.teester.whatsnearby.data.source.SourceContract;

class OsmLoginPresenter implements OsmLoginFragmentContract.Presenter {

	private OsmLoginFragmentContract.View view;
	private SourceContract.Preferences preferences;

	public OsmLoginPresenter(OsmLoginFragment view, SourceContract.Preferences preferences) {
		this.view = view;
		this.preferences = preferences;
	}

	@Override
	public void clickedOsmLoginButton() {
		preferences.setStringPreference("oauth_verifier", "");
		preferences.setStringPreference("oauth_token", "");
		preferences.setStringPreference("oauth_token_secret", "");

		view.startOAuth();
	}
}
