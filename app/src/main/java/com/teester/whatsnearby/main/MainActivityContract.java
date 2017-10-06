package com.teester.whatsnearby.main;

import android.net.Uri;

import com.teester.whatsnearby.BasePresenter;
import com.teester.whatsnearby.BaseView;

public interface MainActivityContract {

	interface Presenter extends BasePresenter {

		void showIfLoggedIn();

		void checkIfOauth(Uri uri);

		void onButtonClicked();

	}

	interface View extends BaseView<Presenter> {

		void showIfLoggedIn(int message, int button);

		void startOAuth();
	}
}