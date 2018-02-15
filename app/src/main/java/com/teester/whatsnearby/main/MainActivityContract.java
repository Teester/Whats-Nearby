package com.teester.whatsnearby.main;

import com.teester.whatsnearby.BasePresenter;
import com.teester.whatsnearby.BaseView;

import java.net.URI;

public interface MainActivityContract {

	interface Presenter extends BasePresenter {

		void showIfLoggedIn();

		void checkIfOauth(URI url);

		void onButtonClicked();

	}

	interface View extends BaseView<Presenter> {

		void showIfLoggedIn(int message, int button);

		void startOAuth();
	}

	interface DebugPresenter extends BasePresenter {

		void getDetails();
	}

	interface DebugView extends BaseView<DebugPresenter> {

		void setLastQueryTime(String lastQuerytime, String lastNotificationTime, String lastQuery);

	}
}