package com.teester.whatsnearby.questions;

import android.net.Uri;

import com.teester.whatsnearby.BasePresenter;
import com.teester.whatsnearby.BaseView;
import com.teester.whatsnearby.data.OsmObject;
import com.teester.whatsnearby.data.OsmObjectType;

public interface QuestionsActivityContract {

	interface Presenter extends BasePresenter {

		void assessIntentData(Uri uri);

		void addPoiNameToTextview();

	}

	interface View extends BaseView<Presenter> {

		void setViewPager(OsmObject osmObject, OsmObjectType listOfQuestions);

		void startNewActivity();

		void makeTextViewInvisible();

		void setTextviewText(String text);

		void startOAuth();
	}
}
