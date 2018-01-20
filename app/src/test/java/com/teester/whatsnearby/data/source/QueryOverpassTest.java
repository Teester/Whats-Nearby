package com.teester.whatsnearby.data.source;

import com.teester.whatsnearby.data.location.Notifier;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static junit.framework.TestCase.assertEquals;

public class QueryOverpassTest {

	public QueryOverpass queryOverpass;

	@Mock
	private Notifier notifier;

	@Before
	public void setUp() {
		queryOverpass = new QueryOverpass();
	}

	@Test
	public void test_generate_overpass_uri() {
		double latitude = 53;
		double longitude = -7;
		float accuracy = 50;
		String expected_uri = "http://www.overpass-api.de/api/interpreter?data=[out:json][timeout:25];(node['shop'](around:50.0,53.0,-7.0);way['shop'](around:50.0,53.0,-7.0);relation['shop'](around:50.0,53.0,-7.0);node['amenity'](around:50.0,53.0,-7.0);way['amenity'](around:50.0,53.0,-7.0);relation['amenity'](around:50.0,53.0,-7.0);node['leisure'](around:50.0,53.0,-7.0);way['leisure'](around:50.0,53.0,-7.0);relation['leisure'](around:50.0,53.0,-7.0);node['tourism'](around:50.0,53.0,-7.0);way['tourism'](around:50.0,53.0,-7.0);relation['tourism'](around:50.0,53.0,-7.0););out%20center%20meta%20qt;";

		String uri = queryOverpass.getOverpassUri(latitude, longitude, accuracy);
		assertEquals(expected_uri, uri);
	}

	@Test
	public void test_get_overpass_result_poi_type() throws JSONException {
		String expected_result = "hairdresser";
		JSONObject json = new JSONObject().put("amenity", expected_result);

		String result = queryOverpass.getType(json);
		assertEquals(expected_result, result);
	}

}