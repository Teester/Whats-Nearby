package com.teester.whatsnearby.questions.osmlogin;

interface OsmLoginFragmentContract {

	interface Presenter {

		void clickedOsmLoginButton();
	}

	interface View {

		void startOAuth();
	}
}
