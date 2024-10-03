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
    private Paint house = new Paint();
    private Paint sun = new Paint();
    private Paint cloud = new Paint();
    private Paint brownPaint = new Paint(); // For bear features
    private Paint gameOverPaint = new Paint(); // For Game Over "X"

    private int bearY = 600, bearDY = 0;
    private int bearX = 200;  // X-axis movement for the bear
    private int obstacleX = 1000, obstacleSpeed = 10;
    private boolean jump = false;
    private final int gravity = 2;  // Reduced gravity for a longer jump
    private final int jumpStrength = -50;  // Stronger jump
    private int bearSpeed = 5; // Speed for bear movement along the x-axis
    private boolean isGameOver = false; // Track game over state
    private MainActivity mainActivity; // Reference to MainActivity

    public DrawView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        if (context instanceof MainActivity) {
            mainActivity = (MainActivity) context; // Assign MainActivity reference
        }

        // Set colors for the paints
        bear.setColor(Color.rgb(139, 69, 19)); // Bear color (brown)
        obstacle.setColor(Color.RED); // Obstacle color
        grass.setColor(Color.GREEN); // Grass color
        sky.setColor(Color.CYAN); // Sky color
        house.setColor(Color.rgb(255, 222, 173)); // House color (light peach)
        sun.setColor(Color.YELLOW); // Sun color
        cloud.setColor(Color.LTGRAY); // Cloud color
        brownPaint.setColor(Color.BLACK); // For eyes, nose, and mouth
        gameOverPaint.setColor(Color.RED); // Game Over "X"
        gameOverPaint.setTextSize(200); // X size
        gameOverPaint.setStrokeWidth(10);
        gameOverPaint.setStyle(Paint.Style.STROKE);
    }

    @Override
    protected void onDraw(@NonNull Canvas canvas) {
        // Draw the background and game elements regardless of the game state

        // Draw sky
        canvas.drawRect(0, 0, getWidth(), 400, sky); // Sky at the top of the screen

        // Draw sun
        canvas.drawCircle(100, 100, 80, sun); // Sun in the top left corner

        // Draw clouds
        canvas.drawOval(300, 100, 500, 180, cloud); // Cloud 1
        canvas.drawOval(600, 80, 900, 160, cloud);  // Cloud 2

        // Draw grass
        canvas.drawRect(0, 400, getWidth(), getHeight(), grass); // Grass at the bottom of the screen

        // Draw grass blades for detail
        for (int i = 0; i < getWidth(); i += 20) {
            canvas.drawLine(i, 400, i + 10, 380, grass); // Grass blades
        }

        // Draw house
        canvas.drawRect(800, 300, 1000, 500, house); // House base
        canvas.drawRect(850, 200, 950, 300, house); // House roof
        canvas.drawRect(850, 400, 900, 450, brownPaint); // House door

        // Draw bear with body
        canvas.drawCircle(bearX, bearY, 50, bear); // Bear body
        canvas.drawCircle(bearX, bearY - 80, 40, bear); // Bear head
        canvas.drawCircle(bearX - 30, bearY - 120, 20, bear); // Left ear
        canvas.drawCircle(bearX + 30, bearY - 120, 20, bear); // Right ear
        canvas.drawCircle(bearX - 15, bearY - 90, 8, brownPaint); // Left eye
        canvas.drawCircle(bearX + 15, bearY - 90, 8, brownPaint); // Right eye
        canvas.drawCircle(bearX, bearY - 70, 10, brownPaint); // Nose
        canvas.drawArc(bearX - 15, bearY - 60, bearX + 15, bearY - 50, 0, 180, false, brownPaint); // Mouth
        canvas.drawRect(bearX - 30, bearY + 50, bearX - 10, bearY + 90, bear); // Left leg
        canvas.drawRect(bearX + 10, bearY + 50, bearX + 30, bearY + 90, bear); // Right leg
        canvas.drawRect(bearX - 60, bearY - 30, bearX - 40, bearY + 30, bear); // Left arm
        canvas.drawRect(bearX + 40, bearY - 30, bearX + 60, bearY + 30, bear); // Right arm

        // Move bear along the x-axis
        bearX += bearSpeed;
        if (bearX > getWidth()) {
            bearX = 0;
        }

        // Draw obstacle
        canvas.drawRect(obstacleX, 650, obstacleX + 100, 700, obstacle);

        // Move obstacle from right to left
        obstacleX -= obstacleSpeed;
        if (obstacleX < -100) {
            obstacleX = getWidth();
        }

        // Apply gravity to bear
        bearY += bearDY;
        if (bearY >= 600) {
            bearY = 600;
            bearDY = 0;
            jump = false;
        } else {
            bearDY += gravity;
        }

        // Check for collision and handle game over
        if (obstacleX < bearX + 50 && obstacleX + 100 > bearX - 50 && bearY > 550) {
            isGameOver = true; // Collision detected: switch to game over state
        }

        // If the game is over, display the "X" and trigger the restart button
        if (isGameOver) {
            canvas.drawText("X", getWidth() / 2 - 50, getHeight() / 2, gameOverPaint);
            if (mainActivity != null) {
                mainActivity.gameOver(); // Notify MainActivity that the game is over
            }
        }

        // Keep the screen redrawing
        invalidate();
    }

    // Make the bear jump
    public void jump() {
        if (!jump && !isGameOver) { // Only jump if on the ground and not game over
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
