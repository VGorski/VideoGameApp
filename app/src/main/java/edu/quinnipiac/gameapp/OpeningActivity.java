package edu.quinnipiac.gameapp;

/*
@authors: Victoria Gorski and Julia Wilkinson
@date: 2 - 29 - 20
@description: The OpeningActivity class is used as an opening screen for the app and moves onto the SpinnerActivity class when the button is clicked.
 */

// Imports
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

// Constructor
public class OpeningActivity extends AppCompatActivity {


    // Creates the activity
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opening);

    }

    // On a click of the button, move from the opening screen to the spinner activity screen
    public void onBegin(View view){
        Intent intent = new Intent(OpeningActivity.this, SpinnerActivity.class);
        startActivity(intent);
    }
}
