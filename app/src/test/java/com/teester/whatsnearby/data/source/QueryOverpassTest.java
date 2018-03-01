package com.teester.whatsnearby.data.source;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class QueryOverpassTest {

	public QueryOverpass queryOverpass;

	@Before
	public void setUp() {
		queryOverpass = new QueryOverpass();
	}

	@Test
	public void testGenerateOverpassUri() {
		double latitude = 53;
		double longitude = -7;
		float accuracy = 50;
		String expected_uri = "https://www.overpass-api.de/api/interpreter?data=[out:json][timeout:25];(node[~\"^(shop|amenity|leisure|tourism)$\"~\".\"](around:50.0,53.0,-7.0);way[~\"^(shop|amenity|leisure|tourism)$\"~\".\"](around:50.0,53.0,-7.0);relation[~\"^(shop|amenity|leisure|tourism)$\"~\".\"](around:50.0,53.0,-7.0););out%20center%20meta%20qt;";

		String uri = queryOverpass.getOverpassUri(latitude, longitude, accuracy);
		assertEquals(expected_uri, uri);
	}

	@Test
	public void testGetOverpassResultPoiType() throws JSONException {
		String expected_result = "hairdresser";
		JSONObject json = new JSONObject().put("amenity", expected_result);

		String result = queryOverpass.getType(json);
		assertEquals(expected_result, result);
	}

}