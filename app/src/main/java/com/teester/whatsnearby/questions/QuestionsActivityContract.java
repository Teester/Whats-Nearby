package com.teester.whatsnearby.questions;

import com.teester.whatsnearby.data.OsmObject;
import com.teester.whatsnearby.data.pois.PoiContract;

import java.net.URI;

public interface QuestionsActivityContract {

	interface Presenter {

		void addPoiNameToTextview();

		void assessIntentData(URI uri);

		void savePoiList();

		void restorePoiList();
	}

	interface View {

		void setViewPager(OsmObject osmObject, PoiContract listOfQuestions);

		void startNewActivity();

		void makeTextViewInvisible();

		void setTextviewText(String text);

		void startOAuth();
	}
}
