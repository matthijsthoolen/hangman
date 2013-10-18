package com.mprog.hangman.database;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: Matthijs
 * Date: 18-10-13
 * Time: 15:45
 * To change this template use File | Settings | File Templates.
 */
public class Settings implements Serializable{

    int _level;
    int _tries;
    int _minlength;
    int _maxlength;
    int _time;

    public Settings(int _level, int _tries, int _minlength) {
        this._level = _level;
        this._tries = _tries;
        this._minlength = _minlength;
        this._maxlength = _minlength;
    }

    public Settings(int _level, int _tries, int _minlength, int _maxlength) {
        this._level = _level;
        this._tries = _tries;
        this._minlength = _minlength;
        this._maxlength = _maxlength;
    }

    public Settings(int _level, int _tries, int _minlength, int _maxlength, int _time) {
        this._level = _level;
        this._tries = _tries;
        this._minlength = _minlength;
        this._maxlength = _maxlength;
        this._time = _time;
    }

    public int get_level() {
        return _level;
    }

    public void set_level(int _level) {
        this._level = _level;
    }

    public int get_tries() {
        return _tries;
    }

    public void set_tries(int _tries) {
        this._tries = _tries;
    }

    public int get_minlength() {
        return _minlength;
    }

    public void set_minlength(int _minlength) {
        this._minlength = _minlength;
    }

    public int get_maxlength() {
        return _maxlength;
    }

    public void set_maxlength(int _maxlength) {
        this._maxlength = _maxlength;
    }

    public int get_time() {
        return _time;
    }

    public void set_time(int _time) {
        this._time = _time;
    }
}
