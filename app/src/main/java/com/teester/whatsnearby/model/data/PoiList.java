package com.teester.whatsnearby.model.data;

import com.teester.whatsnearby.model.OsmObject;

import java.util.List;

/**
 * A Singleton to store the current POI list
 */
public class PoiList {

	private static PoiList INSTANCE;
	private List<OsmObject> poiList;

	private PoiList() {

	}

	public static synchronized PoiList getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new PoiList();
		}
		return INSTANCE;
	}

	/**
	 * Get a list of points of interest
	 *
	 * @return
	 */
	public List<OsmObject> getPoiList() {
		return poiList;
	}

	/**
	 * Set a list of points of interest
	 *
	 * @param poiList
	 */
	public void setPoiList(List<OsmObject> poiList) {
		this.poiList = poiList;
	}

	/**
	 * Create a list of POIs except the first one
	 *
	 * @return
	 */
	public List<OsmObject> createAlternateList() {
		return poiList.subList(1, poiList.size());
	}

	/**
	 * Clears the list of points of interest
	 */
	public void clearPoiList() {
		INSTANCE = null;
	}
}
