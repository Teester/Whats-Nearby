package com.teester.whatsnearby.data.source;

public interface SourceContract {

	interface Preferences {
		String getStringPreference(String preference);

		boolean getBooleanPreference(String preference);

		Void setStringPreference(String preference, String value);

		void setBooleanPreference(String preference, boolean value);

		long getLongPreference(String preference);

		void setLongPreference(String preference, long value);

		float getFloatPreference(String preference);

		void setFloatPreference(String preference, float value);

		double getDoublePreference(String preference);

		void setDoublePreference(String preference, double value);
	}

	interface Overpass {

		String getOverpassUri(double latitude, double longitude, float accuracy);

		String queryOverpassApi(String urlString);

		void processResult(String result);

		void queryOverpass(double latitude, double longitude, float accuracy);
	}

	interface upload {

		void uploadToOsm();

		void setUsername();
	}

	interface oAuth {

		void processOAuth();
	}
}
