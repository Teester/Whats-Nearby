package com.teester.whatsnearby.data;

import com.teester.whatsnearby.data.pois.PoiContract;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class PoiListTest {

	private OsmObject osmObject = new OsmObject(1, "", "", "", 1, 1, 1);

	@Before
	public void setUp() {
		List<OsmObject> pois = new ArrayList<>();
		pois.add(this.osmObject);
		PoiList.getInstance().setPoiList(pois);
	}

	@After
	public void tearDown() {
		PoiList.getInstance().clearPoiList();
	}

	@Test
	public void getPoiFromPoiList() {
		OsmObject expectedPoi = this.osmObject;
		OsmObject actualPoi = PoiList.getInstance().getPoiList().get(0);

		assertEquals(expectedPoi.getName(), actualPoi.getName());
		assertEquals(expectedPoi.getOsmType(), actualPoi.getOsmType());
		assertEquals(expectedPoi.getType(), actualPoi.getType());
		assertEquals(expectedPoi.getId(), actualPoi.getId());
		assertEquals(expectedPoi.getLatitude(), actualPoi.getLatitude(), 0);
		assertEquals(expectedPoi.getLongitude(), actualPoi.getLongitude(), 0);
		assertEquals(expectedPoi.getDistance(), actualPoi.getDistance(), 0);
	}

	@Test
	public void checkPoiListIsCleared() {
		PoiList.getInstance().clearPoiList();
		List<OsmObject> actualResult = PoiList.getInstance().getPoiList();

		assertEquals(0, actualResult.size());
	}

	@Test
	public void createAlternateList() {
		List<OsmObject> alternateList = PoiList.getInstance().createAlternateList();

		assertEquals(0, alternateList.size());
	}

	@Test
	public void checkPoiType() {
		OsmObject osmObject = PoiList.getInstance().getPoiList().get(0);

		PoiContract actualObject = PoiTypes.getPoiType(osmObject.getOsmType());
		PoiContract expectedObject = PoiTypes.getPoiType(this.osmObject.getOsmType());

		assertEquals(expectedObject, actualObject);
	}

	@Test
	public void sortPoiList() {
		OsmObject object1 = new OsmObject(2, "", "", "", 3, 3, 1);
		OsmObject object2 = new OsmObject(3, "", "", "", 1, 1, 1);
		OsmObject object3 = new OsmObject(4, "", "", "", 2, 2, 1);
		List<OsmObject> list = new ArrayList<>();
		list.add(object1);
		list.add(object2);
		list.add(object3);

		PoiList.getInstance().setPoiList(list);
		PoiList.getInstance().sortList(0, 0);

		assertEquals(object1.getId(), PoiList.getInstance().getPoiList().get(2).getId());
		assertEquals(object2.getId(), PoiList.getInstance().getPoiList().get(0).getId());
		assertEquals(object3.getId(), PoiList.getInstance().getPoiList().get(1).getId());
	}
}