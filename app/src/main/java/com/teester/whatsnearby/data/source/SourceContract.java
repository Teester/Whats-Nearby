package com.teester.whatsnearby.data.source;

public interface SourceContract {

	interface Preferences {
		String getStringPreference(String preference);

		boolean getBooleanPreference(String preference);

		void setStringPreference(String preference, String value);

		void setBooleanPreference(String preference, boolean value);

		long getLongPreference(String preference);

		void setLongPreference(String preference, long value);

		float getFloatPreference(String preference);

		void setFloatPreference(String preference, float value);
	}

	interface Overpass {

		String getOverpassUri(double latitude, double longitude, float accuracy);

		String queryOverpassApi(String urlString);

		void processResult(String result);

		void queryOverpass(double latitude, double longitude, float accuracy);
	}

	interface Upload {

		void Upload();
	}

	interface OAuth {

		void processOAuth();
	}
}
