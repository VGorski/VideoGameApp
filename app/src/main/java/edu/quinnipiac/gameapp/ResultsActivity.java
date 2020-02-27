package edu.quinnipiac.gameapp;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

public class ResultsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);


        String results = (String) getIntent().getExtras().get("name");
        String results1 = (String) getIntent().getExtras().get("description");
        String results2 = (String) getIntent().getExtras().get("released");


        EditText nameView = (EditText) findViewById(R.id.gameName);
        EditText descriptView = (EditText) findViewById(R.id.gameDescription);
        EditText releaseView = (EditText) findViewById(R.id.gameRelease);

        nameView.setText(results);
        descriptView.setText(results1);
        releaseView.setText(results2);
    }
}