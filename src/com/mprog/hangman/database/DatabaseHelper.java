package com.mprog.hangman.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {

    static final String DATABASE = "Hangman";
    static final String TABLE_DICTIONARY = "WordGenerator";
    static final String TABLE_HISTORY = "History";
    static final String TABLE_SCOREBOARD = "ScoreboardView";
    public DatabaseHelper(Context context) {
        super(context, DATABASE, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_DICTIONARY + " (id INTEGER PRIMARY KEY , word TEXT, length INTEGER, level INTEGER)");
        db.execSQL("CREATE TABLE " + TABLE_HISTORY + " (id INTEGER PRIMARY KEY , word_id INTEGER, last_used INTEGER)");
        db.execSQL("CREATE TABLE " + TABLE_SCOREBOARD + " (id INTEGER PRIMARY KEY , username INTEGER, score INTEGER, level INTEGER, lives INTEGER, word_id INTEGER, date INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DICTIONARY);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_HISTORY);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SCOREBOARD);
        onCreate(db);
    }

    void addWord(Dictionary word) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("id", word.getID());
        values.put("word", word.getWord());
        values.put("length", word.getLength());
        values.put("level", word.getLevel());

        db.insert(TABLE_DICTIONARY, null, values);
        db.close();
    }

    void getRandomWord() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor mCount = db.rawQuery("SELECT COUNT(*) FROM "+ TABLE_DICTIONARY, null, null);
        mCount.moveToFirst();
        int count = mCount.getInt(0);
        mCount.close();
        Log.d("COUNTER", "COUNT = " + count);
    }

    void addScore(Scoreboard score) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("id", score.getID());
        values.put("username", score.getUsername());
        values.put("score", score.getScore());
        values.put("level", score.getLevel());
        values.put("lives", score.getLives());
        values.put("word_id", score.getWordID());
        values.put("date", score.getDate());

        db.insert(TABLE_SCOREBOARD, null, values) ;
        db.close();
    }

    void makeHistory(History history) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("id", history.getID());
        values.put("word_id", history.getWordID());
        values.put("last_used", history.getUsed());

        db.insert(TABLE_HISTORY, null, values);
        db.close();
    }


}
