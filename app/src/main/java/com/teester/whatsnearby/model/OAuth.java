package com.teester.whatsnearby.model;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.Browser;

import oauth.signpost.OAuthConsumer;
import oauth.signpost.OAuthProvider;
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;
import oauth.signpost.commonshttp.CommonsHttpOAuthProvider;
import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;
import oauth.signpost.exception.OAuthNotAuthorizedException;

public class OAuth extends AsyncTask<Void, Void, Void> {

	private static final String TAG = OAuth.class.getSimpleName();
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
	}

	public OAuth(Preferences context) {
		this.preferences = context;
	}

	@Override
	protected Void doInBackground(Void... voids) {

		Preferences preferences = new Preferences(context);
		String verifier = preferences.getStringPreference("oauth_verifier");
		String token = preferences.getStringPreference("oauth_token");
		String tokenSecret = preferences.getStringPreference("oauth_token_secret");

		OAuthConsumer consumer = new CommonsHttpOAuthConsumer(CONSUMER_KEY, CONSUMER_SECRET);
		OAuthProvider provider = new CommonsHttpOAuthProvider(REQUEST_TOKEN_ENDPOINT_URL, ACCESS_TOKEN_ENDPOINT_URL, AUTHORIZE_WEBSITE_URL);

		try {
			if (verifier == "") {
				String url = provider.retrieveRequestToken(consumer, CALLBACK_URL);

				preferences.setStringPreference("oauth_token_secret", consumer.getTokenSecret());
				preferences.setStringPreference("oauth_token", consumer.getToken());

				//Open the url in an external browser
				Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
				browserIntent.putExtra(Browser.EXTRA_APPLICATION_ID, context.getPackageName());
				browserIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_SINGLE_TOP);
				context.startActivity(browserIntent);
			} else {
				consumer.setTokenWithSecret(token, tokenSecret);
				provider.setOAuth10a(true);
				provider.retrieveAccessToken(consumer, verifier);

				preferences.setStringPreference("oauth_token_secret", consumer.getTokenSecret());
				preferences.setStringPreference("oauth_token", consumer.getToken());
				preferences.setBooleanPreference("logged_in_to_osm", true);
			}
		} catch (OAuthMessageSignerException e) {
			e.printStackTrace();
		} catch (OAuthNotAuthorizedException e) {
			e.printStackTrace();
		} catch (OAuthExpectationFailedException e) {
			e.printStackTrace();
		} catch (OAuthCommunicationException e) {
			e.printStackTrace();
		}
		return null;
	}
}
