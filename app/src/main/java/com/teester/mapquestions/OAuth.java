package com.teester.mapquestions;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.provider.Browser;
import android.util.Log;

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
	private Context context;

	private static final String CONSUMER_KEY = "1LJqwD4kMz96HTbv9I1U1XBM0AL1RpcjuFOPvW0B";
	private static final String CONSUMER_SECRET = "KDCLveu82AZawLELpC6yIP3EI8fJa0JqF0ALukbl";

	String REQUEST_TOKEN_ENDPOINT_URL = "https://www.openstreetmap.org/oauth/request_token";
	String ACCESS_TOKEN_ENDPOINT_URL = "https://www.openstreetmap.org/oauth/access_token";
	String AUTHORIZE_WEBSITE_URL = "https://www.openstreetmap.org/oauth/authorize";
	String CALLBACK_URL = "mapquestions://oauth";


	public OAuth(Context context) {
		this.context = context;
		Log.i(TAG, "in OAuth");
	}

	@Override
	protected Void doInBackground(Void... voids) {

		Log.i(TAG, "in doInBackground");
		//SharedPreferences prefs = context.getSharedPreferences("com.teester.mapquestions", Context.MODE_PRIVATE);
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());

		String verifier = prefs.getString("oauth_verifier", "");
		String token = prefs.getString("oauth_token", "");
		String tokenSecret = prefs.getString("oauth_token_secret", "");
		Log.i(TAG, "oauth_token: " + token);
		Log.i(TAG, "oauth_token_secret: " + tokenSecret);
		Log.i(TAG, "oauth_verifier: " + verifier);

		OAuthConsumer consumer = new CommonsHttpOAuthConsumer(CONSUMER_KEY, CONSUMER_SECRET);

		// create a new service provider object and configure it with
		// the URLs which provide request tokens, access tokens, and
		// the URL to which users are sent in order to grant permission
		// to your application to access protected resources
		OAuthProvider provider = new CommonsHttpOAuthProvider(REQUEST_TOKEN_ENDPOINT_URL, ACCESS_TOKEN_ENDPOINT_URL, AUTHORIZE_WEBSITE_URL);

		// fetches a request token from the service provider and builds
		// a url based on AUTHORIZE_WEBSITE_URL and CALLBACK_URL to
		// which your app must now send the user
		if (verifier == "") {
			String url = "";
			try {
				Log.i(TAG, "Token: " + consumer.getToken());
				Log.i(TAG, "Token Secret: " + consumer.getTokenSecret());
				url = provider.retrieveRequestToken(consumer, CALLBACK_URL);
				Log.i(TAG, url);
				Log.i(TAG, "Token: " + consumer.getToken());
				Log.i(TAG, "Token Secret: " + consumer.getTokenSecret());

				SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext()).edit();
				editor.putString("oauth_token_secret", consumer.getTokenSecret());
				editor.putString("oauth_token", consumer.getToken());
				editor.apply();
			} catch (OAuthMessageSignerException e) {
				e.printStackTrace();
			} catch (OAuthNotAuthorizedException e) {
				e.printStackTrace();
			} catch (OAuthExpectationFailedException e) {
				e.printStackTrace();
			} catch (OAuthCommunicationException e) {
				e.printStackTrace();
			}

			//Open the url in an external browser
			Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
			browserIntent.putExtra(Browser.EXTRA_APPLICATION_ID, context.getPackageName());
			browserIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_SINGLE_TOP);
			context.startActivity(browserIntent);

			return null;
		} else {
			try {

				Log.i(TAG, "Token: " + consumer.getToken());
				Log.i(TAG, "Token Secret: " + consumer.getTokenSecret());
				consumer.setTokenWithSecret(token, tokenSecret);

				Log.i(TAG, "Token: " + consumer.getToken());
				Log.i(TAG, "Token Secret: " + consumer.getTokenSecret());

				provider.setOAuth10a(true);
				provider.retrieveAccessToken(consumer, verifier);

				SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext()).edit();

				Log.i(TAG, "Token: " + consumer.getToken());
				Log.i(TAG, "Token Secret: " + consumer.getTokenSecret());

				editor.putString("oauth_token_secret", consumer.getTokenSecret());
				editor.putString("oauth_token", consumer.getToken());
				editor.apply();



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

	@Override
	protected void onPostExecute(Void aVoid) {
		super.onPostExecute(aVoid);
	}
}
