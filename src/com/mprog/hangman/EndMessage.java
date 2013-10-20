package com.mprog.hangman;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
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
        String result = i.getStringExtra("Result");
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.endMessageLayout);

        /**
         * Set a different background for winning and losing
         */
        if (result.equals("won")) {
            linearLayout.setBackgroundResource(R.drawable.backrepeat_won);
        } else {
            linearLayout.setBackgroundResource(R.drawable.backrepeat_lost);
            findViewById(R.id.score).setVisibility(View.GONE);
        }
        TextView textView = (TextView) findViewById(R.id.endText);
        textView.setText(text);
    }

    public void onClick(View view) {
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
