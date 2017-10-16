package com.teester.whatsnearby.questions;

import com.teester.whatsnearby.data.Answers;
import com.teester.whatsnearby.data.OsmObject;
import com.teester.whatsnearby.data.OsmObjectType;
import com.teester.whatsnearby.data.PoiList;
import com.teester.whatsnearby.data.PoiTypes;
import com.teester.whatsnearby.data.source.SourceContract;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.LinkedHashMap;
import java.util.LinkedList;
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

	/**
	 * Extracts the query parameters from a java.net.URL
	 *
	 * @param url - the url to be parsed
	 * @return - a map containing the parameters and their values
	 * @throws UnsupportedEncodingException
	 */
	public static Map<String, List<String>> splitQuery(URL url) throws UnsupportedEncodingException {
		final Map<String, List<String>> query_pairs = new LinkedHashMap<String, List<String>>();
		final String[] pairs = url.getQuery().split("&");
		for (String pair : pairs) {
			final int idx = pair.indexOf("=");
			final String key = idx > 0 ? URLDecoder.decode(pair.substring(0, idx), "UTF-8") : pair;
			if (!query_pairs.containsKey(key)) {
				query_pairs.put(key, new LinkedList<String>());
			}
			final String value = idx > 0 && pair.length() > idx + 1 ? URLDecoder.decode(pair.substring(idx + 1), "UTF-8") : null;
			query_pairs.get(key).add(value);
		}
		return query_pairs;
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
	public void assessIntentData(URL uri) {
		if (uri != null) {
			try {
				Map<String, List<String>> list = splitQuery(uri);
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
