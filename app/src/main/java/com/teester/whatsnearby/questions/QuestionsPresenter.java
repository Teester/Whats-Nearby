package com.teester.whatsnearby.questions;

import com.teester.whatsnearby.Utilities;
import com.teester.whatsnearby.data.Answers;
import com.teester.whatsnearby.data.OsmObject;
import com.teester.whatsnearby.data.PoiList;
import com.teester.whatsnearby.data.PoiTypes;
import com.teester.whatsnearby.data.PreferenceList;
import com.teester.whatsnearby.data.pois.PoiContract;
import com.teester.whatsnearby.data.source.SourceContract;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.util.List;
import java.util.Map;

public class QuestionsPresenter implements QuestionsActivityContract.Presenter {

	private QuestionsActivityContract.View view;
	private SourceContract.Preferences preferences;

	QuestionsPresenter(QuestionsActivityContract.View view, SourceContract.Preferences preferences) {
		this.view = view;
		this.preferences = preferences;
		Answers.clearAnswerList();
		preferences.setLongPreference(PreferenceList.LAST_NOTIFICATION_TIME, System.currentTimeMillis());
	}

	@Override
	public void addPoiNameToTextview() {
		List<OsmObject> poiList = PoiList.getInstance().getPoiList();
		String poiType = poiList.get(0).getType();
		PoiContract listOfQuestions = PoiTypes.getPoiType(poiType);
		listOfQuestions.shuffleQuestions();

		if (preferences.getBooleanPreference(PreferenceList.LOGGED_IN_TO_OSM)) {
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
	public void assessIntentData(URI uri) {
		if (uri != null) {
			try {
				Map<String, List<String>> list = Utilities.splitQuery(uri);
				String verifier = list.get(PreferenceList.OAUTH_VERIFIER).get(0);
				String token = list.get(PreferenceList.OAUTH_TOKEN).get(0);
				preferences.setStringPreference(PreferenceList.OAUTH_VERIFIER, verifier);
				preferences.setStringPreference(PreferenceList.OAUTH_TOKEN, token);
				view.startOAuth();

			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
	}
}
