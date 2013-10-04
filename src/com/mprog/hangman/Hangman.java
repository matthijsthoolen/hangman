package com.mprog.hangman;

import android.app.Activity;
import android.util.Log;
import com.mprog.hangman.database.Dictionary;
import com.mprog.hangman.database.Scoreboard;

import java.util.Arrays;

/**
 * Created with IntelliJ IDEA.
 * User: Matthijs
 * Date: 30-9-13
 * Time: 18:09
 * To change this template use File | Settings | File Templates.
 */
public class Hangman extends Activity {
    private static int tries;
    private static String word[];
    private static String guessed[];
    private static int status;
    HangmanDraw drawView;
    static Scoreboard scoreboard;

    //For the beta there is only one word...
    Dictionary wordInfo = new Dictionary("parteretrap", 1);

    public Hangman(int level, HangmanDraw drawView2) {
         //At the moment everything will be set with the same settings
        scoreboard = new Scoreboard();
        setLevel(level);
        setScore(0);
        setLives(13);
        setTries(0);
        setStatus(0);
        drawView = drawView2;
        word = new String[wordInfo.getLength()];
        guessed = new String[26];
    }

    /*
    * Check the guess and start new functions to handle the guess
    */
    public void guessLetter(String letterGuess) {
        letterGuess = letterGuess.toLowerCase();

        Log.e("DEBUG", "GUESS: " + letterGuess);

        //If not a letter, or already guessed before
        if (!letterGuess.matches("^[a-z]+$") || Arrays.asList(guessed).contains(letterGuess))
        {
            Log.e("DEBUG", "INVALID INPUT");
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
            increaseScore(100);
        }

        if (getLives() == 0) {
            setStatus(2);
        }

        checkWon();

        Log.e("DEBUG", "Lives: " + getLives());
        Log.e("DEBUG", "Tries: " + getTries());
    }

    public void guessWord(String wordGuess) {
        if (wordGuess.toLowerCase() == wordInfo.getWord())
        {
            Log.e("debug", "woohoe you won, congrats!");
            setStatus(1);
        }
        else
        {
            decreaseLives();
            increaseTries();
        }
    }

    /*
     * Check if the word is completed already, if so set status to 1
     */
    private void checkWon() {
        for (String letter : word) {
            if (letter == null) {
                return;
            }
        }

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
