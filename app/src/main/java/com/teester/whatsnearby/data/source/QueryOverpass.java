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
		} else if (accuracy > 100) {
			measuredAccuracy = 100;
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

	/**
	 * Querys overpass with a given string and gets json back
	 *
	 * @param urlString the url to query
	 * @return json retrieved from overpass
	 */
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

		updateDatabase();
		resultToDisplay = new Scanner(in, "UTF-8").useDelimiter("\\A").next();
		return resultToDisplay;
	}

	/**
	 * Converts the json returned from overpass into usable objects
	 *
	 * @param result The json from overpass
	 */
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

	/**
	 * Entry point to query Overpass
	 *
	 * @param latitude Location latitude
	 * @param longitude Location longitude
	 * @param accuracy Location accuracy
	 */
	@Override
	public void queryOverpass(double latitude, double longitude, float accuracy) {
		SourceContract.Preferences preferences = new Preferences(context);
		preferences.setLongPreference(PreferenceList.LAST_QUERY_TIME, System.currentTimeMillis());
		this.queryLatitude = latitude;
		this.queryLongitude = longitude;

		if (!checkDatabaseForLocation()) {
			String overpassUrl = getOverpassUri(latitude, longitude, accuracy);
			String overpassQuery = queryOverpassApi(overpassUrl);
			preferences.setStringPreference(PreferenceList.LAST_QUERY, overpassQuery);
			processResult(overpassQuery);
		}

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
	 * Checks the room database to see if we've been here before recently
	 *
	 * @param currentLocation the current location
	 * @return true if we've been at a location near here in the last week
	 */
	private boolean checkDatabaseForLocation() {
		final long time = System.currentTimeMillis() - (7 * 24 * 60 * 60 * 1000);

		SourceContract.Preferences preferences = new Preferences(context);
		if (BuildConfig.DEBUG && preferences.getBooleanPreference(PreferenceList.DEBUG_MODE)) {
			return false;
		}

		AppDatabase db = AppDatabase.getAppDatabase(context);
		List<VisitedLocation> locations = db.visitedLocationDao().findByLocation(this.queryLatitude, this.queryLongitude);
		for (int i = 0; i < locations.size(); i++) {
			VisitedLocation location = locations.get(i);
			float distance = Utilities.computeDistance(this.queryLatitude, this.queryLongitude, location.getLatitude(), location.getLongitude());
			if (distance < 20 && location.getTimeVisited() > time) {
				String notQueryReason = preferences.getStringPreference(PreferenceList.NOT_QUERY_REASON);
				notQueryReason += "• Been notified at this location in the past week\n";
				System.out.println(notQueryReason);
				preferences.setStringPreference(PreferenceList.NOT_QUERY_REASON, notQueryReason);
				return true;
			}
		}

		return false;
	}
	/**
	 * Prepares the notification to be displayed.
	 */
	private void prepareNotification() {
		if (poiList.size() > 0) {
			Answers.setPoiDetails(poiList.get(0));

			OsmObject poi = poiList.get(0);
			PoiContract type = PoiTypes.getPoiType(poi.getType());
			int drawable = type.getObjectIcon();

			LocationJobNotifier.createNotification(context, poi.getName(), drawable);
		} else {
			LocationJobNotifier.cancelNotifications(context);
		}
	}

	/**
	 * Adds an object to the room database
	 */
	private void updateDatabase() {
		boolean beenHereBefore = false;
		AppDatabase db = AppDatabase.getAppDatabase(context);
		OsmObject osmObject = new OsmObject(0, "", "", "", this.queryLatitude, this.queryLongitude, 0);
		VisitedLocation visitedLocation = new VisitedLocation(osmObject);
		List<VisitedLocation> locationList = db.visitedLocationDao().findByLocation(queryLatitude, queryLongitude);
		for (int i = 0; i < locationList.size(); i++) {
			VisitedLocation location = locationList.get(i);
			float distance = Utilities.computeDistance(location.getLatitude(), location.getLongitude(), queryLatitude, queryLongitude);
			if (distance < 20) {
				beenHereBefore = true;
			}
		}

		if (!beenHereBefore) {
			db.visitedLocationDao().insert(visitedLocation);
		}
	}

}
