package com.teester.whatsnearby.questions;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.teester.whatsnearby.R;
import com.teester.whatsnearby.data.OsmObject;
import com.teester.whatsnearby.data.OsmObjectType;
import com.teester.whatsnearby.data.source.OAuth;
import com.teester.whatsnearby.data.source.Preferences;
import com.teester.whatsnearby.data.source.SourceContract;
import com.teester.whatsnearby.main.MainActivity;
import com.teester.whatsnearby.questions.intro.IntroFragment;
import com.teester.whatsnearby.questions.nothere.NotHereFragment;
import com.teester.whatsnearby.questions.question.QuestionFragment;
import com.teester.whatsnearby.view.MyPagerAdapter;
import com.teester.whatsnearby.view.NonSwipeableViewPager;

import java.net.URI;
import java.net.URISyntaxException;

public class QuestionsActivity extends AppCompatActivity
		implements QuestionsActivityContract.View,
		NotHereFragment.OnFragmentInteractionListener,
		QuestionFragment.OnFragmentInteractionListener,
		IntroFragment.OnFragmentInteractionListener {

	private NonSwipeableViewPager viewPager;
	private TextView textView;
	private QuestionsActivityContract.Presenter questionsPresenter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_container);

		this.textView = findViewById(R.id.answer_not_here);
		this.viewPager = findViewById(R.id.viewPager);

		SourceContract.Preferences preferences = new Preferences(getApplicationContext());
		questionsPresenter = new QuestionsPresenter(this, preferences);

		NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
		notificationManager.cancelAll();
	}

	@Override
	protected void onResume() {
		super.onResume();
		questionsPresenter.addPoiNameToTextview();
	}

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		setIntent(intent);
		URI url = null;
		try {
			url = new URI(intent.getData().toString());
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		questionsPresenter.assessIntentData(url);
	}

	public void onQuestionFragmentInteraction() {
		int currPos=viewPager.getCurrentItem();
		viewPager.setCurrentItem(currPos+1);
	}

	public void onIntroFragmentInteraction() {
		int currPos=viewPager.getCurrentItem();
		viewPager.setCurrentItem(currPos+1);
	}

	public void notHereClicked(View v)
	{
		Fragment fragment = new NotHereFragment();
		getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
	}

	public void newPlaceClicked(View v) {
		Fragment fragment = new QuestionFragment();
		getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();

	}

	@Override
	public void setViewPager(OsmObject poi, OsmObjectType listOfQuestions) {
		FragmentPagerAdapter adapterViewPager = new MyPagerAdapter(getSupportFragmentManager(), poi, listOfQuestions, getApplicationContext());
		viewPager.setAdapter(adapterViewPager);
	}

	@Override
	public void startNewActivity() {
		Intent intent = new Intent(this, MainActivity.class);
		startActivity(intent);
	}

	@Override
	public void makeTextViewInvisible() {
		textView.setVisibility(View.INVISIBLE);
	}

	@Override
	public void setTextviewText(String name) {
		textView.setText(String.format(getResources().getString(R.string.nothere), name));
	}

	@Override
	public void startOAuth() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				new OAuth(getApplicationContext());
			}
		}).start();
	}

	@Override
	public void onNotHereFragmentInteraction() {
		// required empty method
	}
}
