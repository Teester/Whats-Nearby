package com.teester.whatsnearby.questions.osmlogin;

interface OsmLoginFragmentContract {

	interface Presenter {

		void ClickedOsmLoginButton();
	}

	interface View {

		void startOAuth();
	}
}
