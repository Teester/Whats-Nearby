package com.teester.whatsnearby.model;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.os.AsyncTask;
import android.util.Log;

import com.teester.whatsnearby.BuildConfig;
import com.teester.whatsnearby.R;
import com.teester.whatsnearby.model.data.Answers;

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

	private static final String CONSUMER_KEY = "1LJqwD4kMz96HTbv9I1U1XBM0AL1RpcjuFOPvW0B";
	private static final String CONSUMER_SECRET = "KDCLveu82AZawLELpC6yIP3EI8fJa0JqF0ALukbl";

	ArrayList<Answer> answers;
	Context context;
	Element currentElement;

	public UploadToOSM(Context context) throws OAuthNotAuthorizedException, OAuthExpectationFailedException, OAuthCommunicationException, OAuthMessageSignerException {
		this.answers = Answers.getInstance().getAnswerList();
		this.context = context;
		Upload();
	}

	public static String getApplicationName(Context context) {
		ApplicationInfo applicationInfo = context.getApplicationInfo();
		int stringId = applicationInfo.labelRes;
		return stringId == 0 ? applicationInfo.nonLocalizedLabel.toString() : context.getString(stringId);
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

		Preferences preferences = new Preferences(context);
		String oauth_token_secret = preferences.getStringPreference("oauth_token_secret");
		String oauth_token = preferences.getStringPreference("oauth_token");

		OAuthConsumer consumer = new CommonsHttpOAuthConsumer(CONSUMER_KEY, CONSUMER_SECRET);
		consumer.setTokenWithSecret(oauth_token, oauth_token_secret);
		return consumer;
	}

	private Map<String,String> createChangesetTags()
	{
		Map<String,String> changesetTags = new HashMap<>();
		changesetTags.put("comment", String.format(context.getString(R.string.changeset_comment), answers.get(0).getObjectName()));
		changesetTags.put("created_by", context.getResources().getString(R.string.app_name));
		changesetTags.put("version", BuildConfig.VERSION_NAME);
		changesetTags.put("source", context.getString(R.string.changeset_source));
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
					"What's Nearby?", consumer);

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
