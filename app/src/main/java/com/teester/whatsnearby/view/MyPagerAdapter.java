package com.teester.whatsnearby.view;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import com.teester.whatsnearby.data.OsmObject;
import com.teester.whatsnearby.data.OsmObjectType;
import com.teester.whatsnearby.data.source.Preferences;
import com.teester.whatsnearby.questions.question.QuestionFragment;
import com.teester.whatsnearby.questions.upload.UploadFragment;

public class MyPagerAdapter extends FragmentPagerAdapter {

	private static final String TAG = MyPagerAdapter.class.getSimpleName();

	public OsmObjectType listOfQuestions;
	private OsmObject poi;
	private int count;

	public MyPagerAdapter(FragmentManager fragmentManager, OsmObject poi, OsmObjectType listOfQuestions, Context context) {
		super(fragmentManager);
		this.poi = poi;
		this.listOfQuestions = listOfQuestions;
		Log.i(TAG, "" + listOfQuestions.toString());
		boolean logged_in = new Preferences(context).getBooleanPreference("logged_in_to_osm");
		if (logged_in == true) {
			this.count = listOfQuestions.getNoOfQuestions() + 1;
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
		if (position < count - 1) {
			return QuestionFragment.newInstance(this.poi, position, this.listOfQuestions);
		} else {
			return UploadFragment.newInstance();
		}
	}
}