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
    private Paint p = new Paint();
    private int y = 0, dY = 5;

    public DrawView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(@NonNull Canvas canvas) {
        super.onDraw(canvas);
        p.setColor(Color.BLUE);
        canvas.drawCircle(100, y, 130.5f, p);
        canvas.drawCircle(400, 200, 130.5f, new Paint());
        y += dY;
        y %= getHeight();
        invalidate(); //tells android view is not correct, so calls onDraw again
    }

    public int getdY() {
        return dY;
    }

    public void setdY(int dY) {
        this.dY = dY;
    }
}

