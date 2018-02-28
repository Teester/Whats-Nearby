package com.teester.whatsnearby.questions;

import com.teester.whatsnearby.Utilities;
import com.teester.whatsnearby.data.Answers;
import com.teester.whatsnearby.data.OsmObject;
import com.teester.whatsnearby.data.OsmObjectType;
import com.teester.whatsnearby.data.PoiList;
import com.teester.whatsnearby.data.PoiTypes;
import com.teester.whatsnearby.data.source.SourceContract;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.util.List;
import java.util.Map;

public class QuestionsPresenter implements QuestionsActivityContract.Presenter {

	private QuestionsActivityContract.View view;
	private SourceContract.Preferences preferences;

	public QuestionsPresenter(QuestionsActivityContract.View view, SourceContract.Preferences preferences) {
		this.view = view;
		this.preferences = preferences;
		Answers.clearAnswerList();
	}

	@Override
	public void init() {
		// required empty method
	}

	@Override
	public void destroy() {
		// required empty method
	}

	@Override
	public void addPoiNameToTextview() {
		List<OsmObject> poiList = PoiList.getInstance().getPoiList();
		String poiType = poiList.get(0).getType();
		OsmObjectType listOfQuestions = PoiTypes.getPoiType(poiType);
		listOfQuestions.shuffleQuestions();

		if (preferences.getBooleanPreference("logged_in_to_osm")) {
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
				String verifier = list.get("oauth_verifier").get(0);
				String token = list.get("oauth_token").get(0);
				preferences.setStringPreference("oauth_verifier", verifier);
				preferences.setStringPreference("oauth_token", token);
				view.startOAuth();

			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
	}
}
