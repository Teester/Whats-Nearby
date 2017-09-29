package com.teester.whatsnearby;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.util.Log;

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

import static android.content.Context.NOTIFICATION_SERVICE;

public class QueryOverpass {

	private static final String TAG = QueryOverpass.class.getSimpleName();
	private Location queryLocation;
	private Context context;

	public QueryOverpass(Location location, Context context) {

		this.context = context;
		this.queryLocation = location;

		String overpassUrl = getOverpassUri(location);
		CallAPI overpassQuery = new CallAPI();
		overpassQuery.execute(overpassUrl);
	}

	private static Bitmap getBitmapFromVectorDrawable(Context context, int drawableId) {
		Drawable drawable = ContextCompat.getDrawable(context, drawableId);
		if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
			drawable = (DrawableCompat.wrap(drawable)).mutate();
		}

		Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(),
				drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(bitmap);
		drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
		drawable.draw(canvas);

		return bitmap;
	}

	private String getOverpassUri(Location location) {
		float accuracy = location.getAccuracy();
		if (accuracy < 20) {
			accuracy = 20;
		}

		// Build the Overpass query
		// getting the centre of nodes, ways and relations a given radius around a location for different types
		String overpassLocation = String.format("around:%s,%s,%s", accuracy, location.getLatitude(), location.getLongitude());
		String nwr = "node['%2$s'](%1$s);way['%2$s'](%1$s);relation['%2$s'](%1$s);";

		String shop = String.format(nwr, overpassLocation, "shop");
		String amenity = String.format(nwr, overpassLocation, "amenity");
		String leisure = String.format(nwr, overpassLocation, "leisure");
		String tourism = String.format(nwr, overpassLocation, "tourism");

		String overpassUrl = String.format("http://www.overpass-api.de/api/interpreter?data=[out:json][timeout:25];(%s%s%s%s);out%%20center%%20meta%%20qt;", shop, amenity, leisure, tourism);
		Log.d(TAG, overpassUrl);
		return overpassUrl;
	}

	private void notifyLocation(ArrayList<OsmObject> object) {
		OsmObject poi = object.get(0);
		String poiType = poi.getType();
		PoiTypes poiTypes = new PoiTypes();
		OsmObjectType type = poiTypes.getPoiType(poiType);
		int drawable = type.getDrawable(this.context);

		Intent resultIntent = new Intent(this.context, QuestionsActivity.class);
		resultIntent.putExtra("poilist", object);

		PendingIntent resultPendingIntent =	PendingIntent.getActivity(this.context,	0, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);
		int mNotificationId = 001;
		NotificationCompat.Builder mBuilder =
				new NotificationCompat.Builder(this.context)
						.setSmallIcon(R.drawable.ic_small_icon)
						.setLargeIcon(getBitmapFromVectorDrawable(context, drawable))
						.setContentTitle(String.format(context.getResources().getString(R.string.at_location), poi.getName()))
						.setContentText(context.getResources().getString(R.string.answer_questions))
						.addAction(R.drawable.ic_yes, context.getResources().getString(R.string.ok), resultPendingIntent)
						.setContentIntent(resultPendingIntent)
						.setAutoCancel(true);
		mBuilder.setDefaults(NotificationCompat.DEFAULT_ALL);
		NotificationManager mNotifyMgr = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
		mNotifyMgr.notify(mNotificationId, mBuilder.build());

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
					PoiTypes poiTypes = new PoiTypes();
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
								OsmObjectType poitype = poiTypes.getPoiType(type);
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
				Log.d(TAG, "" + poiList);

				// Arrange the locations by distance from location
				Collections.sort(poiList, new Comparator<OsmObject>() {
					public int compare(OsmObject p1, OsmObject p2) {
						return (int) (queryLocation.distanceTo(p1.getLocation()) - queryLocation.distanceTo(p2.getLocation()));
					}
				});

				for (int k = 0; k < poiList.size(); k++) {
					OsmObject o = poiList.get(k);
					Log.d(TAG, "" + poiList.get(k).getName() + ": " + (int) queryLocation.distanceTo(poiList.get(k).getLocation()) + "m");

				}
				if (poiList.size() > 0) {
					notifyLocation(poiList);
				}
			}
		}
	}


}
