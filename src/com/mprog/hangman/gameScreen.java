package com.mprog.hangman;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.*;
import android.view.inputmethod.InputMethodManager;
//import com.mprog.hangman.dictionary.WordGenerator;

public class GameScreen extends Activity {
    HangmanDraw drawView;
    Hangman hangman;
    View view;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        drawView = new HangmanDraw(this);
        drawView.setBackgroundColor(Color.WHITE);
        setContentView(drawView);

        hangman = new Hangman(1, drawView);
        ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        Log.e("DEBUG", "Key Down  :" + (char)event.getUnicodeChar());
        char letter = (char)event.getUnicodeChar();
        hangman.guessLetter(String.valueOf(letter));

        checkStatus();

        setContentView(drawView);
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        Log.e("DEBUG", "touchevent");
        ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.layout.menu, menu);
        return true;
    }

    private void checkStatus() {
        if (hangman.getStatus() == 1) {
            Intent nextScreen = new Intent(getApplicationContext(), Won.class);
            startActivity(nextScreen);
        }
        else if (hangman.getStatus() == 2) {
            Intent nextScreen = new Intent(getApplicationContext(), Lost.class);
            startActivity(nextScreen);
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