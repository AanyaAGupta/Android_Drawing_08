package com.example.p7l08guptaaanya;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class DrawView extends View {
    private Paint bear = new Paint();
    private Paint obstacle = new Paint();
    private Paint grass = new Paint();
    private Paint sky = new Paint();
    //private Paint house = new Paint();
    private Paint sun = new Paint();
    private Paint cloud = new Paint();
    private Paint brownPaint = new Paint();
    private Paint gameOverPaint = new Paint();

    private int bearY = 600, bearDY = 0;
    private int bearX = 200;
    private int obstacleX = 1000, obstacleSpeed = 8;
    private boolean jump = false;
    private final int gravity = 2;
    private final int jumpStrength = -50;
    private int bearSpeed = 4;
    private boolean isGameOver = false;
    private MainActivity mainActivity;

    public DrawView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        if (context instanceof MainActivity) {
            mainActivity = (MainActivity) context;
        }

        bear.setColor(Color.rgb(139, 69, 19));
        obstacle.setColor(Color.RED);
        grass.setColor(Color.GREEN);
        sky.setColor(Color.CYAN);
        //house.setColor(Color.rgb(255, 222, 173));
        sun.setColor(Color.YELLOW);
        cloud.setColor(Color.WHITE);
        brownPaint.setColor(Color.BLACK);
        gameOverPaint.setColor(Color.RED);
        gameOverPaint.setTextSize(400);
        //gameOverPaint.setStrokeWidth(10);
        //gameOverPaint.setStyle(Paint.Style.STROKE);
    }

    @Override
    protected void onDraw(@NonNull Canvas canvas) {
        //draw the sky:
        canvas.drawRect(0, 0, getWidth(), 400, sky);

        //draw the sun
        canvas.drawCircle(100, 100, 80, sun);

        //draw the clouds
        canvas.drawOval(300, 100, 500, 180, cloud);
        canvas.drawOval(600, 80, 900, 160, cloud);

        //draw the grass
        canvas.drawRect(0, 400, getWidth(), getHeight(), grass);

        //add more detail to grass?
        for (int i = 0; i < getWidth(); i += 20) {
            canvas.drawLine(i, 400, i + 10, 380, grass);
        }

        //draw a house
//        canvas.drawRect(800, 300, 1000, 500, house);
//        canvas.drawRect(850, 200, 950, 300, house);
//        canvas.drawRect(850, 400, 900, 450, brownPaint);

        //draw the bear
        canvas.drawCircle(bearX, bearY, 50, bear); //body
        canvas.drawCircle(bearX, bearY - 80, 40, bear); //head
        canvas.drawCircle(bearX - 30, bearY - 120, 20, bear); //ear
        canvas.drawCircle(bearX + 30, bearY - 120, 20, bear); //ear
        canvas.drawCircle(bearX - 15, bearY - 90, 8, brownPaint); //eye
        canvas.drawCircle(bearX + 15, bearY - 90, 8, brownPaint); //eye
        canvas.drawCircle(bearX, bearY - 70, 10, brownPaint); //nose
        canvas.drawArc(bearX - 15, bearY - 60, bearX + 15, bearY - 50, 0, 180, false, brownPaint); //mouth
        canvas.drawRect(bearX - 30, bearY + 50, bearX - 10, bearY + 90, bear); // leg
        canvas.drawRect(bearX + 10, bearY + 50, bearX + 30, bearY + 90, bear); //leg

        //make the bear move
        bearX += bearSpeed;
        if (bearX > getWidth()) {
            bearX = 0;
        }

        //obstacle
        canvas.drawRect(obstacleX, 650, obstacleX + 100, 700, obstacle);

        //make obstacle move
        obstacleX -= obstacleSpeed;
        if (obstacleX < -100) {
            obstacleX = getWidth();
        }

        //gravity + jump
        bearY += bearDY;
        if (bearY >= 600) {
            bearY = 600;
            bearDY = 0;
            jump = false;
        } else {
            bearDY += gravity;
        }

        //collision with obstacle
        if (obstacleX < bearX + 50 && obstacleX + 100 > bearX - 50 && bearY > 550) {
            isGameOver = true;
        }

        //game over
        if (isGameOver) {
            canvas.drawText("X", getWidth()/2 - 50, getHeight()/2, gameOverPaint);
            if (mainActivity != null) {
                mainActivity.gameOver();
            }
        }

        invalidate();
    }

    public void jump() {
        if (!jump && !isGameOver) {
            bearDY = jumpStrength;
            jump = true;
        }
    }

    public boolean isGameOver() {
        return isGameOver;
    }

    public void restart() {
        isGameOver = false;
        bearX = 200;
        obstacleX = 1000;
        bearY = 600;
        bearDY = 0;
        jump = false;
    }
}
