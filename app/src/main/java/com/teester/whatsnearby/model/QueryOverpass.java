package com.teester.whatsnearby.model;

import android.content.Context;
import android.location.Location;
import android.os.AsyncTask;
import android.util.Log;

import com.teester.whatsnearby.model.data.PoiList;
import com.teester.whatsnearby.model.data.PoiTypes;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class QueryOverpass {

	private static final String TAG = QueryOverpass.class.getSimpleName();
	private Location queryLocation;
	private Context context;

	public QueryOverpass(Location location, Context context) {

		this.context = context;
		this.queryLocation = location;

		String overpassUrl = getOverpassUri(location.getLatitude(), location.getLongitude(), location.getAccuracy());
		CallAPI overpassQuery = new CallAPI();
		overpassQuery.execute(overpassUrl);
	}

	/**
	 * Gets an overpass query url from a given location and accuracy
	 *
	 * @param latitude  location latitude
	 * @param longitude location longitude
	 * @param accuracy  accuracy of location in metres
	 * @return an Overpass url getting items in a radius of the location
	 */
	private String getOverpassUri(double latitude, double longitude, float accuracy) {
		if (accuracy < 20) {
			accuracy = 20;
		}

		// Build the Overpass query
		// getting the centre of nodes, ways and relations a given radius around a location for different types
		String overpassLocation = String.format("around:%s,%s,%s", accuracy, latitude, longitude);
		String nwr = "node['%2$s'](%1$s);way['%2$s'](%1$s);relation['%2$s'](%1$s);";

		String shop = String.format(nwr, overpassLocation, "shop");
		String amenity = String.format(nwr, overpassLocation, "amenity");
		String leisure = String.format(nwr, overpassLocation, "leisure");
		String tourism = String.format(nwr, overpassLocation, "tourism");

		String overpassUrl = String.format("http://www.overpass-api.de/api/interpreter?data=[out:json][timeout:25];(%s%s%s%s);out%%20center%%20meta%%20qt;", shop, amenity, leisure, tourism);

		return overpassUrl;
	}

	public class CallAPI extends AsyncTask<String, String, String> {

		@Override
		protected String doInBackground(String... params) {

			String urlString = params[0]; // URL to call
			String resultToDisplay = "";
			InputStream in = null;

			try {
				URL url = new URL(urlString);
				HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
				in = new BufferedInputStream(urlConnection.getInputStream());
			} catch (Exception e) {
				System.out.println(e.getMessage());
				return e.getMessage();
			}

			try {
				resultToDisplay = IOUtils.toString(in, "UTF-8");
				//to [convert][1] byte stream to a string
			} catch (IOException e) {
				e.printStackTrace();
			}
			return resultToDisplay;
		}

		@Override
		protected void onPostExecute(String result) {

			ArrayList<OsmObject> poiList = new ArrayList<OsmObject>();

			if (result != null) {
				try {
					JSONObject jsonObj = new JSONObject(result);

					// Getting JSON Array node
					JSONArray elements = jsonObj.getJSONArray("elements");

					// looping through All elements
					for (int i = 0; i < elements.length(); i++) {
						JSONObject e = elements.getJSONObject(i);
						if (e.has("tags")) {
							JSONObject tags = e.getJSONObject("tags");
							if (tags.has("name")) {
								int id = e.getInt("id");
								String osmType = e.getString("type");

								JSONObject c = new JSONObject();
								if (e.has("center")) {
									c = e.getJSONObject("center");
								} else {
									c = e;
								}
								double lat = c.getDouble("lat");
								double lon = c.getDouble("lon");
								Location elementLocation = new Location("dummyprovider");
								elementLocation.setLatitude(lat);
								elementLocation.setLongitude(lon);
								double distance = queryLocation.distanceTo(elementLocation);
								String name = tags.getString("name");
								String type = "";

								if (tags.has("amenity")) {
									type = tags.getString("amenity");
								}
								if (tags.has("shop")) {
									type = tags.getString("shop");
								}
								if (tags.has("tourism")) {
									type = tags.getString("tourism");
								}
								if (tags.has("leisure")) {
									type = tags.getString("leisure");
								}
								OsmObjectType poitype = PoiTypes.getPoiType(type);
								if (poitype != null) {
									OsmObject object = new OsmObject(id, osmType, name, type, lat, lon, elementLocation.distanceTo(queryLocation));
									poiList.add(object);
								}
							}
						}
					}
				} catch (final JSONException e) {
					Log.e(TAG, "Json parsing error: " + e.getMessage());
				}

				// Arrange the locations by distance from location
				Collections.sort(poiList, new Comparator<OsmObject>() {
					public int compare(OsmObject p1, OsmObject p2) {
						return (int) (queryLocation.distanceTo(p1.getLocation()) - queryLocation.distanceTo(p2.getLocation()));
					}
				});

				if (poiList.size() > 0) {
					PoiList.getInstance().setPoiList(poiList);

					OsmObject poi = poiList.get(0);
					OsmObjectType type = PoiTypes.getPoiType(poi.getType());
					int drawable = type.getObjectIcon();
					Notifier.createNotification(context, poi.getName(), drawable);
				}
			}
		}
	}


}
