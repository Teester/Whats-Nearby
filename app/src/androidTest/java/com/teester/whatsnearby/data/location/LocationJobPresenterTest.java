package com.teester.whatsnearby.data.location;

import android.location.Location;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.teester.whatsnearby.data.PreferenceList;
import com.teester.whatsnearby.data.source.Preferences;
import com.teester.whatsnearby.data.source.SourceContract;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class LocationJobPresenterTest {

    private LocationJobPresenter locationJobPresenter;

    @Mock
    private SourceContract.Preferences preferences;

    @Before
    public void setUp() {
        preferences = new Preferences(InstrumentationRegistry.getContext());
        locationJobPresenter = new LocationJobPresenter(InstrumentationRegistry.getContext(), new LocationJobService());
    }

    @Test
    public void processLocationTestWithGoodAccuracy() {
        Location location = new Location("dummyprovider");
        location.setLatitude(53);
        location.setLongitude(7);
        location.setAccuracy(10);

        locationJobPresenter.processLocation(location);

        double latitude = preferences.getDoublePreference(PreferenceList.LATITUDE);
        double longitude = preferences.getDoublePreference(PreferenceList.LONGITUDE);

        assertEquals(53, (int) latitude);
        assertEquals(7, (int) longitude);
    }

    @Test
    public void processLocationTestWithPoorAccuracy() {
        Location location = new Location("dummyprovider");
        location.setLatitude(54);
        location.setLongitude(8);
        location.setAccuracy(120);

        locationJobPresenter.processLocation(location);

        double latitude = preferences.getDoublePreference(PreferenceList.LATITUDE);
        double longitude = preferences.getDoublePreference(PreferenceList.LONGITUDE);

        assertEquals(54, (int) latitude);
        assertEquals(8, (int) longitude);
    }

    @Test
    public void processLocationTestWithTerribleAccuracy() {
        Location location = new Location("dummyprovider");
        location.setLatitude(55);
        location.setLongitude(9);
        location.setAccuracy(1200);

        locationJobPresenter.processLocation(location);

        double latitude = preferences.getDoublePreference(PreferenceList.LATITUDE);
        double longitude = preferences.getDoublePreference(PreferenceList.LONGITUDE);

        assertEquals(55, (int) latitude);
        assertEquals(9, (int) longitude);
    }
}