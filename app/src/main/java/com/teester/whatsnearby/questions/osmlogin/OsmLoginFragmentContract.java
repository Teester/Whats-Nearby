package com.teester.whatsnearby.questions.osmlogin;

interface OsmLoginFragmentContract {

	interface Presenter {

		void onClicked(int id);
	}

	interface View {

		void startOAuth();
	}
}
