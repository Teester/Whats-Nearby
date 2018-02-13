package com.teester.whatsnearby.data.location;

import android.Manifest;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import com.mapzen.android.lost.api.LocationListener;
import com.mapzen.android.lost.api.LocationRequest;
import com.mapzen.android.lost.api.LocationServices;
import com.mapzen.android.lost.api.LostApiClient;
import com.teester.whatsnearby.data.source.Preferences;
import com.teester.whatsnearby.data.source.QueryOverpass;
import com.teester.whatsnearby.data.source.SourceContract;
import com.teester.whatsnearby.main.MainActivity;

public class LocationService extends Service implements LocationServiceContract.Service {

	private static final String TAG = LocationService.class.getSimpleName();

	private static final int PRIORITY = LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY;
	private static final String OVERPASSLASTQUERYTIMEPREF = "last_overpass_query_time";

	private LocationServiceContract.Presenter locationPresenter;
	LocationListener listener = new LocationListener() {
		@Override
		public void onLocationChanged(Location location) {
			locationPresenter.processLocation(location);
		}
	};
	private SourceContract.Preferences preferences;
	private LostApiClient client;
	private Context context;

	/**
	 * Gets a bitmap of a drawable from a given drawable id
	 *
	 * @param context    application context
	 * @param drawableId the id of the required drawable
	 * @return A bitmap image
	 */
	private static Bitmap getBitmapFromVectorDrawable(Context context, int drawableId) {
		Drawable drawable = ContextCompat.getDrawable(context, drawableId);
		Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(),
				drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(bitmap);
		drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
		drawable.draw(canvas);

		return bitmap;
	}

	@Nullable
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		context = getApplicationContext();
		preferences = new Preferences(context);
		locationPresenter = new LocationPresenter(this, preferences);
		locationPresenter.init();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		super.onStartCommand(intent, flags, startId);
		return START_STICKY;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		if (client.isConnected()) {
			LocationServices.FusedLocationApi.removeLocationUpdates(client, listener);
			client.disconnect();
		}
	}

	@Override
	public void setPresenter(LocationServiceContract.Presenter presenter) {
		locationPresenter = presenter;
	}

	@Override
	public void cancelNotifications() {
		Notifier.cancelNotifictions(getApplicationContext());
	}

	@Override
	public void createLostClient(final int interval) {
		client = new LostApiClient.Builder(this).addConnectionCallbacks(new LostApiClient.ConnectionCallbacks() {
			@Override
			public void onConnected() {
				LocationRequest request = LocationRequest.create();
				request.setPriority(PRIORITY);
				request.setInterval(interval);

				checkLocationPermission();

				LocationServices.FusedLocationApi.requestLocationUpdates(client, request, listener);
			}

			@Override
			public void onConnectionSuspended() {

			}
		}).build();
		client.connect();
	}

	@Override
	public void performOverpassQuery(final Location location) {
		SourceContract.Overpass overpassQuery = new QueryOverpass(getApplicationContext());
		new Thread(new Runnable() {
			@Override
			public void run() {
				SourceContract.Overpass overpassQuery = new QueryOverpass(getApplicationContext());
				overpassQuery.queryOverpass(location.getLatitude(), location.getLongitude(), location.getAccuracy());
			}
		}).start();
	}

	public void checkLocationPermission() {
		if (ActivityCompat.checkSelfPermission(getApplicationContext(),
				Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

			Intent intent = new Intent(getBaseContext(), MainActivity.class);
			startActivity(intent);

			return;
		}
	}

	@Override
	public void createNotification(String name, int drawable) {
		Notifier.createNotification(getApplicationContext(), name, drawable);
	}
}
