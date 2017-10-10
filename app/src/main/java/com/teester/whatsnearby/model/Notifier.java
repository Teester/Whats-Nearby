package com.teester.whatsnearby.model;

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
import com.teester.whatsnearby.questions.QuestionsActivity;

public class Notifier {

	private Notifier() {

	}

	public static void createNotification(Context context, String name, int drawable) {

		Intent resultIntent = new Intent(context, QuestionsActivity.class);
		resultIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);

		PendingIntent resultPendingIntent = PendingIntent.getActivity(context, 0, resultIntent, PendingIntent.FLAG_CANCEL_CURRENT);
		int mNotificationId = 001;
		NotificationCompat.Builder mBuilder =
				new NotificationCompat.Builder(context)
						.setSmallIcon(R.drawable.ic_small_icon)
						.setLargeIcon(getBitmapFromVectorDrawable(context, drawable))
						.setContentTitle(String.format(context.getResources().getString(R.string.at_location), name))
						.setContentText(context.getResources().getString(R.string.answer_questions))
						.addAction(R.drawable.ic_yes, context.getResources().getString(R.string.ok), resultPendingIntent)
						.setContentIntent(resultPendingIntent)
						.setAutoCancel(true);
		mBuilder.setDefaults(NotificationCompat.DEFAULT_ALL);
		NotificationManager mNotifyMgr = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
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
}
