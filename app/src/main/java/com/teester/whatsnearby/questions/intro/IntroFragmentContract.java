package com.teester.whatsnearby.questions.intro;

public interface IntroFragmentContract {
	interface Presenter {
		void getDetails();

	}

	interface View {
		void showDetails(String name, String address, int drawable);
	}
}
