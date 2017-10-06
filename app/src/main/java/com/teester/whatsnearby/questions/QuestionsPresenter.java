package com.teester.whatsnearby.questions;

import android.net.Uri;

import com.teester.whatsnearby.model.OsmObject;
import com.teester.whatsnearby.model.OsmObjectType;
import com.teester.whatsnearby.model.PreferencesContract;
import com.teester.whatsnearby.model.QuestionObject;
import com.teester.whatsnearby.model.data.PoiList;
import com.teester.whatsnearby.model.data.PoiTypes;

import java.util.List;

public class QuestionsPresenter implements QuestionsActivityContract.Presenter {

	private QuestionsActivityContract.View view;
	private QuestionObject questionObject;
	private PreferencesContract preferences;

	public QuestionsPresenter(QuestionsActivityContract.View view, PreferencesContract preferences) {
		this.view = view;
		this.preferences = preferences;
	}

	@Override
	public void init() {
	}

	@Override
	public void destroy() {

	}

	@Override
	public void assessIntentExtras() {
		List<OsmObject> poiList = PoiList.getInstance().getPoiList();
		String poiType = poiList.get(0).getType();
		PoiTypes poiTypes = new PoiTypes();
		OsmObjectType listOfQuestions = poiTypes.getPoiType(poiType);
		listOfQuestions.shuffleQuestions();

		if (preferences.getBoolPreference("logged_in_to_osm") == true) {
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
