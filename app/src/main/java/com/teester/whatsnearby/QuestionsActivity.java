package com.teester.whatsnearby;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

public class QuestionsActivity extends AppCompatActivity
		 implements QuestionFragment.OnFragmentInteractionListener,
					NotHereFragment.OnFragmentInteractionListener,
					UploadFragment.OnFragmentInteractionListener,
					OsmLoginFragment.OnFragmentInteractionListener {

	private static final String TAG = QuestionsActivity.class.getSimpleName();
	public static QuestionObject question;
	public static QuestionObject previousQuestion;
	public static ArrayList<Answer> answers = new ArrayList<Answer>();
	FragmentPagerAdapter adapterViewPager;
	NonSwipeableViewPager viewPager;
	private ArrayList<OsmObject> poiList;
	private OsmObjectType listOfQuestions;
	private String oauth_verifier;
	private String oauth_token;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_container);

		NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
		notificationManager.cancelAll();

		// Get the POI list from the intent which activated the activity
		// noinspection unchecked
		ArrayList<OsmObject> poiList = (ArrayList<OsmObject>) getIntent().getExtras().get(("poilist"));
		this.poiList = poiList;
		SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

		// Set up a viewPager
		if (poiList != null) {
			String poiType = poiList.get(0).getType();
			PoiTypes poiTypes = new PoiTypes();
			this.listOfQuestions = poiTypes.getPoiType(poiType);
			this.listOfQuestions.shuffleQuestions();

			if (sharedPreferences.getBoolean("logged_in_to_osm", false) == true) {
				viewPager = (NonSwipeableViewPager) findViewById(R.id.viewPager);
				adapterViewPager = new MyPagerAdapter(getSupportFragmentManager(), poiList.get(0), listOfQuestions, getApplicationContext());
				viewPager.setAdapter(adapterViewPager);

				TextView textView = (TextView) findViewById(R.id.answer_not_here);
				textView.setText(String.format(getResources().getString(R.string.nothere), poiList.get(0).getName()));
			} else {
				Intent intent = new Intent(this, MainActivity.class);
				startActivity(intent);
			}
		}
	}

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		setIntent(intent);
	}

	@Override
	protected void onResume() {
		super.onResume();
		Intent intent = getIntent();
		Uri uri = intent.getData();
		if (uri != null) {

			SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit();
			editor.putString("oauth_verifier", uri.getQueryParameter("oauth_verifier"));
			editor.putString("oauth_token", uri.getQueryParameter("oauth_token"));
			editor.apply();

			OAuth oAuth = new OAuth(getApplicationContext());
			oAuth.execute();
		}
	}

	@Override
	public void onQuestionFragmentInteraction() {
		int currPos=viewPager.getCurrentItem();
		viewPager.setCurrentItem(currPos+1);
	}

	@Override
	public void onNotHereFragmentInteraction(ArrayList<OsmObject> poiList) {
	}

	@Override
	public void onUploadFragmentInteraction() {
	}

	@Override
	public void onOsmLoginFragmentInteraction(Uri uri) {
	}

	public void notHereClicked(View v)
	{
		Fragment fragment = new NotHereFragment();

		Bundle args = new Bundle();
		args.putParcelableArrayList(NotHereFragment.ARG_PARAM1, this.poiList);
		fragment.setArguments(args);

		// Insert the fragment by replacing any existing fragment
		getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
	}

	public void newPlaceClicked(View v) {
		Fragment fragment = new QuestionFragment();

		Bundle args = new Bundle();
		args.putParcelableArrayList(QuestionFragment.ARG_PARAM1, this.poiList);
		fragment.setArguments(args);

		// Insert the fragment by replacing any existing fragment
		getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();

	}

	public static class MyPagerAdapter extends FragmentPagerAdapter {
		public OsmObjectType listOfQuestions;
		private OsmObject poi;
		private int count;

		public MyPagerAdapter(FragmentManager fragmentManager, OsmObject poi, OsmObjectType listOfQuestions, Context context) {
			super(fragmentManager);
			this.poi = poi;
			this.listOfQuestions = listOfQuestions;
			SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
			if (prefs.getBoolean("logged_in_to_osm", false) == true) {
			//if ( prefs.contains("oauth_token") && prefs.contains("oauth_token_secret")) {
				this.count = listOfQuestions.getNoOfQuestions() +1;
			} else {
				this.count = 1;
			}

		}

		// Returns total number of pages
		@Override
		public int getCount() {
			return this.count;
		}

		// Returns the fragment to display for that page
		@Override
		public Fragment getItem(int position) {
			Log.i(TAG, ""+position);
			Log.i(TAG, ""+count);
//			if (this.count == 1) {
//				return OsmLoginFragment.newInstance();
//			}
			if (position < count -1) {
				return QuestionFragment.newInstance(this.poi, position, this.listOfQuestions);
			} else {
				return UploadFragment.newInstance();
			}
		}

	}
}
