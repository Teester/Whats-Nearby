package com.teester.whatsnearby.main;

import com.teester.whatsnearby.BasePresenter;
import com.teester.whatsnearby.BaseView;

import java.net.URI;

public interface MainActivityContract {

	interface Presenter extends BasePresenter {

		void showIfLoggedIn();

		void checkIfOauth(URI url);

		void onButtonClicked();

		void toggleDebugMode();

	}

	interface View extends BaseView<Presenter> {

		void showIfLoggedIn(int message, int button);

		void startOAuth();
	}

	interface DebugPresenter extends BasePresenter {

		void getDetails();

	}

	interface DebugView extends BaseView<DebugPresenter> {

		void setLastQueryTime(String time, int color);

		void setLastQuery(String queryTime);

		void setAccuracy(String accuracy, int color);

		void setQuerydistance(String querydistance, int color);

		void setLastNotificationTime(String notificationTime, int color);

		void setCheckdistance(String queryTimeSince, int color);

		void setLocation(double latitude, double longitude);
	}

	interface AboutPresenter extends BasePresenter {

		void findVersion();

		void getLicence();

		void getGitHub();
	}

	interface AboutView extends BaseView<AboutPresenter> {
		void setVersion(String version);

		void visitUri(String uri);
	}
}