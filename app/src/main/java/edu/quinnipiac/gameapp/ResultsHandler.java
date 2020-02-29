package edu.quinnipiac.gameapp;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;

public class ResultsHandler {
    //public static final String GAME_RESULTS = "Game_Results";
    //We can change this later.....
    //final public static String[] games = {"fallout","borderlands 2","grand theft auto v","payday 2","battlefield","life is strange episode"};

    public ResultsHandler(){


    }


    public String getGameName(String gameName) throws JSONException {
        JSONObject gameNameJSONObject = new JSONObject(gameName);
        JSONObject gameJSONObject = gameNameJSONObject.getJSONArray("results").getJSONObject(0);
        return gameJSONObject.getString("name");
    }


    public String getGameDescription(String gameDescription) throws JSONException {
        JSONObject gameDescriptionJSONObject = new JSONObject(gameDescription);
        JSONObject gameJSONObject = gameDescriptionJSONObject.getJSONArray("results").getJSONObject(0);
        return gameJSONObject.getString("description");
    }
    public String getGameRelease(String gameRelease) throws JSONException {
        JSONObject gameReleaseJSONObject = new JSONObject(gameRelease);
        JSONObject gameJSONObject = gameReleaseJSONObject.getJSONArray("results").getJSONObject(0);
        return gameJSONObject.getString("released");
    }


}