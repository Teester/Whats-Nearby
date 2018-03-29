package com.teester.whatsnearby;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

import com.teester.whatsnearby.data.location.LocationJobService;

public class Startup extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		if (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)) {

			JobScheduler jobScheduler = (JobScheduler) context.getSystemService(Context.JOB_SCHEDULER_SERVICE);
			ComponentName jobService = new ComponentName(context.getPackageName(), LocationJobService.class.getName());
			JobInfo jobInfo = new JobInfo.Builder(1, jobService)
					.setPeriodic(60000)
					.build();
			assert jobScheduler != null;
			jobScheduler.schedule(jobInfo);
		}
	}
}

