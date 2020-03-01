package edu.quinnipiac.gameapp;

/*
@authors: Victoria Gorski and Julia Wilkinson
@date: 2 - 29 - 20
@description: The ResultsHandler class is used to get the data from the API and return it to the ResultsActivity class.
 */

// Imports
import org.json.JSONException;
import org.json.JSONObject;

// Constructor
public class ResultsHandler {

    // Required constructor
    public ResultsHandler(){

    }

    // Get the name of the game
    public String getGameName(String gameName) throws JSONException {
        JSONObject gameNameJSONObject = new JSONObject(gameName);
        // Get the name from the array in the API and return it as a string
        JSONObject gameJSONObject = gameNameJSONObject.getJSONArray("results").getJSONObject(0);
        return gameJSONObject.getString("name");
    }

    // Get the release date of the game
    public String getGameRelease(String gameRelease) throws JSONException {
        JSONObject gameReleaseJSONObject = new JSONObject(gameRelease);
        // Get the release date from the array in the API and return it as a string
        JSONObject gameJSONObject = gameReleaseJSONObject.getJSONArray("results").getJSONObject(0);
        return gameJSONObject.getString("released");
    }
}