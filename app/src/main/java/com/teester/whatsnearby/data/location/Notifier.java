package com.teester.whatsnearby.data.location;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;

import com.teester.whatsnearby.R;
import com.teester.whatsnearby.data.source.Preferences;
import com.teester.whatsnearby.data.source.SourceContract;
import com.teester.whatsnearby.questions.QuestionsActivity;

import java.util.Date;

import static android.content.Context.NOTIFICATION_SERVICE;

public class Notifier {

	private static final String OVERPASSLASTQUERYTIMEPREF = "last_overpass_query_time";

	/**
	 * Creates a notification and stores the time of notification
	 *
	 * @param context  - Application context
	 * @param name     - poi name
	 * @param drawable - poi drawable id
	 */
	public static void createNotification(Context context, String name, int drawable) {

		// Store the time the notification was made
		SourceContract.Preferences preferences = new Preferences(context);
		preferences.setLongPreference(OVERPASSLASTQUERYTIMEPREF, System.currentTimeMillis());
		System.out.println(new Date(System.currentTimeMillis()).toString());
		preferences.setStringPreference("last_notification_time", new Date(System.currentTimeMillis()).toString());
		Intent resultIntent = new Intent(context, QuestionsActivity.class);
		resultIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);

		PendingIntent resultPendingIntent = PendingIntent.getActivity(context, 0, resultIntent, PendingIntent.FLAG_CANCEL_CURRENT);
		int mNotificationId = 001;
		NotificationCompat.Builder mBuilder =
				new NotificationCompat.Builder(context, "whats_nearby_1")
						.setSmallIcon(R.drawable.ic_small_icon)
						.setLargeIcon(getBitmapFromVectorDrawable(context, drawable))
						.setContentTitle(String.format(context.getResources().getString(R.string.at_location), name))
						.setContentText(context.getResources().getString(R.string.answer_questions))
						.addAction(R.drawable.ic_yes, context.getResources().getString(R.string.ok), resultPendingIntent)
						.setContentIntent(resultPendingIntent)
						.setAutoCancel(true);
		mBuilder.setDefaults(NotificationCompat.DEFAULT_ALL);
		NotificationManager mNotifyMgr = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
		mNotifyMgr.notify(mNotificationId, mBuilder.build());
	}

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

	/**
	 * Cancels any notifications from the app
	 *
	 * @param context
	 */
	public static void cancelNotifictions(Context context) {

		NotificationManager notificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
		notificationManager.cancelAll();
	}
}
