package com.teester.whatsnearby.data.source;

import com.teester.whatsnearby.data.Answers;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import de.westnordost.osmapi.OsmConnection;
import de.westnordost.osmapi.common.errors.OsmAuthorizationException;
import de.westnordost.osmapi.map.MapDataDao;
import de.westnordost.osmapi.map.data.Element;
import oauth.signpost.OAuthConsumer;
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;
import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;
import oauth.signpost.exception.OAuthNotAuthorizedException;

/**
 * Handles uploading answers to OpenStreetMap
 */

public class UploadToOSM implements SourceContract.Upload {
	private static final String TAG = UploadToOSM.class.getSimpleName();

	private static final String CONSUMER_KEY = "1LJqwD4kMz96HTbv9I1U1XBM0AL1RpcjuFOPvW0B";
	private static final String CONSUMER_SECRET = "KDCLveu82AZawLELpC6yIP3EI8fJa0JqF0ALukbl";

	private Element currentElement;
	private SourceContract.Preferences preferences;

	public UploadToOSM(SourceContract.Preferences preferences) throws OAuthNotAuthorizedException, OAuthExpectationFailedException, OAuthCommunicationException, OAuthMessageSignerException {
		this.preferences = preferences;
		Upload();
	}

	@Override
	public void Upload() {
		String type = Answers.getPoiType();
		long id = Answers.getPoiId();
		Map<String, String> changesetTags = Answers.getChangesetTags();

		// Get OSM connection details
		OsmConnection osm = getConnection();

		// Download the relevant object from OSM
		Element downloadedElement = getCurrentElement(osm, type, id);

		// Add/modify the relevant keys
		Element modifiedElement = modifyCurrentElement(downloadedElement);

		// Update the altered object
		List<Element> collection = Collections.singletonList(modifiedElement);
		if (modifiedElement.isModified()) {
			try {
				long upload = new MapDataDao(osm).updateMap(changesetTags, collection, null);
			} catch (OsmAuthorizationException e) {
				//Log.i(TAG, e.toString());
			}
		}
	}

	private OsmConnection getConnection() {
		String oauth_token_secret = preferences.getStringPreference("oauth_token_secret");
		String oauth_token = preferences.getStringPreference("oauth_token");

		OAuthConsumer consumer = new CommonsHttpOAuthConsumer(CONSUMER_KEY, CONSUMER_SECRET);
		consumer.setTokenWithSecret(oauth_token, oauth_token_secret);

		OsmConnection osm = new OsmConnection(
				"https://api.openstreetmap.org/api/0.6/",
				"What's Nearby?", consumer);
		return osm;
	}

	private Element getCurrentElement(OsmConnection osm, String type, long id) {
		// Download the relevant object from OSM
		Element downloadedElement = null;
		switch (type) {
			case "node":
				downloadedElement = new MapDataDao(osm).getNode(id);
				break;
			case "way":
				downloadedElement = new MapDataDao(osm).getWay(id);
				break;
			case "relation":
				downloadedElement = new MapDataDao(osm).getRelation(id);
				break;
			default:
				return null;
		}
		return downloadedElement;
	}

	private Element modifyCurrentElement(Element modifiedElement) {
		Iterator<Map.Entry<String, String>> it = Answers.getAnswerMap().entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<String, String> pair = it.next();
			String key = pair.getKey();
			String value = pair.getValue();
			if (value != "") {
				modifiedElement.getTags().put(key, value);
			}
		}

		return modifiedElement;
	}
}
