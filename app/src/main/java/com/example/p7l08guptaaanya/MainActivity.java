package com.example.p7l08guptaaanya;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    DrawView drawview;
    Button restartButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        drawview = findViewById(R.id.drawView);
        restartButton = findViewById(R.id.restart);
        restartButton.setVisibility(View.GONE);
    }

    public void jump(View view) {
        if(!drawview.isGameOver()){
            drawview.jump();
        }
    }

    public void gameOver() {
        restartButton.setVisibility(View.VISIBLE);
    }

    public void restart(View view) {
        drawview.restart();
        restartButton.setVisibility(View.GONE);
    }
}