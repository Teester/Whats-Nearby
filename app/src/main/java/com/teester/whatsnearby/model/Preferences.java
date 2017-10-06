package com.teester.whatsnearby.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class Preferences implements PreferencesContract {

	private SharedPreferences prefs;

	public Preferences(Context context) {
		prefs = PreferenceManager.getDefaultSharedPreferences(context);
	}

	@Override
	public String getStringPreference(String preference) {
		return prefs.getString(preference, "");
	}

	@Override
	public boolean getBoolPreference(String preference) {
		return prefs.getBoolean(preference, false);
	}

	@Override
	public void setStringPreference(String preference, String value) {
		prefs.edit().putString(preference, value).apply();
	}

	@Override
	public void setBooleanPreference(String preference, boolean value) {
		prefs.edit().putBoolean(preference, value).apply();
	}
}
