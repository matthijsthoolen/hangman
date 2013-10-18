package com.mprog.hangman;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import com.mprog.hangman.database.DatabaseHelper;

public class ScoreboardView extends Activity {
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scoreboard);
        generateScoreboard();
    }

    /**
     * Generate scoreboard in a table
     */
    public void generateScoreboard() {
        DatabaseHelper dbHelper = new DatabaseHelper(Launcher.mainContext);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(dbHelper.TABLE_SCOREBOARD, new String[]{"score",
                "level", "lives", "word_id"}, null, null, null, null, "score DESC", "10");

//        while (cursor.moveToNext()) {
//            String scoreNr = "Score"
//            Log.d("Hangman debug", "Score = " + cursor.getInt(0));
//            TextView textView = (TextView) findViewById(R.id.score);
//            textView.setText(text);
//        }
        if (!cursor.moveToNext()) {
            return;
        }
        TextView textView = (TextView) findViewById(R.id.score1);
        textView.setText("Score: " + cursor.getInt(0));
        if (!cursor.moveToNext()) {
            return;
        }
        textView = (TextView) findViewById(R.id.score2);
        textView.setText("Score: " + cursor.getInt(0));
        if (!cursor.moveToNext()) {
            return;
        }
        textView = (TextView) findViewById(R.id.score3);
        textView.setText("Score: " + cursor.getInt(0));
        if (!cursor.moveToNext()) {
            return;
        }
        textView = (TextView) findViewById(R.id.score4);
        textView.setText("Score: " + cursor.getInt(0));
        if (!cursor.moveToNext()) {
            return;
        }
        textView = (TextView) findViewById(R.id.score5);
        textView.setText("Score: " + cursor.getInt(0));


    }

    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.backMenu:
                Intent nextScreen = new Intent(getApplicationContext(), MainMenu.class);
                startActivity(nextScreen);
                break;
        }
    }
}
