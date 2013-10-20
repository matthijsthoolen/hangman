package com.mprog.hangman;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import com.mprog.hangman.database.DatabaseHelper;

public class MainMenu extends Activity {
    /**
     * Called when the activity is first created.
     */

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }

    public void onClick(View view) {
        //Starting a new Intent
        switch(view.getId()) {
            case R.id.originalMenu:
                Intent nextScreen = new Intent(getApplicationContext(), OriginalMenu.class);
                startActivity(nextScreen);
                break;
//            case R.id.arcadeMenu:
//                nextScreen = new Intent(getApplicationContext(), ArcadeMenu.class);
//                startActivity(nextScreen);
//                break;
            case R.id.scoreboard:
                nextScreen = new Intent(getApplicationContext(), ScoreboardView.class);
                startActivity(nextScreen);
                break;
        }

    }
}
