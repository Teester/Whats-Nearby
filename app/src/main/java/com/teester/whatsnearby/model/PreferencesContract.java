package com.teester.whatsnearby.model;

public interface PreferencesContract {

	String getStringPreference(String preference);

	boolean getBoolPreference(String preference);

	void setStringPreference(String preference, String value);

	void setBooleanPreference(String preference, boolean value);
}
