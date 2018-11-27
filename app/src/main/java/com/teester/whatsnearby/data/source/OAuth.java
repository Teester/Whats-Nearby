package com.teester.whatsnearby.data.source;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.Browser;

import com.teester.whatsnearby.data.PreferenceList;

import oauth.signpost.OAuthConsumer;
import oauth.signpost.OAuthProvider;
import oauth.signpost.basic.DefaultOAuthConsumer;
import oauth.signpost.basic.DefaultOAuthProvider;
import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;
import oauth.signpost.exception.OAuthNotAuthorizedException;

public class OAuth implements SourceContract.oAuth {

	private static final String CONSUMER_KEY = "1LJqwD4kMz96HTbv9I1U1XBM0AL1RpcjuFOPvW0B";
	private static final String CONSUMER_SECRET = "KDCLveu82AZawLELpC6yIP3EI8fJa0JqF0ALukbl";
	private static final String REQUEST_TOKEN_ENDPOINT_URL = "https://www.openstreetmap.org/oauth/request_token";
	private static final String ACCESS_TOKEN_ENDPOINT_URL = "https://www.openstreetmap.org/oauth/access_token";
	private static final String AUTHORIZE_WEBSITE_URL = "https://www.openstreetmap.org/oauth/authorize";
	private static final String CALLBACK_URL = "mapquestions://oauth";
	private Context context;
	private Preferences preferences;

	public OAuth(Context context) {
		this.context = context;
		preferences = new Preferences(context);
		processOAuth();
	}

	@Override
	public void processOAuth() {

		String verifier = preferences.getStringPreference(PreferenceList.OAUTH_VERIFIER);
		String token = preferences.getStringPreference(PreferenceList.OAUTH_TOKEN);
		String tokenSecret = preferences.getStringPreference(PreferenceList.OAUTH_TOKEN_SECRET);

		OAuthConsumer consumer = new DefaultOAuthConsumer(CONSUMER_KEY, CONSUMER_SECRET);
		OAuthProvider provider = new DefaultOAuthProvider(REQUEST_TOKEN_ENDPOINT_URL, ACCESS_TOKEN_ENDPOINT_URL, AUTHORIZE_WEBSITE_URL);

		try {
			if ("".equals(verifier)) {
				String url = provider.retrieveRequestToken(consumer, CALLBACK_URL);

				preferences.setStringPreference(PreferenceList.OAUTH_TOKEN_SECRET, consumer.getTokenSecret());
				preferences.setStringPreference(PreferenceList.OAUTH_TOKEN, consumer.getToken());

				//Open the url in an external browser
				Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
				browserIntent.putExtra(Browser.EXTRA_APPLICATION_ID, context.getPackageName());
				browserIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_SINGLE_TOP);
				context.startActivity(browserIntent);
			} else {
				consumer.setTokenWithSecret(token, tokenSecret);
				provider.setOAuth10a(true);
				provider.retrieveAccessToken(consumer, verifier);

				new UploadToOSM(preferences);

				preferences.setStringPreference(PreferenceList.OAUTH_TOKEN_SECRET, consumer.getTokenSecret());
				preferences.setStringPreference(PreferenceList.OAUTH_TOKEN, consumer.getToken());
				preferences.setBooleanPreference(PreferenceList.LOGGED_IN_TO_OSM, true);
			}
        } catch (OAuthMessageSignerException |
                OAuthNotAuthorizedException |
                OAuthExpectationFailedException |
                OAuthCommunicationException e) {
			e.printStackTrace();
		}
	}
}
