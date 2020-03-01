package edu.quinnipiac.gameapp;

/*
@authors: Victoria Gorski and Julia Wilkinson
@date: 2 - 29 - 20
@description: The SpinnerActivity class is used to control the spinner and the games in it. It connects to the API and then passes its data to the ResultsHandler
class in order to be converted into Strings the user can read.
 */

// Imports
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import androidx.appcompat.widget.ShareActionProvider;
import android.widget.Spinner;
import android.widget.Toast;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Collections;

// Constructor
public class SpinnerActivity extends AppCompatActivity {

    // Instance variables
    private final String LOG_TAG = SpinnerActivity.class.getSimpleName();
    public ResultsHandler resultsHandler = new ResultsHandler();
    boolean userSelect = false;
    public RelativeLayout layout;
    private String names = "https://rawg-video-games-database.p.rapidapi.com/games";
    private ShareActionProvider provider;

    // Used to create the options menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.mainmenu,menu);
        MenuItem shareItem = menu.findItem(R.id.action_share);
        // Allow the share button to access other apps
        provider = (ShareActionProvider) MenuItemCompat.getActionProvider(shareItem);
        layout = findViewById(R.id.spinnerActivity);
        return super.onCreateOptionsMenu(menu);
    }

    // Attach the options to some action
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        // Depending on which option is selected, match it to the corresponding action
        switch (id) {
            // Access settings
            case R.id.action_settings:
                // Hint for the user to change the background
                Toast.makeText(this, "You can change the background!", Toast.LENGTH_SHORT).show();
                return true;
                // Access share menu
            case R.id.action_share:
                // Allow the user to send a message using other apps
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT, "This is a message for you");
                provider.setShareIntent(intent);
                return true;
                // Access help menu
            case R.id.action_help:
                // Display a pop - up
                AlertDialog.Builder help = new AlertDialog.Builder(this);
                help.setTitle("How to Use");
                // Explain what the app does
                help.setMessage("This app is designed to retrieve information from a video game API and display it to the user. To use this app, select a game from the spinner in order to learn about its release date.");
                // If the user clicks outside the box, remove the pop - up
                help.setCancelable(true);
                help.show();
                return true;
                // Change background to background1.jpg
            case R.id.awesomeBackground:
                layout.setBackgroundResource(R.drawable.background1);
                return true;
                // Change background to background2.jpg
            case R.id.coolBackground:
                layout.setBackgroundResource(R.drawable.background2);
                return true;
                // Change background to background3.jpg
            case R.id.cuteBackground:
                layout.setBackgroundResource(R.drawable.background3);
                return true;
                // If no options are selected, return the menu
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    // Create the spinner and the Async process
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spinner);
        getIntent();

        // Connect the layout with the activity
        Spinner spinner = findViewById(R.id.spinner);

        // Create the spinner drop - down menu
        try {
            ArrayAdapter<String> resultsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, Collections.singletonList(resultsHandler.getGameName((String)spinner.getSelectedItem())));
            resultsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(resultsAdapter);
            // Throw this if the spinner doesn't work
        } catch (Exception e){
            Log.e("SpinnerActivity", e.getMessage());

        }

        // Get the game the user selects
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (userSelect) {
                    final String item = (String) parent.getItemAtPosition(position);
                    Log.i("onItemSelected : result", item);
                    // Start the Async task to get the results
                    new FetchResults().execute(item);
                    userSelect = false;
                }
            }

            // If nothing is selected, do not do anything
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    // If a user clicks something, make sure the item is selected
    @Override
    public void onUserInteraction(){
        super.onUserInteraction();
        userSelect = true;
    }

    // Async process that fetches the results while the app is running
    private class FetchResults extends AsyncTask<String,Void,String[]>{

        // In the background, grab the url and the results of the selected game
        @Override
        protected String[] doInBackground(String... params) {
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            String[] results;

            // Try to get the url that was declared in the instance variables
            try {
                URL url = new URL(names);

                // Get a connection to the API
                urlConnection = (HttpURLConnection) url.openConnection();
                // Ask to get some information
                urlConnection.setRequestMethod("GET");
                // Key to prove the user is authentic
                urlConnection.setRequestProperty("X-RapidAPI-Key","c90f923929msh8fa181332722ccbp1e3d7ejsn55c233fb5c0d");
                // Connect to the API
                urlConnection.connect();

                // If the results do not exist, do not return anything
                InputStream in = urlConnection.getInputStream();
                if (in == null) {
                    return null;
                }

                // Get the string from the buffer
                reader  = new BufferedReader(new InputStreamReader(in));
                results = getStringFromBuffer(reader);

                // If there's an error, report this message
            }catch(Exception e){
                Log.e(LOG_TAG,"Error" + e.getMessage());
                return null;
                // If the url does not exist, disconnect from the API
            }finally{
                if (urlConnection != null){
                    urlConnection.disconnect();
                }
                // If there's nothing in the reader, do not report anything
                if (reader != null){
                    try{
                        reader.close();
                    }catch (IOException e){
                        Log.e(LOG_TAG,"Error" + e.getMessage());
                        return null;
                    }
                }
            }
            return results;
        }

        // Execute this method to get the desired results
        protected void onPostExecute(String[] result){

            // If there are results, print them
            if (result != null){
                Intent intent = new Intent(SpinnerActivity.this,ResultsActivity.class);
                intent.putExtra("name",result[0]);
                intent.putExtra("released",result[2]);
                startActivity(intent);
            }
        }

        // Get the string from the buffer
        private String[] getStringFromBuffer(BufferedReader bufferedReader) throws Exception {
            StringBuffer buffer = new StringBuffer();
            String line;

            // While the buffer is not empty, keep printing out information
            while((line = bufferedReader.readLine()) != null){
                buffer.append(line + '\n');

            }

            if (bufferedReader !=null){
                try{
                    bufferedReader.close();
                    // If there's an issue with the buffer, throw an error
                }catch (IOException e){
                    Log.e("ResultsActivity","Error" + e.getMessage());
                    return null;
                }
            }

            // Get the data from the ResultsHandler
            String[] resultsData = {"","",""};
            resultsData[0] = resultsHandler.getGameName(buffer.toString());
            resultsData[2] = resultsHandler.getGameRelease(buffer.toString());
            return resultsData;
        }
    }
}
