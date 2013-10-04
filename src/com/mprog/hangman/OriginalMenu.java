package com.mprog.hangman;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.mprog.hangman.R;

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
                Intent nextScreen = new Intent(getApplicationContext(), GameScreen.class);
                startActivity(nextScreen);
                break;
            case R.id.levelIntermediate:
                nextScreen = new Intent(getApplicationContext(), GameScreen.class);
                startActivity(nextScreen);
                break;
            case R.id.levelExpert:
                nextScreen = new Intent(getApplicationContext(), GameScreen.class);
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
