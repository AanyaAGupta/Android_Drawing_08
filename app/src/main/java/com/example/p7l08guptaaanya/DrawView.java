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
//    private Paint p = new Paint();
//    private int y = 0, dY = 5;

    private Paint bear = new Paint();
    private Paint obstacle = new Paint();
    private int bearY = 600, bearDY = 0;
    private int obstacleX = 1000, obstacleSpeed = 10;
    private boolean jump = false;
    private final int gravity = 3, jumpStrength = -30;

    public DrawView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(@NonNull Canvas canvas) {
        super.onDraw(canvas);

        //step 1: draw bear
        canvas.drawCircle(200, bearY, 50, bear); //body
        canvas.drawCircle(170, bearY - 50, 20, bear); //ear
        canvas.drawCircle(230, bearY - 50, 20, bear);

        //step 2: draw obstacle
        canvas.drawRect(obstacleX, 650, obstacleX + 100, 700, obstacle);

        //step 3: make obstacle move
        obstacleX -= obstacleSpeed;
        if(obstacleX < -100){
            obstacleX = getWidth();
        }

        //step 4: move bear
        bearY += bearDY;
        if(bearY >= 600){
            bearY = 600;
            bearDY = 0;
            jump = false;
        } else{
            bearDY += gravity;
        }

        //step 5: check collision
        if(obstacleX < 250 && obstacleX + 100 > 150 && bearY > 550){
            //reset
            bearY = 600;
            obstacleX = getWidth();
        }

        invalidate();

    }

    public void jump(){
        if(!jump){ //if on ground
            bearDY = jumpStrength;
            jump = true;
        }
    }

}

