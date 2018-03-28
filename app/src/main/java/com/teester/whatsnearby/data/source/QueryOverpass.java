package com.teester.whatsnearby.data.source;

import android.content.Context;

import com.teester.whatsnearby.BuildConfig;
import com.teester.whatsnearby.Utilities;
import com.teester.whatsnearby.data.Answers;
import com.teester.whatsnearby.data.OsmObject;
import com.teester.whatsnearby.data.PoiList;
import com.teester.whatsnearby.data.PoiTypes;
import com.teester.whatsnearby.data.PreferenceList;
import com.teester.whatsnearby.data.database.AppDatabase;
import com.teester.whatsnearby.data.database.VisitedLocation;
import com.teester.whatsnearby.data.location.LocationJobNotifier;
import com.teester.whatsnearby.data.pois.PoiContract;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class QueryOverpass implements SourceContract.Overpass {

	private double queryLatitude;
	private double queryLongitude;
	private List<OsmObject> poiList = new ArrayList<>();
	private Context context;

	public QueryOverpass(Context context) {
		this.context = context;
	}

	QueryOverpass() {
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
		float measuredAccuracy;
		if (accuracy < 20) {
			measuredAccuracy = 20;
		} else {
			measuredAccuracy = accuracy;
		}

		// Build the Overpass query
		// getting the centre of nodes, ways and relations a given radius around a location for different types
		String overpassLocation = String.format("around:%s,%s,%s", measuredAccuracy, latitude, longitude);
		String nwr = "%1$s[~\"^(%2$s)$\"~\".\"](%3$s);";
		String types = "shop|amenity|leisure|tourism";

		String node = String.format(nwr, "node", types, overpassLocation);
		String way = String.format(nwr, "way", types, overpassLocation);
		String relation = String.format(nwr, "relation", types, overpassLocation);

		return String.format("https://www.overpass-api.de/api/interpreter?data=[out:json][timeout:25];(%s%s%s);out%%20center%%20meta%%20qt;", node, way, relation);
	}

	@Override
	public String queryOverpassApi(String urlString) {

		String resultToDisplay;
		String userAgent = String.format("%s/%s", "whatsnearby", BuildConfig.VERSION_NAME);
		InputStream in;
		try {
			URL url = new URL(urlString);
			HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
			urlConnection.setRequestProperty("User-Agent", userAgent);
			in = new BufferedInputStream(urlConnection.getInputStream());
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}

		resultToDisplay = new Scanner(in, "UTF-8").useDelimiter("\\A").next();
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

							PoiContract poitype = PoiTypes.getPoiType(type);
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
				e.printStackTrace();
			}

			PoiList.getInstance().setPoiList(poiList);
			PoiList.getInstance().sortList(queryLatitude, queryLongitude);
			prepareNotification();
		}
	}

	@Override
	public void queryOverpass(double latitude, double longitude, float accuracy) {
		SourceContract.Preferences preferences = new Preferences(context);
		preferences.setLongPreference(PreferenceList.LAST_QUERY_TIME, System.currentTimeMillis());
		this.queryLatitude = latitude;
		this.queryLongitude = longitude;
		String overpassUrl = getOverpassUri(latitude, longitude, accuracy);
		String overpassQuery = queryOverpassApi(overpassUrl);
		preferences.setStringPreference(PreferenceList.LAST_QUERY, overpassQuery);
		processResult(overpassQuery);
	}

	/**
	 * Gets the type of location e.g. hairdresser, pharmacy, stadium etc
	 *
	 * @param tags - the JSON object of the location
	 * @return - the location type
	 * @throws JSONException - if it's not an amenity, shop, tourism or leisure
	 */
	public String getType(JSONObject tags) throws JSONException {
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
			PoiContract type = PoiTypes.getPoiType(poi.getType());
			int drawable = type.getObjectIcon();
			System.out.println(String.format("At %s before: %s", poi.getName(), recentlyVisited));
			if (!recentlyVisited) {
				updateDatabase(poi);
				LocationJobNotifier.createNotification(context, poi.getName(), drawable);
			}
		} else {
			LocationJobNotifier.cancelNotifications(context);
		}
	}

	private boolean checkDatabaseForLocation(long osmId) {
		SourceContract.Preferences preferences = new Preferences(context);
		if (BuildConfig.DEBUG && preferences.getBooleanPreference(PreferenceList.DEBUG_MODE)) {
			return false;
		}

		AppDatabase db = AppDatabase.getAppDatabase(context);
		VisitedLocation location = db.visitedLocationDao().findByOsmId(osmId);
		List<VisitedLocation> list = db.visitedLocationDao().getAllVisitedLocations();

		for (int i = 0; i < list.size(); i++) {
			Date timeVisited = new Date(list.get(i).getTimeVisited());
			System.out.println(String.format("name: %s, Last visited: %s", list.get(i).getName(), timeVisited));
		}

		long time = System.currentTimeMillis() - (7 * 24 * 60 * 60 * 1000);
		return location != null && location.getTimeVisited() > time;
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
