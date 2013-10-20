package com.mprog.hangman;

import android.util.Log;
import com.mprog.hangman.database.DatabaseHelper;
import com.mprog.hangman.database.Word;
import com.mprog.hangman.database.Settings;
import com.mprog.hangman.database.Scoreboard;

import java.util.Arrays;

/**
 * Created with IntelliJ IDEA.
 * User: Matthijs
 * Date: 30-9-13
 * Time: 18:09
 * To change this template use File | Settings | File Templates.
 */
public class Hangman {
    private static int tries;
    private static String word[];
    private static String guessed[];
    private static int status;

    HangmanDraw drawView;
    Settings settings;
    static Scoreboard scoreboard;
    Word wordInfo;

    public Hangman(HangmanDraw drawView2, Settings settings) {
         //At the moment everything will be set with the same Settings
        this.settings = settings;
        scoreboard = new Scoreboard();
        setLevel(settings.get_level());
        setScore(0);
        setLives(settings.get_tries());
        setTries(0);
        setStatus(0);
        setupHangman(settings);
        drawView = drawView2;
    }

    private void setupHangman(Settings settings) {
        DatabaseHelper db = new DatabaseHelper(Launcher.mainContext);
        if (checkDBQueue()) {
            wordInfo = db.getRandomWord(settings);
            Log.d("Hangman debug", "Choosen word: " + wordInfo.getWord());
            clearDBQueue();
            word = new String[wordInfo.getLength()];
            guessed = new String[26];
            scoreboard.setWord(wordInfo.getWord());
        }
    }



    /**
     * Check if the DB is already used by a async task. If so, wait until its free again
     * ALWAYS call clearDBqueue afterwards.
     * @return
     */
    private boolean checkDBQueue() {

        DatabaseHelper.gameQueue = 1;

        while (DatabaseHelper.asyncQueue == 1);

        return true;
    }

    private void clearDBQueue() {
        DatabaseHelper.gameQueue = 0;
    }

    /*
    * Check the guess and start new functions to handle the guess
    */
    public void guessLetter(String letterGuess) {
        letterGuess = letterGuess.toLowerCase();

        //If not a letter, or already guessed before
        if (!letterGuess.matches("^[a-z]+$") || Arrays.asList(guessed).contains(letterGuess))
        {
            return;
        }
        guessed[Hangman.tries] = letterGuess;

        //set the letterGuess in a char
        char guess = letterGuess.charAt(0);
        int i = 0, j = 0;

        for(char letter : wordInfo.getWordArray())
        {
            if (guess == letter) {
                word[j] = letterGuess;
                i++;
            }
            j++;
        }

        if (i == 0) {
            decreaseLives();
            increaseTries();
        } else {
            increaseTries();
            increaseScore(config.ORIGINAL_POINTS_GOODGUESS);
        }

        if (getLives() == 0) {
            setStatus(2);
        }

        checkWon();
    }

    /*
     * Check if the word is completed already, if so set status to 1 and calculate new score
     */
    private void checkWon() {
        for (String letter : word) {
            if (letter == null) {
                return;
            }
        }

        //Calculate endscore. Foreach lives left, you get 2500/start lives + a bonus for each letter.
        increaseScore(getLives() * (config.ORIGINAL_POINTS_LIVESOVER/settings.get_tries()) + wordInfo.getLength() * config.ORIGINAL_POINTS_LETTERBONUS);
        scoreboard.setLives(getTries());

        setStatus(1);
    }

    public static void setLevel(int newLevel) {
        scoreboard.setLevel(newLevel);
    }

    public static int getLevel() {
        return scoreboard.getLevel();
    }

    public static void increaseScore(int addPoints) {
        scoreboard.setScore(scoreboard.getScore() + addPoints);
    }

    public static void setScore(int newScore) {
        scoreboard.setScore(newScore);
    }

    public static int getScore() {
        return scoreboard.getScore();
    }

    public static void decreaseLives() {
        scoreboard.setLives(scoreboard.getLives() - 1);
    }

    public static void setLives(int newLives) {
        scoreboard.setLives(newLives);
    }

    public static int getLives() {
        return scoreboard.getLives();
    }

    public static void setTries(int newTries) {
        Hangman.tries = newTries;
    }

    public static void increaseTries() {
        Hangman.tries += 1;
    }

    public static int getTries() {
        return Hangman.tries;
    }

    public static String[] getWord() {
        return Hangman.word;
    }

    public static String[] getGuessed() {
        return Hangman.guessed;
    }

    public static void setStatus(int status) {
        Hangman.status = status;
    }

    public static int getStatus() {
        return Hangman.status;
    }
}
