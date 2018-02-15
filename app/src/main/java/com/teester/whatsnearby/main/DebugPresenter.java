package com.teester.whatsnearby.main;

import com.teester.whatsnearby.data.source.SourceContract;

public class DebugPresenter implements MainActivityContract.DebugPresenter {

	private MainActivityContract.DebugView view;
	private SourceContract.Preferences preferences;

	public DebugPresenter(MainActivityContract.DebugView view, SourceContract.Preferences preferences) {
		this.view = view;
		this.preferences = preferences;
	}

	@Override
	public void init() {

	}

	@Override
	public void destroy() {

	}

	@Override
	public void getDetails() {
		String lastQueryTime = preferences.getStringPreference("last_query_time");
		String lastNotificationTime = preferences.getStringPreference("last_notification_time");
		String lastQuery = preferences.getStringPreference("last_query");

		view.setLastQueryTime(lastQueryTime, lastNotificationTime, lastQuery);
	}
}
