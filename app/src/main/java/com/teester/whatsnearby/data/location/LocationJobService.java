package com.teester.whatsnearby.data.location;

import android.app.job.JobParameters;
import android.app.job.JobService;

public class LocationJobService extends JobService implements Runnable, LocationJobServiceContract.LocationJobService {

	private JobParameters jobParameters;
	private LocationJobServiceContract.LocationJobService locationJobServiceCallback;

	/*
	 *  When the job is started, run it on a background thread
	 */
	@Override
	public boolean onStartJob(final JobParameters jobParameters) {
		this.jobParameters = jobParameters;
		locationJobServiceCallback = this;

		new Thread(this).start();

		return false;
	}

	/*
	 *  When the job is stopped, we don't need to do anything
	 */
	@Override
	public boolean onStopJob(JobParameters jobParameters) {
		return false;
	}

	/*
	 *  When we get a response from the backgrund thred, indicate the job is finished & needs rescheduling
	 */
	@Override
	public void locationCallback() {
		jobFinished(jobParameters, true);
	}

	/*
	 *  Get the location and process it
	 */
	@Override
	public void run() {
		LocationJobPresenter locationJobPresenter = new LocationJobPresenter(getApplicationContext(), locationJobServiceCallback);
		locationJobPresenter.getLocation();
	}
}
