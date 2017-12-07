# Whats Nearby?
An android app for collecting information about POIs for OpenStreetMap

**This app should be considered alpha level at best and should be used for testing only at this time.**

*What's Nearby?* asks simple "yes" or "no" questions about a location it detects you are at.  These answers are then uploaded to OpenStreetMap.  This app is aimed at those who aren't aware of OSM tagging concepts and casual mappers. Tags should not be exposed in the interface, but rather inferred from the answers to questions like "Does this location have Wifi for customers?". The idea is for the mapping to be almost frictionless, where people are mapping by being prompted that data is missing rather than consciously deciding to add data.

## What's the process?
*What's Nearby?* checks your location every 5 minutes.  If it decides that you are at a new place, then it performs an Overpass query to find all the relevant locations close by.  It then selects the closest of these and presents you a suggestion that you might be at that location via an android notification.  If you say that you are (by selecting 'ok') it presents a series of yes or no questions.  Answering each moves you on to the next and at the end the results are uploaded to OpenStreetmap.

There are 5 criteria which must be met before an Overpass query is done:
1. The location accuracy must be less than 100m (to ensure accurate results)
2. The location must not have moved by more than 20m since the last location determination (to ensure that you're actually at a location and not just passing it)
3. The location must have moved by more than 20m since the last Overpass query was done (to ensure that you're not being notified about the same location over and over)
4. It must be longer than 60 minutes since the last Overpass query (to prevent notification overload)
5. The location must not have been notified in the last week

(Note that for testing purposes, the time between checks has been decreased to 1min for now)

## What's done
- Basic location determination and notification logic
- Overpass querying
- A whitelist of POI types to ask questions and questions to ask about each
- OSM Oauth authentication
- Uploading answers to OSM
- A way to select a different location if *What's Nearby?* has got it wrong
- A database to store answers locally (which will help with repeat location visits)

## What's next
- A way to review answers before upload
- UI indication of current OSM tags for the question
- Ask questions based on previous answers
- Add locations to the map
- Add notes to the map

## Whats the licence?
This software is released under the terms of the [GNU General Public License](http://www.gnu.org/licenses/gpl-3.0.html).