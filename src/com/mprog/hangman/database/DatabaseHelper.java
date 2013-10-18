package com.mprog.hangman.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import com.mprog.hangman.WordGenerator;

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
        super(context, DATABASE, null, 16);
        contextSave = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d("Hangman", "Created database");
        db.execSQL("CREATE TABLE " + TABLE_PREFERENCES + " (id INTEGER PRIMARY KEY , language String, nrWords INTEGER)");
        db.execSQL("CREATE TABLE " + TABLE_DICTIONARY + " (id INTEGER PRIMARY KEY , word TEXT, length INTEGER, level INTEGER)");
        db.execSQL("CREATE TABLE " + TABLE_HISTORY + " (id INTEGER PRIMARY KEY , word_id INTEGER, last_used INTEGER)");
        db.execSQL("CREATE TABLE " + TABLE_SCOREBOARD + " (id INTEGER PRIMARY KEY , username INTEGER, score INTEGER, level INTEGER, lives INTEGER, word_id INTEGER, date INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PREFERENCES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DICTIONARY);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_HISTORY);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SCOREBOARD);
        onCreate(db);
    }

    public void setPreferences(String language, int nrWords) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("language", language);
        values.put("nrWords", nrWords);

        db.insert(TABLE_PREFERENCES, null, values);
        db.close();
        Log.d("Hangman debug", "Preferences added!");
    }

    public Preferences getPreferences() {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_PREFERENCES, new String[] { "id",
                "language", "nrWords"}, "id" + "=?",
                new String[] { String.valueOf(1) }, null, null, null, null);

        if(cursor != null) {
            cursor.moveToFirst();
        }

        Preferences pref = new Preferences(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getInt(2));

        Log.d("Hangman debug", "Preferences count BLA " + pref.getNrWords());

        db.close();

        return pref;
    }

    public void addWord(Dictionary word) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("word", word.getWord());
        values.put("length", word.getLength());
        values.put("level", word.getLevel());

        db.insert(TABLE_DICTIONARY, null, values);
        db.close();
    }

    /**
     * Get a random word in the Dictionary object
     * @return Dictionary
     */
    public Dictionary getRandomWord(Settings settings) {
        int count = (int) getRecordCount(TABLE_DICTIONARY);
        SQLiteDatabase db = this.getReadableDatabase();

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

        Dictionary word = new Dictionary(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getInt(2));

        Log.d("Hangman debug", "WORD = " + word.getWord());
        Log.d("Hangman debug", "ID = " + word.getID());

        return word;
    }

    /**
     * Count the number of entries in the given table
     * @param tableName
     * @return
     */
    public long getRecordCount(String tableName) {
        SQLiteDatabase db = this.getWritableDatabase();
        long count = DatabaseUtils.queryNumEntries(db, tableName);
        db.close();
        return count;
    }

    /**
     * Add the final score to the database
     * @param score
     */
    public void addScore(Scoreboard score) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("username", score.getUsername());
        values.put("score", score.getScore());
        values.put("level", score.getLevel());
        values.put("lives", score.getLives());
        values.put("word_id", score.getWordID());
        values.put("date", score.getDate());

        db.insert(TABLE_SCOREBOARD, null, values) ;
        db.close();

        Log.d("Hangman debug", "Score saved!");
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
