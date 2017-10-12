package com.teester.whatsnearby.data.location;

import android.Manifest;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;

import com.mapzen.android.lost.api.LocationListener;
import com.mapzen.android.lost.api.LocationRequest;
import com.mapzen.android.lost.api.LocationServices;
import com.mapzen.android.lost.api.LostApiClient;
import com.teester.whatsnearby.R;
import com.teester.whatsnearby.data.source.Preferences;
import com.teester.whatsnearby.data.source.QueryOverpass;
import com.teester.whatsnearby.data.source.SourceContract;
import com.teester.whatsnearby.main.MainActivity;
import com.teester.whatsnearby.questions.QuestionsActivity;

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
		return START_STICKY;
	}

	@Override public void onDestroy() {
		super.onDestroy();
		LocationServices.FusedLocationApi.removeLocationUpdates(client, listener);
		client.disconnect();
	}

	@Override
	public void setPresenter(LocationServiceContract.Presenter presenter) {
		locationPresenter = presenter;
	}

	@Override
	public void cancelNotifications() {
		NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		notificationManager.cancelAll();
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

		new Thread(new Runnable() {
			@Override
			public void run() {
				new QueryOverpass(location, getApplicationContext());
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

		// Store the time the notification was made
		preferences.setLongPreference(OVERPASSLASTQUERYTIMEPREF, System.currentTimeMillis());

		Intent resultIntent = new Intent(getApplicationContext(), QuestionsActivity.class);
		resultIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);

		PendingIntent resultPendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, resultIntent, PendingIntent.FLAG_CANCEL_CURRENT);
		int mNotificationId = 001;
		NotificationCompat.Builder mBuilder =
				new NotificationCompat.Builder(getApplicationContext())
						.setSmallIcon(R.drawable.ic_small_icon)
						.setLargeIcon(getBitmapFromVectorDrawable(getApplicationContext(), drawable))
						.setContentTitle(String.format(getApplicationContext().getResources().getString(R.string.at_location), name))
						.setContentText(getApplicationContext().getResources().getString(R.string.answer_questions))
						.addAction(R.drawable.ic_yes, getApplicationContext().getResources().getString(R.string.ok), resultPendingIntent)
						.setContentIntent(resultPendingIntent)
						.setAutoCancel(true);
		mBuilder.setDefaults(NotificationCompat.DEFAULT_ALL);
		NotificationManager mNotifyMgr = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
		mNotifyMgr.notify(mNotificationId, mBuilder.build());
	}
}
