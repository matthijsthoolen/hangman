package com.mprog.hangman.database;

/**
 * Created with IntelliJ IDEA.
 * User: Matthijs
 * Date: 4-10-13
 * Time: 10:35
 * To change this template use File | Settings | File Templates.
 */
public class History {
    int _history_id;
    int _word_id;
    int _last_used;

    public History() {

    }

    public History(int word_id, int last_used) {
        this._word_id = word_id;
        this._last_used = last_used;
    }

    public History(int history_id, int word_id, int last_used) {
        this._history_id = history_id;
        this._word_id = word_id;
        this._last_used = last_used;
    }

    public int getID() {
        return this._history_id;
    }

    public void setID(int id) {
        this._history_id = id;
    }

    public int getWordID() {
        return this._word_id;
    }

    public void setWordID(int wordID) {
        this._word_id = wordID;
    }

    public int getUsed() {
        return this._last_used;
    }

    public void setUsed(int used) {
        this._last_used = used;
    }
}

