package com.teester.whatsnearby.data;

public class CurrentPoi {

	private static CurrentPoi INSTANCE;
	private OsmObject poi;

	public CurrentPoi getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new CurrentPoi();
		}
		return INSTANCE;
	}

	public OsmObject getCurrentPoi() {
		return poi;
	}

	public void setCurrentPoi(OsmObject osmObject) {
		poi = osmObject;
	}

	public void clearCurrentPoi() {
		INSTANCE = null;
	}
}
