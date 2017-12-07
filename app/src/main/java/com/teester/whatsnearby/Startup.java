package com.teester.whatsnearby;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.teester.whatsnearby.data.location.LocationService;

public class Startup extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		if (intent.getAction() == Intent.ACTION_BOOT_COMPLETED) {
			context.startService(new Intent(context, LocationService.class));
		}
	}
}

