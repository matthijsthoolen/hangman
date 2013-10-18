package com.mprog.hangman;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import com.mprog.hangman.database.DatabaseHelper;

import java.io.InputStream;

public class ArcadeMenu extends Activity {
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.arcademenu);
    }

    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.levelNovice:
                Log.d("Hangman", "Y u no parse?");
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
