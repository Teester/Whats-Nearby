package com.teester.whatsnearby.data.source;

import android.content.Context;

import com.teester.whatsnearby.BuildConfig;
import com.teester.whatsnearby.Utilities;
import com.teester.whatsnearby.data.Answers;
import com.teester.whatsnearby.data.OsmObject;
import com.teester.whatsnearby.data.OsmObjectType;
import com.teester.whatsnearby.data.PoiList;
import com.teester.whatsnearby.data.PoiTypes;
import com.teester.whatsnearby.data.localDatabase.AppDatabase;
import com.teester.whatsnearby.data.localDatabase.VisitedLocation;
import com.teester.whatsnearby.data.location.Notifier;

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
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class QueryOverpass implements SourceContract.Overpass {

	private static final String TAG = QueryOverpass.class.getSimpleName();
	private double queryLatitude;
	private double queryLongitude;
	private double queryAccuracy;
	private List<OsmObject> poiList = new ArrayList<OsmObject>();
	private Context context;

	public QueryOverpass(Context context) {
		this.context = context;
	}

	public QueryOverpass() {
	}

	/**
	 * Gets an overpass query url from a given location and accuracy
	 *
	 * @param latitude  location latitude
	 * @param longitude location longitude
	 * @param accuracy  accuracy of location in metres
	 * @return an Overpass url getting items in a radius of the location
	 */
	@Override
	public String getOverpassUri(double latitude, double longitude, float accuracy) {
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

	@Override
	public String queryOverpassApi(String urlString) {

		String resultToDisplay = "";
		String userAgent = String.format("%s/%s", "whatsnearby", BuildConfig.VERSION_NAME);
		InputStream in = null;
		try {
			URL url = new URL(urlString);
			HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
			urlConnection.setRequestProperty("User-Agent", userAgent);
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
	public void processResult(String result) {
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

							long id = e.getLong("id");

							String osmType = e.getString("type");

							if (e.has("center")) {
								e = e.getJSONObject("center");
							}
							double lat = e.getDouble("lat");
							double lon = e.getDouble("lon");
							float distance = Utilities.computeDistance(lat, lon, queryLatitude, queryLongitude);
							String name = tags.getString("name");

							String type = getType(tags);

							OsmObjectType poitype = PoiTypes.getPoiType(type);
							if (poitype != null) {
								OsmObject object = new OsmObject(id, osmType, name, type, lat, lon, distance);
								Iterator<String> keysIterator = tags.keys();
								while (keysIterator.hasNext()) {
									String key = keysIterator.next();
									if (tags.get(key) instanceof String) {
										String value = tags.getString(key);
										object.addTag(key, value);
									}
								}
								poiList.add(object);
							}
						}
					}
				}
			} catch (final JSONException e) {
			}

			PoiList.getInstance().setPoiList(poiList);
			PoiList.getInstance().sortList(queryLatitude, queryLongitude);
			prepareNotification();
		}
	}

	@Override
	public void queryOverpass(double latitude, double longitude, float accuracy) {

		queryLatitude = latitude;
		queryLongitude = longitude;
		queryAccuracy = accuracy;
		String overpassUrl = getOverpassUri(latitude, longitude, accuracy);
		String overpassQuery = queryOverpassApi(overpassUrl);
		processResult(overpassQuery);
	}

	/**
	 * Gets the type of location e.g. hairdresser, pharmacy, stadium etc
	 *
	 * @param tags - the JSON object of the location
	 * @return - the location type
	 * @throws JSONException - if it's not an amenity, shop, tourism or leisure
	 */
	String getType(JSONObject tags) throws JSONException {
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
		return type;
	}

	/**
	 * Prepares the notification to be displayed.
	 */
	private void prepareNotification() {
		if (poiList.size() > 0) {
			Answers.setPoiDetails(poiList.get(0));

			OsmObject poi = poiList.get(0);
			boolean recentlyVisited = checkDatabaseForLocation(poi.getId());
			OsmObjectType type = PoiTypes.getPoiType(poi.getType());
			int drawable = type.getObjectIcon();
			System.out.println(String.format("At %s before: %s", poi.getName(), recentlyVisited));
			if (recentlyVisited == false) {
				updateDatabase(poi);
				Notifier.createNotification(context, poi.getName(), drawable);
			}
		} else {
			Notifier.cancelNotifictions(context);
		}
	}

	private boolean checkDatabaseForLocation(long osmId) {
		AppDatabase db = AppDatabase.getAppDatabase(context);
		VisitedLocation location = db.visitedLocationDao().findByOsmId(osmId);
		List<VisitedLocation> list = db.visitedLocationDao().getAllVisitedLocations();
		for (int i = 0; i < list.size(); i++) {
			System.out.println("name: " + list.get(i).getName() + new Date(list.get(i).getTimeVisited()));
		}
		if (location != null) {
			if (location.getTimeVisited() > System.currentTimeMillis() - (7 * 24 * 60 * 60 * 1000)) {
				return true;
			}
		}

		return false;

	}

	private void updateDatabase(OsmObject osmObject) {
		AppDatabase db = AppDatabase.getAppDatabase(context);
		VisitedLocation location = db.visitedLocationDao().findByOsmId(osmObject.getId());
		if (location != null) {
			location.setTimeVisited(System.currentTimeMillis());
			db.visitedLocationDao().update(location);
		} else {
			location = new VisitedLocation(osmObject);
			db.visitedLocationDao().insert(location);
		}
	}

}
