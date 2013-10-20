package com.mprog.hangman;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;
import com.mprog.hangman.database.Word;
import com.mprog.hangman.database.DatabaseHelper;

import java.io.InputStream;

/**
 * WordGenerator copies all the content from the XML file to the SQLitedatabase
 */
public class WordGenerator extends AsyncTask<String, Integer, String> {

    private Context context;
    private launcherInterface bridge;
    private String fileName;
    private int startNr;

    public WordGenerator(Context newContext, launcherInterface newBridge, String file, int start) {
        context = newContext;
        bridge = newBridge;
        fileName = file;
        startNr = start;
    }

    @Override
    protected String doInBackground(String... params) {
        try {
            XMLtoDB(context);
        }
        catch (Exception e) {
            Log.e("Hangman", "Error while parsing", e);
        }

        return "Executed";
    }

    @Override
    /**
     * Update the launcher with the progress,
     */
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        int value = values[0].intValue();
        bridge.updateLaunchScreen(value);
    }

    /**
     * Put the XML words into the database
     * @param context
     * @throws Exception
     */
    private void XMLtoDB(Context context) throws Exception {
        DatabaseHelper db = new DatabaseHelper(context);
        XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
        factory.setNamespaceAware(false);
        XmlPullParser xpp = factory.newPullParser();

        int id = context.getResources().getIdentifier(fileName, "raw", "com.mprog.hangman");
        InputStream is = context.getResources().openRawResource(id);

        xpp.setInput(is, null);
        xpp.nextTag();
        xpp.nextTag();
        xpp.nextTag();

        int eventType = xpp.getEventType();
        int i = 0;

        /*
         * If the database is already partly filled, start where we left off.
         */
        if (startNr != 0) {
            while (startNr > (i - 1)) {

                if (xpp.getName() != null && xpp.getName().equals("item")) {
                    xpp.next();
                    xpp.nextTag();
                    i++;
                }

                xpp.next();
            }
        }

        while (eventType != XmlPullParser.END_DOCUMENT) {

            if (xpp.getName() != null && xpp.getName().equals("item")) {
                xpp.next();
                Word word = new Word(xpp.getText(), 1);

                DatabaseHelper.asyncQueue = 1;

                while (DatabaseHelper.gameQueue == 1) {
                    Thread.sleep(500);
                };

                db.addWord(word);

                DatabaseHelper.asyncQueue = 0;

                xpp.nextTag();

                /**
                 * On every X insert, update the launcher with the current progress.
                 */
                if ((i % 100)== 0)
                {
                    publishProgress(i);
                }
                i++;
            }

            eventType = xpp.next();

        }
    }
}
