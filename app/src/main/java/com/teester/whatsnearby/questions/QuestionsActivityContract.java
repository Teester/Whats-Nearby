package com.teester.whatsnearby.questions;

import com.teester.whatsnearby.BasePresenter;
import com.teester.whatsnearby.BaseView;
import com.teester.whatsnearby.data.OsmObject;
import com.teester.whatsnearby.data.OsmObjectType;

import java.net.URI;

public interface QuestionsActivityContract {

	interface Presenter extends BasePresenter {

		void addPoiNameToTextview();

		void assessIntentData(URI uri);
	}

	interface View extends BaseView<Presenter> {

		void setViewPager(OsmObject osmObject, OsmObjectType listOfQuestions);

		void startNewActivity();

		void makeTextViewInvisible();

		void setTextviewText(String text);

		void startOAuth();
	}
}
