package com.teester.whatsnearby.main;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.teester.whatsnearby.R;
import com.teester.whatsnearby.data.PreferenceList;
import com.teester.whatsnearby.data.source.Preferences;
import com.teester.whatsnearby.data.source.SourceContract;

import java.util.Objects;

public class FragmentDebug
		extends
		Fragment
		implements
		MainActivityContract.DebugView,
		SharedPreferences.OnSharedPreferenceChangeListener {

	private TextView lastQueryTime;
	private TextView lastNotificationTime;
	private TextView lastQuery;
	private TextView querydistance;
	private TextView checkdistance;
	private TextView lastLocation;
	private TextView reason;
	private MainActivityContract.DebugPresenter debugPresenter;
	private SharedPreferences sharedPreferences;
	private Context context;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		context = Objects.requireNonNull(getActivity()).getApplicationContext();
		sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
		SourceContract.Preferences preferences = new Preferences(getContext());
		debugPresenter = new DebugPresenter(this, preferences);
	}

	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		return inflater.inflate(R.layout.fragment_debug, container, false);
	}

	@Override
	public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		this.lastQueryTime = view.findViewById(R.id.debug_last_overpass_query_value);
		this.lastNotificationTime = view.findViewById(R.id.debug_last_notification_value);
		this.lastQuery = view.findViewById(R.id.debug_last_overpass_query_result_value);
		this.querydistance = view.findViewById(R.id.debug_distance_since_last_query_value);
		this.checkdistance = view.findViewById(R.id.debug_distance_since_last_location_check_value);
		this.lastLocation = view.findViewById(R.id.debug_most_recent_location_value);
		this.reason = view.findViewById(R.id.debug_reason);

		debugPresenter.getDetails();
	}

	@Override
	public void onPause() {
		super.onPause();
		sharedPreferences.unregisterOnSharedPreferenceChangeListener(this);
	}

	@Override
	public void onResume() {
		super.onResume();
		sharedPreferences.registerOnSharedPreferenceChangeListener(this);
	}

	@Override
	public void setLastQueryTime(String time, int color) {
		this.lastQueryTime.setText(time);
		this.lastQueryTime.setTextColor(ContextCompat.getColor(context, color));
	}

	@Override
	public void setLastNotificationTime(String notificationTime, int color) {
		this.lastNotificationTime.setText(notificationTime);
		this.lastNotificationTime.setTextColor(ContextCompat.getColor(context, color));
	}

	@Override
	public void setLastQuery(String queryTime) {
		this.lastQuery.setText(queryTime);
	}


	@Override
	public void setQuerydistance(String querydistance, int color) {
		this.querydistance.setText(querydistance);
		this.querydistance.setTextColor(ContextCompat.getColor(context, color));
	}

	@Override
	public void setCheckdistance(String queryTimeSince, int color) {
		this.checkdistance.setText(queryTimeSince);
		this.checkdistance.setTextColor(ContextCompat.getColor(context, color));
	}

	@Override
	public void setLocation(String location) {
		this.lastLocation.setText(location);
	}

	@Override
	public void setReason(String reason) {
		this.reason.setText(reason);
	}

	@Override
	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
		switch (s) {
			case PreferenceList.LATITUDE:
			case PreferenceList.LONGITUDE:
			case PreferenceList.DISTANCE_TO_LAST_LOCATION:
			case PreferenceList.LAST_QUERY_TIME:
			case PreferenceList.LAST_NOTIFICATION_TIME:
			case PreferenceList.DISTANCE_TO_LAST_QUERY:
			case PreferenceList.LAST_QUERY:
			case PreferenceList.LOCATION_ACCURACY:
				debugPresenter.getDetails();
				break;
			default:
				break;
		}
	}
}
