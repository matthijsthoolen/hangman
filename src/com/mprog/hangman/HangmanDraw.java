package com.mprog.hangman;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class HangmanDraw extends View {
    Paint paint = new Paint();
    Paint green, black, beige, brown;

    public HangmanDraw(Context context) {
        super(context);
        paint.setStrokeWidth(8);
        green = black = beige = brown = new Paint(paint);
        green.setColor(Color.GREEN);
        black.setColor(Color.BLACK);
        beige.setColor(Color.parseColor("#ffd070"));
        brown.setColor(Color.parseColor("#5b331d"));
    }

    @Override
    public void onDraw(Canvas canvas) {
        drawWord(canvas, Hangman.getWord());
        GallowDraw(canvas, Hangman.getLives());
        ScoreDraw(canvas, Hangman.getScore());
        LivesDraw(canvas, Hangman.getLives());
        drawGuessed(canvas, Hangman.getGuessed());
    }

    private void drawWord(Canvas canvas, String[] word) {
        int x = 50;
        int y = 700;
        black.setTextSize(50);

        for (String letter : word) {
            if (letter == null) {
                canvas.drawText("_", x, y, black);
            } else {
                canvas.drawText("" + letter, x, y, black);
            }
            x += 50;
        }
    }

    private void ScoreDraw(Canvas canvas, int score) {
        black.setTextSize(30);
        canvas.drawText("Score:" + score, 550, 30, black);
    }

    private void LivesDraw(Canvas canvas, int lives) {
        black.setTextSize(30);
        canvas.drawText("lives:" + lives, 550, 75, black);
    }

    private void drawGuessed(Canvas canvas, String[] guessedArray)
    {
        int x = 400;
        int y = 200;
        black.setTextSize(40);
        for(String letter : guessedArray) {
            if (letter != null) {
                canvas.drawText(" " + letter, x, y, black);
                x += 35;
                if (x > 650) {
                    x = 400;
                    y += 40;
                }
            }
        }
    }

    private void GallowDraw(Canvas canvas, int i) {
        if (i <= 12) canvas.drawLine(10, 600, 400, 600, green); //bottom
        if (i <= 11) canvas.drawLine(100, 192, 100, 600, brown); //paal
        if (i <= 10) canvas.drawLine(100, 550, 150, 600, brown); //paalsteun bottom 1
        if (i <= 9) canvas.drawLine(100, 550, 50, 600, brown); //paalsteun bottom 2
        if (i <= 8) canvas.drawLine(100, 250, 150, 200, brown); //bovenbalk steun
        if (i <= 7) canvas.drawLine(100, 200, 320, 200, brown); //bovenbalk
        if (i <= 6) canvas.drawLine(316, 200, 316, 250, brown); //touw
        if (i <= 5) canvas.drawCircle(316, 250, 35, beige); //hoofd
        if (i <= 4) canvas.drawOval(new RectF(280, 285, 350, 435),beige); //body
        if (i <= 3) canvas.drawLine(300, 290, 250, 400, beige); //arm1
        if (i <= 2) canvas.drawLine(330, 290, 380, 400, beige); //arm2
        if (i <= 1) canvas.drawLine(300, 425, 290, 495, beige); //leg1
        if (i <= 0) canvas.drawLine(330, 425, 340, 495, beige); //leg2
    }

}