package com.mprog.hangman;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class EndMessage extends Activity {
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.endmessage);
        Intent i = getIntent();
        String text = i.getStringExtra("Text");
        TextView textView = (TextView) findViewById(R.id.endText);
        textView.setText(text);
    }

    public void onClick(View view) {
        //Starting a new Intent
        switch(view.getId()) {
            case R.id.again:
                Intent nextScreen = new Intent(getApplicationContext(), GameScreen.class);
                startActivity(nextScreen);
                this.finish();
                break;
            case R.id.menu:
                nextScreen = new Intent(getApplicationContext(), MainMenu.class);
                startActivity(nextScreen);
                this.finish();
                break;
            case R.id.score:
                nextScreen = new Intent(getApplicationContext(), ScoreboardView.class);
                startActivity(nextScreen);
                this.finish();
                break;
        }

    }

    @Override
    public void onBackPressed() {
        Intent nextScreen = new Intent(getApplicationContext(), MainMenu.class);
        startActivity(nextScreen);
        return;
    }
}
