package com.mprog.hangman;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.*;
import android.view.inputmethod.InputMethodManager;
import com.mprog.hangman.database.DatabaseHelper;
import com.mprog.hangman.database.Settings;
//import com.mprog.hangman.dictionary.WordGenerator;

public class GameScreen extends Activity {
    HangmanDraw drawView;
    Hangman hangman;
    View view;
    static Settings settings;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        drawView = new HangmanDraw(this);
        drawView.setBackgroundColor(Color.WHITE);
        setContentView(drawView);

        /**
         * Check if there are new settings, if not use the old settings.
         */
        if (getIntent().getExtras() != null) {
            Intent i = getIntent();
            Settings settings = (Settings)i.getSerializableExtra("Settings");
            this.settings = settings;
        }

        hangman = new Hangman(drawView, this.settings);
        /*
         *  Show keyboard on create
         */
        InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
    }


    /*
     * Remove keyboard when user exits the gameScreen
     */
    @Override
    public void onPause() {
        super.onPause();
        InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
            imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
    }

    @Override
    /**
     * If the user types, check the letter.
     */
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        char letter = (char)event.getUnicodeChar();
        hangman.guessLetter(String.valueOf(letter));

        checkStatus();

        setContentView(drawView);
        return super.onKeyDown(keyCode, event);
    }

    @Override
    /**
     * If the user touches the screen, show a keyboard.
     */
    public boolean onTouchEvent(MotionEvent event) {
        ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
        return true;
    }

    @Override
    /**
     * Create the options menu
     */
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.layout.menu, menu);
        return true;
    }

    /**
     * Check the status of the game. If the game has finished, continue to the next screen.
     */
    private void checkStatus() {
        String text = null;

        if (hangman.getStatus() == 1) {
            text = "You won! Your score: " + hangman.getScore();
        } else if (hangman.getStatus() == 2) {
            text = "You lost! It was: " + hangman.wordInfo.getWord();
        }

        if (text != null) {
            DatabaseHelper db = new DatabaseHelper(Launcher.mainContext);
            db.addScore(Hangman.scoreboard);

            Intent nextScreen = new Intent(getApplicationContext(), EndMessage.class);
            nextScreen.putExtra("Text", text);
            startActivity(nextScreen);
            this.finish();
        }
    }

    /**
     * Event Handling for Individual menu item selected
     * Identify single menu item by it's id
     * */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.menu_home:
                Intent nextScreen = new Intent(getApplicationContext(), MainMenu.class);
                startActivity(nextScreen);
                this.finish();
                return true;

            case R.id.menu_about:
                hangman.guessLetter("a");
                setContentView(drawView);
                return true;

            case R.id.menu_preferences:
                //WordGenerator test = new WordGenerator();
                //test.main();
                //Log.e("Test", "Log is working...");
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

}