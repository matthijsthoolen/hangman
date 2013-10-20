package com.mprog.hangman;

import android.content.Context;
import android.graphics.*;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;

import java.lang.reflect.Array;

/**
 * This class draws the gamescreen on every move.
 */
public class HangmanDraw extends View {

    Canvas canvas;
    Bitmap background;
    Bitmap gallow;

    Display display;
    int width;
    int height;
    //Fix for bigger screens
    int scrMlt = 1;

    Paint paint = new Paint();

    HangmanDraw(Context context) {
        super(context);
        background = (Bitmap) BitmapFactory.decodeResource(getResources(), R.drawable.background_gamescreen);
        paint.setStrokeWidth(8);
        paint.setColor(Color.BLACK);

        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        width = size.x;
        height = size.y;

        if (width >= 720) {
            scrMlt = 2;
        }
    }

    @Override
    public void onDraw(Canvas canvas) {
        this.canvas = canvas;
        drawBackground();
        drawGallow();
        drawWord();
        drawScore();
        drawLives();
        drawGuessed();
    }

    private void drawBackground() {
        canvas.drawBitmap(background, 0, 0, null);
    }

    /**
     * Draw the correct gallow image. Calculate the best hangman image, and then search the ID from the resource
     */
    private void drawGallow() {
        double steps = (((double)config.DRAW_GALLOW_STEPS)/Hangman.settings.get_tries()) * (Hangman.settings.get_tries() - Hangman.getLives());
        int nr;
        if (steps < 12) nr = (int)Math.round(steps);
        else if (steps >= 13) nr = 13;
        else nr = 12;

        String HANGMAN = "hangman" + nr;

        int resID = this.getResources().getIdentifier(HANGMAN, "drawable", "com.mprog.hangman");
        gallow = (Bitmap) BitmapFactory.decodeResource(getResources(), resID);
        canvas.drawBitmap(gallow, 0, (45 * scrMlt), null);
    }

    private void drawWord() {
        String[] word = Hangman.getWord();
        /**
         * Center the word and make sure it fits on the screen.
         */
        int y = 36 * scrMlt;
        int xIncrease = (width-(20 * scrMlt))/(Array.getLength(word));
        if (xIncrease > 100) {
            xIncrease = 100;
        }
        int x = (width - (Array.getLength(word) * xIncrease))/2;

        paint.setTextSize(35 * scrMlt);

        /**
         * Print each letter of the word, the empty slots are being filled with a underscore
         */
        for (String letter : word) {
            if (letter == null) {
                canvas.drawText("_", x, y, paint);
            } else {
                canvas.drawText("" + letter, x, y, paint);
            }
            x += xIncrease;
        }
    }

    /**
     * Draw the current score
     */
    private void drawScore() {
        paint.setTextSize(18 * scrMlt);
        int startX = (int)gallow.getWidth()/4;
        int startY = gallow.getHeight() + (30 * scrMlt);
        canvas.drawText("Score: " + Hangman.getScore(), startX, startY, paint);

    }

    /**
     * Draw the remaining lives
     */
    private void drawLives() {
        paint.setTextSize(18 * scrMlt);
        int startX = (int)gallow.getWidth()/5 + (140 * scrMlt);
        int startY = gallow.getHeight() + (30 * scrMlt);
        canvas.drawText("Lives: " + Hangman.getLives(), startX, startY, paint);
    }

    /**
     * Draw all the already guessed letters
     */
    private void drawGuessed() {
        int x = 2 * scrMlt;
        int y = 60 * scrMlt;
        paint.setTextSize(14 * scrMlt);
        for(String letter : Hangman.getGuessed()) {
            if (letter != null) {
                canvas.drawText(" " + letter, x, y, paint);
                x += (16 * scrMlt);
                if (x > (40 * scrMlt)) {
                    x = (2 * scrMlt);
                    y += (20 * scrMlt);
                }
            }
        }
    }

}