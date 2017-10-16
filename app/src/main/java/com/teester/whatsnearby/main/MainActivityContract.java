package com.teester.whatsnearby.main;

import com.teester.whatsnearby.BasePresenter;
import com.teester.whatsnearby.BaseView;

import java.net.URL;

public interface MainActivityContract {

	interface Presenter extends BasePresenter {

		void showIfLoggedIn();

		void checkIfOauth(URL url);

		void onButtonClicked();

	}

	interface View extends BaseView<Presenter> {

		void showIfLoggedIn(int message, int button);

		void startOAuth();
	}
}