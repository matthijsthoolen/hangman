package com.mprog.hangman;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class ScoreboardView extends Activity {
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scoreboard);
    }

    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.levelNovice:
                break;
            case R.id.levelIntermediate:
                break;
            case R.id.levelExpert:
                break;
            case R.id.levelCustom:
                break;
            case R.id.backMenu:
                Intent nextScreen = new Intent(getApplicationContext(), MainMenu.class);
                startActivity(nextScreen);
                break;
        }
    }
}
