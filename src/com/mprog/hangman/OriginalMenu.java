package com.mprog.hangman;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.mprog.hangman.R;
import com.mprog.hangman.database.Settings;

public class OriginalMenu extends Activity {
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.originalmenu);
    }

    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.levelNovice:
                Settings settings = new Settings(1, 13, 2, 14);
                Intent nextScreen = new Intent(getApplicationContext(), GameScreen.class);
                nextScreen.putExtra("Settings", settings);
                startActivity(nextScreen);
                break;
            case R.id.levelIntermediate:
                settings = new Settings(2, 8, 6, 14);
                nextScreen = new Intent(getApplicationContext(), GameScreen.class);
                nextScreen.putExtra("Settings", settings);
                startActivity(nextScreen);
                break;
            case R.id.levelExpert:
                settings = new Settings(3, 4, 8, 14);
                nextScreen = new Intent(getApplicationContext(), GameScreen.class);
                nextScreen.putExtra("Settings", settings);
                startActivity(nextScreen);
                break;
            case R.id.levelCustom:
                nextScreen = new Intent(getApplicationContext(), GameScreen.class);
                startActivity(nextScreen);
                break;
            case R.id.backMenu:
                nextScreen = new Intent(getApplicationContext(), MainMenu.class);
                startActivity(nextScreen);
                break;
        }
    }
}
