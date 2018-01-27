package com.teester.whatsnearby.questions.intro;

import com.teester.whatsnearby.BasePresenter;
import com.teester.whatsnearby.BaseView;


public interface IntroFragmentContract {
	interface Presenter extends BasePresenter {
		void getDetails();

	}

	interface View extends BaseView<Presenter> {
		void ShowDetails(String name, String address, int drawable);
	}
}
