package com.teester.whatsnearby.model;

public interface PreferencesContract {

	String getStringPreference(String preference);

	boolean getBooleanPreference(String preference);

	void setStringPreference(String preference, String value);

	void setBooleanPreference(String preference, boolean value);

	long getLongPreference(String preference);

	void setLongPreference(String preference, long value);
}
