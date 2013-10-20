package com.mprog.hangman;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.mprog.hangman.database.DatabaseHelper;

public class ScoreboardView extends Activity {

    ListView listview;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scoreboard);
        listview = (ListView) findViewById(R.id.listView);
        generateScoreboard();
    }

    /**
     * Generate scoreboard in a listview
     */
    public void generateScoreboard() {
        DatabaseHelper dbHelper = new DatabaseHelper(this.getApplicationContext());
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(dbHelper.TABLE_SCOREBOARD, new String[]{"score",
                "level", "lives", "word"}, null, null, null, null, "score DESC", "10");

        String[] items = new String[11];

        int i = 1;

        items[0] = "rank: score - word - tries";

        while (cursor.moveToNext()) {
            items[i] = "" + i + ": " + cursor.getInt(0) + " - " + cursor.getString(3) + " - " + cursor.getInt(2);
            i++;
        }

        if (cursor != null) {

            ArrayAdapter<String> adapter =
                    new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);

            listview.setAdapter(adapter);
        }

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
