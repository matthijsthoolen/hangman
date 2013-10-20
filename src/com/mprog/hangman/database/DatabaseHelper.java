package com.mprog.hangman.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import com.mprog.hangman.Launcher;

import java.util.Random;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE = "Hangman";
    public static final String TABLE_DICTIONARY = "WordGenerator";
    public static final String TABLE_HISTORY = "History";
    public static final String TABLE_SCOREBOARD = "ScoreboardView";
    public static final String TABLE_PREFERENCES = "Preferences";

    private Context contextSave;

    public static int gameQueue = 0;
    public static int asyncQueue = 0;

    public DatabaseHelper(Context context) {
        super(context, DATABASE, null, 1);
        contextSave = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_PREFERENCES + " (id INTEGER PRIMARY KEY , language String, nrWords INTEGER)");
        db.execSQL("CREATE TABLE " + TABLE_DICTIONARY + " (id INTEGER PRIMARY KEY , word TEXT, length INTEGER, level INTEGER)");
        db.execSQL("CREATE TABLE " + TABLE_HISTORY + " (id INTEGER PRIMARY KEY , word_id INTEGER, last_used INTEGER)");
        db.execSQL("CREATE TABLE " + TABLE_SCOREBOARD + " (id INTEGER PRIMARY KEY , username INTEGER, score INTEGER, level INTEGER, lives INTEGER, word STRING, date INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PREFERENCES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DICTIONARY);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_HISTORY);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SCOREBOARD);
        onCreate(db);
    }

    /**
     * Save the user preferences to the database.
     * @param language
     * @param nrWords
     */
    public void setPreferences(String language, int nrWords) {
        SQLiteDatabase db = getDatabaseConnection("writable");

        ContentValues values = new ContentValues();
        values.put("language", language);
        values.put("nrWords", nrWords);

        db.insert(TABLE_PREFERENCES, null, values);
        db.close();
    }

    /**
     * Get the preferences from the database..
     * @return
     */
    public Preferences getPreferences() {
        SQLiteDatabase db = getDatabaseConnection("readable");

        Cursor cursor = db.query(TABLE_PREFERENCES, new String[] { "id",
                "language", "nrWords"}, "id" + "=?",
                new String[] { String.valueOf(1) }, null, null, null, null);

        if(cursor != null) {
            cursor.moveToFirst();
        }

        Preferences pref = new Preferences(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getInt(2));

        db.close();

        return pref;
    }

    /**
     * Add a new word to the database.
     * @param word
     */
    public void addWord(Word word) {
        SQLiteDatabase db = getDatabaseConnection("writable");

        ContentValues values = new ContentValues();
        values.put("word", word.getWord());
        values.put("length", word.getLength());
        values.put("level", word.getLevel());

        db.insert(TABLE_DICTIONARY, null, values);
        db.close();
    }

    /**
     * Get a random word in the Word object
     * @return Word
     */
    public Word getRandomWord(Settings settings) {
        int count = (int) getRecordCount(TABLE_DICTIONARY);
        SQLiteDatabase db = getDatabaseConnection("readable");

        Random r = new Random();
        int random = r.nextInt(count - 1 + 1) + 1;

        Cursor cursor = db.query(TABLE_DICTIONARY, new String[] { "id",
                "word", "level"}, "id >=? AND length >= ? AND length <= ?",
                new String[] { String.valueOf(random), String.valueOf(settings.get_minlength()), String.valueOf(settings.get_maxlength()) }, null, null, null, null);

        /*
         * If there isnt a word that matches our query (id bigger than the random number), search for a id smaller than
         * the random number and matches the other requirements.
         */
        if(cursor.getCount() == 0) {
            cursor.moveToFirst();
            cursor = db.query(TABLE_DICTIONARY, new String[] { "id",
                "word", "level"}, "id <=? AND length >= ? AND length <= ?",
                new String[] { String.valueOf(random), String.valueOf(settings.get_minlength()), String.valueOf(settings.get_maxlength()) }, null, null, "id DESC", "1");
        }

        cursor.moveToFirst();

        Word word = new Word(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getInt(2));

        return word;
    }

    /**
     * Count the number of entries in the given table
     * @param tableName
     * @return
     */
    public long getRecordCount(String tableName) {
        SQLiteDatabase db = getDatabaseConnection("writable");
        long count = DatabaseUtils.queryNumEntries(db, tableName);
        db.close();
        return count;
    }

    /**
     * Add the final score to the database
     * @param score
     */
    public void addScore(Scoreboard score) {
        SQLiteDatabase db = getDatabaseConnection("writable");

        ContentValues values = new ContentValues();
        values.put("username", score.getUsername());
        values.put("score", score.getScore());
        values.put("level", score.getLevel());
        values.put("lives", score.getLives());
        values.put("word", score.getWord());
        values.put("date", score.getDate());

        db.insert(TABLE_SCOREBOARD, null, values) ;
        db.close();
    }

    /**
     * Not used (yet)
     * @param history
     */
    void makeHistory(History history) {
        SQLiteDatabase db = getDatabaseConnection("writable");

        ContentValues values = new ContentValues();
        values.put("id", history.getID());
        values.put("word_id", history.getWordID());
        values.put("last_used", history.getUsed());

        db.insert(TABLE_HISTORY, null, values);
        db.close();
    }

    /**
     * Return a readable or writable database, catch the NullPointerException.
     * @param sort
     * @return
     */
    private SQLiteDatabase getDatabaseConnection(String sort) {

        if (sort.equals("readable") || sort.equals("writable")) {

            SQLiteDatabase db;

            try {
                if (sort.equals("readable"))db = this.getReadableDatabase();
                else db = this.getWritableDatabase();
            } catch (NullPointerException e) {
                Log.e("Hangman","catched NullPointerException @ addWord");
                DatabaseHelper dbHelp = new DatabaseHelper(contextSave);

                if (sort.equals("readable"))db = dbHelp.getReadableDatabase();
                else db = dbHelp.getWritableDatabase();
            }

            return db;
        }

        return null;
    }


}
