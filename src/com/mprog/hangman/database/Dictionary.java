package com.mprog.hangman.database;

import android.util.Log;

public class Dictionary {
    int _id;
    String _word;
    int _length;
    int _level;
    char[] _wordArray;

    public Dictionary() {
    }

    public Dictionary (String word, int level) {
        this._word = word.toLowerCase();
        this._length = word.length();
        this._level = level;
        this._wordArray = word.toCharArray();
    }

    public Dictionary (int id, String word, int level) {
        this._id = id;
        this._word = word.toLowerCase();
        this._length = word.length();
        this._level = level;
        this._wordArray = word.toCharArray();
    }

    public int getID() {
        return this._id;
    }

    public void setID(int id) {
        this._id = id;
    }

    public String getWord() {
        return this._word;
    }

    public void setWord(String word) {
        this._word = word;
    }

    public int getLength() {
        return this._length;
    }

    public int getLevel() {
        return this._level;
    }

    public void setLevel(int level) {
        this._level = level;
    }

    public char[] getWordArray() {
        return this._wordArray;
    }



}
