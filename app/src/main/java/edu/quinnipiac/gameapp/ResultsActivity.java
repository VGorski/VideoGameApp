package edu.quinnipiac.gameapp;

/*
@authors: Victoria Gorski and Julia Wilkinson
@date: 2 - 29 - 20
@description: The ResultsActivity class is used to display the name and release data of the selected game.
 */

// Imports

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

// Constructor
public class ResultsActivity extends AppCompatActivity {

    // Creates the activity
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        // Get the name and release date of the game
        String results = (String) getIntent().getExtras().get("name");
        String results2 = (String) getIntent().getExtras().get("released");

        // Find the respective textViews
        EditText nameView = findViewById(R.id.gameName);
        EditText releaseView = findViewById(R.id.gameRelease);

        // Set the text in the textViews
        nameView.setText(results);
        releaseView.setText(results2);
    }
}