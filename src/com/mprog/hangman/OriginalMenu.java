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
                Settings settings = new Settings(1, config.ORIGINAL_NOVICE_LIVES, config.ORIGINAL_NOVICE_MINLENGTH, config.ORIGINAL_NOVICE_MAXLENGTH);
                Intent nextScreen = new Intent(getApplicationContext(), GameScreen.class);
                nextScreen.putExtra("Settings", settings);
                startActivity(nextScreen);
                break;
            case R.id.levelIntermediate:
                settings = new Settings(2, config.ORIGINAL_INTERMEDIATE_LIVES, config.ORIGINAL_INTERMEDIATE_MINLENGTH, config.ORIGINAL_INTERMEDIATE_MAXLENGTH);
                nextScreen = new Intent(getApplicationContext(), GameScreen.class);
                nextScreen.putExtra("Settings", settings);
                startActivity(nextScreen);
                break;
            case R.id.levelExpert:
                settings = new Settings(3, config.ORIGINAL_EXPERT_LIVES, config.ORIGINAL_EXPERT_MINLENGTH, config.ORIGINAL_EXPERT_MAXLENGTH);
                nextScreen = new Intent(getApplicationContext(), GameScreen.class);
                nextScreen.putExtra("Settings", settings);
                startActivity(nextScreen);
                break;
            case R.id.levelCustom:
                nextScreen = new Intent(getApplicationContext(), CustomSettings.class);
                startActivity(nextScreen);
                break;
            case R.id.backMenu:
                nextScreen = new Intent(getApplicationContext(), MainMenu.class);
                startActivity(nextScreen);
                break;
        }
    }
}
