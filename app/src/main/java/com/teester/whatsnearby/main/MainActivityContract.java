package com.teester.whatsnearby.main;

import java.net.URI;

public interface MainActivityContract {

	interface Presenter {

		void showIfLoggedIn();

		void checkIfOauth(URI url);

		void onButtonClicked();

		void toggleDebugMode();

	}

	interface View {

		void showIfLoggedIn(int message, int button);

		void startOAuth();
	}

	interface DebugPresenter {

		void getDetails();

	}

	interface DebugView {

		void setLastQueryTime(String time, int color);

		void setLastQuery(String queryTime);

		void setAccuracy(String accuracy, int color);

		void setQuerydistance(String querydistance, int color);

		void setLastNotificationTime(String notificationTime, int color);

		void setCheckdistance(String queryTimeSince, int color);

		void setLocation(double latitude, double longitude);
	}

	interface AboutPresenter {

		void findVersion();

		void getLicence();

		void getGitHub();
	}

	interface AboutView {
		void setVersion(String version);

		void visitUri(String uri);
	}
}