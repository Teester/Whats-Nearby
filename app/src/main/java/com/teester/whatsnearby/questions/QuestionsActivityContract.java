package com.teester.whatsnearby.questions;

import android.net.Uri;

import com.teester.whatsnearby.BasePresenter;
import com.teester.whatsnearby.BaseView;
import com.teester.whatsnearby.model.OsmObject;
import com.teester.whatsnearby.model.OsmObjectType;

public interface QuestionsActivityContract {

	interface Presenter extends BasePresenter {

		void assessIntentData(Uri uri);

		void assessIntentExtras();

	}

	interface View extends BaseView<Presenter> {

		void setViewPager(OsmObject osmObject, OsmObjectType listOfQuestions);

		void startNewActivity();

		void makeTextViewInvisible();

		void setTextviewText(String text);

		void startOAuth();
	}
}
