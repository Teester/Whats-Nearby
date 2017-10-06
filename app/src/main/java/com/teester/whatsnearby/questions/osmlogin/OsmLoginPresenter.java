package com.teester.whatsnearby.questions.osmlogin;

import com.teester.whatsnearby.R;
import com.teester.whatsnearby.model.PreferencesContract;

class OsmLoginPresenter implements OsmLoginFragmentContract.Presenter {

	private OsmLoginFragmentContract.View view;
	private PreferencesContract preferences;

	public OsmLoginPresenter(OsmLoginFragment view, PreferencesContract preferences) {
		this.view = view;
		this.preferences = preferences;
	}

	public void onClicked(int id) {
		if (id == R.id.osmLoginButton) {
			preferences.setStringPreference("oauth_verifier", "");
			preferences.setStringPreference("oauth_token", "");
			preferences.setStringPreference("oauth_token_secret", "");

			view.startOAuth();
		}
	}
}
