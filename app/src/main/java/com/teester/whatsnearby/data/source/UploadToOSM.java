package com.teester.whatsnearby.data.source;

import com.teester.whatsnearby.data.Answers;
import com.teester.whatsnearby.data.PreferenceList;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import de.westnordost.osmapi.OsmConnection;
import de.westnordost.osmapi.common.errors.OsmAuthorizationException;
import de.westnordost.osmapi.map.MapDataDao;
import de.westnordost.osmapi.map.data.Element;
import de.westnordost.osmapi.user.UserDao;
import oauth.signpost.OAuthConsumer;
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;

/**
 * Handles uploading answers to OpenStreetMap
 */

public class UploadToOSM implements SourceContract.upload {

	private static final String CONSUMER_KEY = "1LJqwD4kMz96HTbv9I1U1XBM0AL1RpcjuFOPvW0B";
	private static final String CONSUMER_SECRET = "KDCLveu82AZawLELpC6yIP3EI8fJa0JqF0ALukbl";

    private final SourceContract.Preferences preferences;
    private Element element;

    /**
     * Constructer for uploading object
     *
     * @param preferences a preferences object
     */
    public UploadToOSM(SourceContract.Preferences preferences) {
        this.preferences = preferences;
    }

    /**
     * Uploads the answers to OSM
     */
    @Override
    public void uploadToOsm() {
        // Download the relevant object from OSM
        getCurrentElement();

        // Add/modify the relevant keys
        modifyCurrentElement();

        // Update the altered object
        List<Element> collection = Collections.singletonList(element);
        if (element.isModified()) {
            try {
                Map<String, String> changeSetTags = Answers.getChangesetTags();
                new MapDataDao(getConnection()).updateMap(changeSetTags, collection, null);
            } catch (OsmAuthorizationException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Gets the OSM connection details to successfully access OSM data
     * @return the connection object
     */
    private OsmConnection getConnection() {
        String oauth_token_secret = preferences.getStringPreference(PreferenceList.OAUTH_TOKEN_SECRET);
        String oauth_token = preferences.getStringPreference(PreferenceList.OAUTH_TOKEN);

        OAuthConsumer consumer = new CommonsHttpOAuthConsumer(CONSUMER_KEY, CONSUMER_SECRET);
        consumer.setTokenWithSecret(oauth_token, oauth_token_secret);

        return new OsmConnection(
                "https://api.openstreetmap.org/api/0.6/",
                "What's Nearby?", consumer);
    }

    /**
     * Downloads the specified element from OSM
     */
    private void getCurrentElement() {
        OsmConnection osm = getConnection();
        String type = Answers.getPoiType();
        long id = Answers.getPoiId();

        // Download the relevant object from OSM
        switch (type) {
            case "node":
                element = new MapDataDao(osm).getNode(id);
                break;
            case "way":
                element = new MapDataDao(osm).getWay(id);
                break;
            case "relation":
                element = new MapDataDao(osm).getRelation(id);
                break;
            default:
                break;
        }
    }

    /**
     * Modifies the downloaded element with the new tags based on the questions
     * answered
     */
    private void modifyCurrentElement() {
        for (Map.Entry<String, String> pair : Answers.getAnswerMap().entrySet()) {
            String key = pair.getKey();
            String value = pair.getValue();
            if (!"".equals(value)) {
                if (!element.getTags().get(key).equals(value)) {
                    element.getTags().put(key, value);
                }
            }
        }
    }

    /**
     * Retrieves the user's OSM username from OSM and stores it in a preference
     */
    @Override
    public void setUsername() {
        UserDao userDao = new UserDao(getConnection());
        String name = userDao.getMine().displayName;
        preferences.setStringPreference(PreferenceList.OSM_USER_NAME, name);
    }
}
