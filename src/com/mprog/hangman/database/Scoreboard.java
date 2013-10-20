package com.mprog.hangman.database;

/**
 * Created with IntelliJ IDEA.
 * User: Matthijs
 * Date: 4-10-13
 * Time: 10:35
 * To change this template use File | Settings | File Templates.
 */
public class Scoreboard {
    int _id;
    String _username;
    int _score;
    int _level;
    int _lives;
    String _word;
    int _date;

    public Scoreboard() {

    }

    public Scoreboard(String username, int score, int level, int lives, String word, int date) {
        this._username = username;
        this._score = score;
        this._level = level;
        this._lives = lives;
        this._word = word;
        this._date = date;
    }

    public Scoreboard(int id, String username, int score, int level, int lives, String word, int date) {
        this._id = id;
        this._username = username;
        this._score = score;
        this._level = level;
        this._lives = lives;
        this._word = word;
        this._date = date;
    }

    public int getID() {
        return this._id;
    }

    public void setID(int id) {
        this._id = id;
    }

    public String getUsername() {
        return this._username;
    }

    public void setUsername(String username) {
        this._username = username;
    }

    public int getScore() {
        return this._score;
    }

    public void setScore(int score) {
        this._score = score;
    }

    public int getLevel() {
        return this._level;
    }

    public void setLevel(int level) {
        this._level = level;
    }

    public int getLives() {
        return this._lives;
    }

    public void setLives(int lives) {
        this._lives = lives;
    }

    public String getWord() {
        return this._word;
    }

    public void setWord(String word) {
        this._word = word;
    }

    public int getDate() {
        return this._date;
    }

    public void setDate(int date) {
        this._date = date;
    }

}
