package com.mprog.hangman;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;
import com.mprog.hangman.database.Settings;

/**
 * Created with IntelliJ IDEA.
 * User: Matthijs
 * Date: 19-10-13
 * Time: 16:35
 * To change this template use File | Settings | File Templates.
 */
public class CustomSettings extends Activity {

    SeekBar livesSeekbar;
    SeekBar lettersSeekbar;
    TextView livesProgress;
    TextView letterProgress;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customsettings);

        livesSeekbar = (SeekBar)findViewById(R.id.NrTries);
        livesSeekbar.incrementProgressBy(1);
        livesSeekbar.setProgress(config.ORIGINAL_CUSTOM_LIVES);
        livesSeekbar.setMax(26);

        lettersSeekbar = (SeekBar)findViewById(R.id.wordLength);
        lettersSeekbar.incrementProgressBy(1);
        lettersSeekbar.setProgress(config.ORIGINAL_CUSTOM_MAXLENGTH);
        lettersSeekbar.setMax(12);

        livesProgress = (TextView)findViewById(R.id.NrTriesProgress);
        livesProgress.setText("" + config.ORIGINAL_CUSTOM_LIVES);

        letterProgress = (TextView)findViewById(R.id.wordLengthProgress);
        letterProgress.setText("" + config.ORIGINAL_CUSTOM_MAXLENGTH);

        livesSeekbar.setOnSeekBarChangeListener(new seekbarListener());
        lettersSeekbar.setOnSeekBarChangeListener(new seekbarListener());
    }

    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.backMenu:
                Settings settings = new Settings(1, 13, 2, 14);
                Intent nextScreen = new Intent(getApplicationContext(), OriginalMenu.class);
                startActivity(nextScreen);
                break;
            case R.id.play:
                settings = new Settings(4, livesSeekbar.getProgress(), lettersSeekbar.getProgress(), lettersSeekbar.getProgress());
                nextScreen = new Intent(getApplicationContext(), GameScreen.class);
                nextScreen.putExtra("Settings", settings);
                startActivity(nextScreen);
                break;
        }
    }

    private class seekbarListener implements SeekBar.OnSeekBarChangeListener {

        /**
         * Check the seekbars and set the textview next to it
         * @param bar
         * @param progress
         * @param fromUser
         */
        public void onProgressChanged(SeekBar bar, int progress, boolean fromUser) {

            switch (bar.getId()) {

               case R.id.NrTries:
                   /**
                    * Value = 0 isnt possible, so autocorrect it to 1
                    */
                   if (progress == 0)
                   {
                       progress = 1;
                       livesSeekbar.setProgress(progress);
                   }
                    livesProgress.setText("" + progress);
                    break;

                case R.id.wordLength:
                    if (progress == 0)
                    {
                        progress = 1;
                        lettersSeekbar.setProgress(progress);
                    }
                    letterProgress.setText("" + progress);
                    break;
            }
        }

        public void onStartTrackingTouch(SeekBar seekBar) {}

        public void onStopTrackingTouch(SeekBar seekBar) {}
    }


}
