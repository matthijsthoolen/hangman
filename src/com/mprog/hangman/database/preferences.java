package com.mprog.hangman.database;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: Matthijs
 * Date: 4-10-13
 * Time: 10:35
 * To change this template use File | Settings | File Templates.
 */
public class Preferences implements Serializable {
    int _preferences_id;
    String _language;
    int _nrWords;

    public Preferences() {

    }

    public Preferences(String language, int nrWords) {
        this._language = language;
        this._nrWords = nrWords;
    }

    public Preferences(int preferences_id, String _language, int nrWords) {
        this._preferences_id = preferences_id;
        this._language = _language;
        this._nrWords = nrWords;
    }

    public int getID() {
        return this._preferences_id;
    }

    public void setID(int id) {
        this._preferences_id = id;
    }

    public String getLanguage() {
        return this._language;
    }

    public void setLanguage(String language) {
        this._language = language;
    }

    public int getNrWords() {
        return this._nrWords;
    }

    public void setNrWords(int nrWords) {
        this._nrWords = nrWords;
    }
}

