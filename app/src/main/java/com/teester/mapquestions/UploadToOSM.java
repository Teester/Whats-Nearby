package com.teester.mapquestions;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.westnordost.osmapi.OsmConnection;
import de.westnordost.osmapi.common.errors.OsmAuthorizationException;
import de.westnordost.osmapi.map.MapDataDao;
import de.westnordost.osmapi.map.data.Element;
import oauth.signpost.OAuthConsumer;
import oauth.signpost.OAuthProvider;
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;
import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;
import oauth.signpost.exception.OAuthNotAuthorizedException;

/**
 * Handles uploading answers to OpenStreetMap
 */

public class UploadToOSM {
	private static final String TAG = UploadToOSM.class.getSimpleName();

	ArrayList<Answer> answers;
	String changeset_app = "Map Questions";
	String changeset_app_version = BuildConfig.VERSION_NAME;;
	String changeset_source = "survey";
	String changeset_comment = "Added details to %s";
	Context context;
	Element currentElement;

	private static final String CONSUMER_KEY = "1LJqwD4kMz96HTbv9I1U1XBM0AL1RpcjuFOPvW0B";
	private static final String CONSUMER_SECRET = "KDCLveu82AZawLELpC6yIP3EI8fJa0JqF0ALukbl";

	public UploadToOSM (ArrayList<Answer> answers, Context context) throws OAuthNotAuthorizedException, OAuthExpectationFailedException, OAuthCommunicationException, OAuthMessageSignerException {
		this.answers = answers;
		this.context = context;
		Upload();
	}

	public Void Upload() {
		String type = this.answers.get(0).getObjectType();
		long objectId = this.answers.get(0).getObjectId();

		OAuthConsumer oAuthConsumer = getConsumer();
		DownloadElement downloadFromOsm = new DownloadElement(objectId, type, oAuthConsumer, context);
		downloadFromOsm.execute();

		return null;
	}

	private OAuthConsumer getConsumer() {

		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
		String oauth_token_secret = prefs.getString("oauth_token_secret", "");
		String oauth_token = prefs.getString("oauth_token", "");

		OAuthConsumer consumer = new CommonsHttpOAuthConsumer(CONSUMER_KEY, CONSUMER_SECRET);
		consumer.setTokenWithSecret(oauth_token, oauth_token_secret);
		return consumer;
	}

	private Map<String,String> createChangesetTags()
	{
		Map<String,String> changesetTags = new HashMap<>();
		// TODO: Add POI name to changeset comment
		changesetTags.put("comment", String.format(changeset_comment, answers.get(0).getObjectName()));
		changesetTags.put("created_by", changeset_app);
		changesetTags.put("version", changeset_app_version);
		changesetTags.put("source", changeset_source);
		return changesetTags;
	}


	public class DownloadElement extends AsyncTask<Void, Void, Void> {

		private long id;
		private String type;
		private Context context;

		public DownloadElement(long id, String type, OAuthConsumer consumer, Context context ) {
			this.id = id;
			this.type = type;
			this.context = context;
		}

		@Override
		protected Void doInBackground(Void... params) {

			OAuthConsumer consumer = getConsumer();
			OsmConnection osm = new OsmConnection(
					"https://api.openstreetmap.org/api/0.6/",
					"Map Questions", consumer);

			// Download the relevant object from OSM
			Element downloadedElement = null;
			switch (this.type) {
				case "node":
					downloadedElement = new MapDataDao(osm).getNode(this.id);
					break;
				case "way":
					downloadedElement = new MapDataDao(osm).getWay(this.id);
					break;
				case "relation":
					downloadedElement = new MapDataDao(osm).getRelation(this.id);
					break;
				default:
					return null;
			}

			// Add/modify the relevant keys
			Element modifiedElement = downloadedElement;
			for (int i = 0; i < answers.size(); i++) {
				Answer answer = answers.get(i);
				QuestionObject questionObject = answers.get(i).getQuestion();
				String key = answer.getQuestion().getTag();
				String value = questionObject.getAnswer(answer.getAnswer());
				modifiedElement.getTags().put(key, value);
			}

			// Update the altered object
			List<Element> collection = Collections.singletonList(modifiedElement);
			Map<String, String> changesetTags = createChangesetTags();
			if (modifiedElement.isModified()) {
				try {
					long upload = new MapDataDao(osm).updateMap(changesetTags, collection, null);
				} catch (OsmAuthorizationException e) {
					Log.i(TAG, e.toString());
				}
			}
			return null;
		}
	}
}
