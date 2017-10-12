package com.teester.whatsnearby.questions;

import android.net.Uri;

import com.teester.whatsnearby.data.Answers;
import com.teester.whatsnearby.data.OsmObject;
import com.teester.whatsnearby.data.OsmObjectType;
import com.teester.whatsnearby.data.PoiList;
import com.teester.whatsnearby.data.PoiTypes;
import com.teester.whatsnearby.data.QuestionObject;
import com.teester.whatsnearby.data.source.SourceContract;

import java.util.List;

public class QuestionsPresenter implements QuestionsActivityContract.Presenter {

	private QuestionsActivityContract.View view;
	private QuestionObject questionObject;
	private SourceContract.Preferences preferences;

	public QuestionsPresenter(QuestionsActivityContract.View view, SourceContract.Preferences preferences) {
		this.view = view;
		this.preferences = preferences;
		Answers.clearAnswerList();
	}

	@Override
	public void init() {
	}

	@Override
	public void destroy() {

	}

	@Override
	public void addPoiNameToTextview() {
		List<OsmObject> poiList = PoiList.getInstance().getPoiList();
		String poiType = poiList.get(0).getType();
		OsmObjectType listOfQuestions = PoiTypes.getPoiType(poiType);
		listOfQuestions.shuffleQuestions();

		if (preferences.getBooleanPreference("logged_in_to_osm") == true) {
			view.setViewPager(poiList.get(0), listOfQuestions);
			if (poiList.size() == 1) {
				view.makeTextViewInvisible();
			} else {
				view.setTextviewText(poiList.get(0).getName());
			}
		} else {
			view.startNewActivity();
		}
	}

	@Override
	public void assessIntentData(Uri uri) {
		if (uri != null) {
			preferences.setStringPreference("oauth_verifier", uri.getQueryParameter("oauth_verifier"));
			preferences.setStringPreference("oauth_token", uri.getQueryParameter("oauth_token"));
			view.startOAuth();
		}
	}
}
