package com.teester.whatsnearby.view;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.teester.whatsnearby.data.PreferenceList;
import com.teester.whatsnearby.data.pois.PoiContract;
import com.teester.whatsnearby.data.source.Preferences;
import com.teester.whatsnearby.questions.question.QuestionFragment;
import com.teester.whatsnearby.questions.upload.UploadFragment;

public class MyPagerAdapter extends FragmentPagerAdapter {

	private int count;

	public MyPagerAdapter(FragmentManager fragmentManager, PoiContract listOfQuestions, Context context) {
		super(fragmentManager);
		boolean logged_in = new Preferences(context).getBooleanPreference(PreferenceList.LOGGED_IN_TO_OSM);
		if (logged_in) {
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
		//if (position == 0) {
		//	return IntroFragment.newInstance(this.poi);
		//} else if (position < count - 1) {
		if (position < count - 1) {
			return QuestionFragment.newInstance(position);
		} else {
			return UploadFragment.newInstance();
		}
		//}
	}
}