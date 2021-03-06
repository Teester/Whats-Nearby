package com.teester.whatsnearby;

import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;

import static junit.framework.TestCase.assertEquals;

public class UtilitiesTest {

	@Test
	public void splitqueryValidUrl() throws URISyntaxException, UnsupportedEncodingException {
		String uri = "http://www.overpass-api.de/api/interpreter?data=[out:json]";
		URI url = new URI(uri);

		Map<String, List<String>> map = Utilities.splitQuery(url);

		assertEquals("[out:json]", map.get("data").get(0));
	}

	@Test
	public void calculateDistance() {
		double lat1 = 53.1;
		double lon1 = -7.5;
		double lat2 = 53.2;
		double lon2 = -7.4;

		float distance = Utilities.computeDistance(lat1, lon1, lat2, lon2);
		assertEquals(12985, distance, 1);
	}
}