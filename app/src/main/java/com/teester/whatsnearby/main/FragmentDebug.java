package com.teester.whatsnearby.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.teester.whatsnearby.R;
import com.teester.whatsnearby.data.source.Preferences;
import com.teester.whatsnearby.data.source.SourceContract;

public class FragmentDebug extends Fragment implements MainActivityContract.DebugView {

	private TextView lastQueryTime;
	private TextView lastNotificationTime;
	private TextView lastQuery;
	private MainActivityContract.DebugPresenter debugPresenter;
	private SourceContract.Preferences preferences;

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		SourceContract.Preferences preferences = new Preferences(getContext());
		debugPresenter = new DebugPresenter(this, preferences);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		return inflater.inflate(R.layout.fragment_debug, container, false);
	}

	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		this.lastQueryTime = view.findViewById(R.id.textView6);
		this.lastNotificationTime = view.findViewById(R.id.textView7);
		this.lastQuery = view.findViewById(R.id.textView8);

		debugPresenter.getDetails();
	}

	@Override
	public void setLastQueryTime(String time, String notificationTime, String queryTime) {
		this.lastQueryTime.setText(time);
		this.lastNotificationTime.setText(notificationTime);
		this.lastQuery.setText(queryTime);
	}

	@Override
	public void setPresenter(MainActivityContract.DebugPresenter presenter) {

	}
}
