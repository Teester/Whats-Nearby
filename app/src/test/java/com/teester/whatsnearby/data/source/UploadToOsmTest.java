package com.teester.whatsnearby.data.source;

import com.teester.whatsnearby.data.Answers;
import com.teester.whatsnearby.data.OsmObject;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class UploadToOsmTest {

	@Mock
	public SourceContract.Preferences preferences;

	@Before
	public void setUp() {
		OsmObject osmObject = new OsmObject(1, "", "", "", 1, 1, 1);
		Answers.setPoiDetails(osmObject);
	}

	@After
	public void tearDown() {
		Answers.clearAnswerList();
	}

	@Test
	public void testUploadToOsm() {
		UploadToOSM uploadToOSM = new UploadToOSM(preferences);
		uploadToOSM.uploadToOsm();

	}

}
