package com.mprog.hangman;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.mprog.hangman.R;

public class Won extends Activity {
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.won);
    }

    public void onClick(View view) {
        //Starting a new Intent
        switch(view.getId()) {
            case R.id.again:
                Intent nextScreen = new Intent(getApplicationContext(), GameScreen.class);
                startActivity(nextScreen);
                break;
            case R.id.menu:
                nextScreen = new Intent(getApplicationContext(), MainMenu.class);
                startActivity(nextScreen);
                break;
        }

    }
}
