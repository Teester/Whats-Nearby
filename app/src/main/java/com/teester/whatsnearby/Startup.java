package com.teester.whatsnearby;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.teester.whatsnearby.model.data.location.LocationService;

public class Startup extends BroadcastReceiver {

	public Startup() {
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		context.startService(new Intent(context, LocationService.class));
	}
}

