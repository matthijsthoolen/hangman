package com.mprog.hangman;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.mprog.hangman.database.DatabaseHelper;
import com.mprog.hangman.database.Preferences;

/**
 * Created with IntelliJ IDEA.
 * User: Matthijs
 * Date: 13-10-13
 * Time: 17:29
 * To change this template use File | Settings | File Templates.
 */
public class Launcher extends Activity implements launcherInterface {

    ImageButton dutch;
    ImageButton english;
    TextView text;
    ProgressBar progressBar;
    WordGenerator word;
    private boolean menuStatus = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DatabaseHelper db = new DatabaseHelper(this.getApplicationContext());
        db.getReadableDatabase();

        setContentView(R.layout.launcher);

        long count = db.getRecordCount(db.TABLE_DICTIONARY);

        db.close();

        /**
         * Check if there are records in the database already, if not ask the user for the preferred language and then
         * add them. If there are records already, check if they are all added, if not continue where we left off.
         */
        if (count == 0) {
            askPreferredLanguage();
        } else {
            checkNrWords((int)count, db.getPreferences());
        }
    }

    /**
     * Ask the user for the preferred language for the words in the database.
     */
    public void askPreferredLanguage() {
        english = (ImageButton) findViewById(R.id.buttonEnglish);
        dutch = (ImageButton) findViewById(R.id.buttonDutch);
        text = (TextView) findViewById(R.id.loadingText);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);
        dutch.setVisibility(0);
        english.setVisibility(0);
        text.setText("Choose dictionary language: ");


        dutch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setPreferences("dutchwords", 160049);
                dutch.setVisibility(View.GONE);
                english.setVisibility(View.GONE);
                text.setText("Installing Hangman. Please wait");
                progressBar.setVisibility(0);
                fillDictionary("dutchwords", 0);
            }

        });

        english.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setPreferences("englishwords", 234369);
                english.setVisibility(View.GONE);
                dutch.setVisibility(View.GONE);
                text.setText("Installing Hangman. Please wait");
                progressBar.setVisibility(0);
                fillDictionary("englishwords", 0);
            }

        });
    }

    /**
     * Check the number of words already added to the database, if not all words are added already, continue adding.
     * @param count
     * @param pref
     */
    private void checkNrWords(int count, Preferences pref) {
        if (count < pref.getNrWords()) {
            fillDictionary(pref.getLanguage(), (count + 1));
        }
        else
        {
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                public void run() {
                    continueToMenu();
                }
            }, 2000);
        }
    }

    /**
     * Update the text on the launcher
     * @param newText
     */
    public void updateText(String newText) {
        TextView textView = (TextView) findViewById(R.id.loadingText);
        textView.setText(newText);
    }

    /**
     * Set the users preferences for the language
     * @param file
     * @param nrWords
     */
    private void setPreferences(String file, int nrWords) {
        DatabaseHelper db = new DatabaseHelper(this);
        db.setPreferences(file, nrWords);
        db.close();
    }

    @Override
    public void updateLaunchScreen(int value) {
    }

    /**
     * Fill the dictionary with the words from the chosen language file.
     * @param file
     * @param start
     */
    private void fillDictionary(String file, int start) {
        /*
         * Trying to make a 'bridge' so that the async class can pass information during execution back to
         * this class. Can you please comment on this, is this the only/best way to do this, or how can I do this?
         */
        launcherInterface bridge = new launcherInterface() {
            @Override
            public void updateLaunchScreen(int value) {
                updateText("Installing: " + value + "/" + config.INSTALL_BEFORE_START);
                if (value == config.INSTALL_BEFORE_START) {
                    continueToMenu();
                }
            }
        };

        word = new WordGenerator(this, bridge, file, start);
        word.execute("");

        /**
         * If there are already enough records in the database, continue after showing the loader screen for 2 seconds
         * (I just want to show the logo :))
         */
        if (start >= config.INSTALL_BEFORE_START) {
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                public void run() {
                    continueToMenu();
                }
            }, 2500);
        }

    }

    private void continueToMenu() {
        if (!menuStatus) {
            menuStatus = true;
            Intent nextScreen = new Intent(getApplicationContext(), MainMenu.class);
            startActivity(nextScreen);
            //Disable cancelAsync() to continue parsing the XML file while playing
            cancelAsync();
            this.finish();
        }
    }

    /**
     * Fix for emulator
     */
    public void cancelAsync() {
        word.cancel(true);
    }


}
