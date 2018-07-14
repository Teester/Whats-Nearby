package com.teester.whatsnearby.data.location;

import android.location.Location;

import com.teester.whatsnearby.BuildConfig;
import com.teester.whatsnearby.data.PreferenceList;
import com.teester.whatsnearby.data.source.SourceContract;

import java.util.Locale;

public class QueryOrNot {
    private static final int MINQUERYINTERVAL = 60 * 60 * 1000;
    private static final double MINQUERYDISTANCE = 20;

    private SourceContract.Preferences preferences;
    private boolean query;
    private String notQueryReason;
    private Location location;

    public QueryOrNot(SourceContract.Preferences preferences, Location location) {
        this.preferences = preferences;
        this.location = location;
        query = true;
        notQueryReason = "";
        decideWhetherToQuery();
    }

    public boolean getWhetherToQuery() {
        return query;
    }

    /**
     * Logic dictating whether or not to query the Overpass api for a given location based on
     * location accuracy, time since last notification and distance since last query.  If the app is in
     * debug mode, it always returns true
     */
    private void decideWhetherToQuery() {

        boolean debug_mode = preferences.getBooleanPreference(PreferenceList.DEBUG_MODE);

        checkTimeSinceLastQuery();
        checkDistanceSinceLastLocation();
        checkDistanceSinceLastCheck();
        checkNumberOfDetections();

        // If we're in debug mode, query every time
        if (debug_mode && BuildConfig.DEBUG) {
            query = true;
        }

        if (query) {
            notQueryReason += "• Queried\n";
        }

        preferences.setStringPreference(PreferenceList.NOT_QUERY_REASON, notQueryReason);
    }

    /**
     * Don't query Overpass if less than 1 hour has passed since the last notificatio
     */
    private void checkTimeSinceLastQuery() {
        long lastNotificationTime = preferences.getLongPreference(PreferenceList.LAST_NOTIFICATION_TIME);
        if (System.currentTimeMillis() - lastNotificationTime < MINQUERYINTERVAL) {
            notQueryReason += String.format(Locale.getDefault(), "• Not long enough since last notification: %dmins\n", ((System.currentTimeMillis() - lastNotificationTime) / 60000));
            query = false;
        }
    }

    /**
     * Makes a location object from a latitude and longitude stored in Preferences
     *
     * @param latitudePreference  a preference containing a latitude
     * @param longitudePreference a preference containing a longitude
     * @return a location object
     */
    private Location getLocationFromPreferences(String latitudePreference, String longitudePreference) {
        double latitude = preferences.getDoublePreference(latitudePreference);
        double longitude = preferences.getDoublePreference(longitudePreference);
        Location location = new Location("dummyprovider");
        location.setLatitude(latitude);
        location.setLongitude(longitude);
        return location;
    }

    /**
     * Don't query Overpass is you've moved more than 20m from the last location query (5 mins ago)
     * (indicates you're probably not in the same place as 5 mins ago)
     */
    private void checkDistanceSinceLastLocation() {
        Location lastLocation = getLocationFromPreferences(PreferenceList.LAST_LOCATION_LATITUDE, PreferenceList.LAST_LOCATION_LONGITUDE);

        if (location.distanceTo(lastLocation) > MINQUERYDISTANCE) {
            notQueryReason += String.format(Locale.getDefault(), "• Too far from last location: %.0fm\n", location.distanceTo(lastLocation));
            query = false;
        }
    }

    /**
     * Don't query Overpass is youre still within 20m of the last location query that you were
     * notified about (indicates you've probably still in the same place)
     */
    private void checkDistanceSinceLastCheck() {
        Location lastQueryLocation = getLocationFromPreferences(PreferenceList.LAST_QUERY_LOCATION_LATITUDE, PreferenceList.LAST_QUERY_LOCATION_LONGITUDE);

        if (location.distanceTo(lastQueryLocation) < MINQUERYDISTANCE) {
            notQueryReason += String.format(Locale.getDefault(), "• Not far enough from location of last query: %.0fm\n", location.distanceTo(lastQueryLocation));
            query = false;
        }
    }

    /**
     * Query based on the number of location detections close to this location in a row.
     * Decide how many detections are needed based on accuracy
     */
    private void checkNumberOfDetections() {
        Location lastLocation = getLocationFromPreferences(PreferenceList.LAST_LOCATION_LATITUDE, PreferenceList.LAST_LOCATION_LONGITUDE);

        long detections = preferences.getLongPreference(PreferenceList.NUMBER_OF_VISITS);
        int numberOfDetectionsRequired = getNumberOfDetectionsRequired(location.getAccuracy());

        if (location.distanceTo(lastLocation) < 20) {
            preferences.setLongPreference(PreferenceList.NUMBER_OF_VISITS, detections + 1);
        } else {
            preferences.setLongPreference(PreferenceList.NUMBER_OF_VISITS, 1);
        }

        if (detections < numberOfDetectionsRequired) {
            query = false;
            notQueryReason += "• Not enough detections in a row";
        }
    }

    /**
     * Gets the numbers of repeat detections of the same location required to trigger a query
     *
     * @param accuracy the accuracy of the location fix
     * @return the number of repeat detections required
     */
    private int getNumberOfDetectionsRequired(float accuracy) {
        int numberRequired;
        if (accuracy > 1000) {
            numberRequired = 4;
        } else if (accuracy > 100) {
            numberRequired = 3;
        } else {
            numberRequired = 2;
        }
        return numberRequired;
    }
}
