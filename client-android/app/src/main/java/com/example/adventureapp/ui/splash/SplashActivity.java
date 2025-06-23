package com.example.adventureapp.ui.splash;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.adventureapp.R;
import com.example.adventureapp.ui.main.MainActivity;

public class SplashActivity extends AppCompatActivity {

    private TextView splashText;
    private final Handler handler = new Handler(Looper.getMainLooper());
    private int dotCount = 0;
    private boolean isRunning = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        splashText = findViewById(R.id.splashText);
        startDotAnimation();

        handler.postDelayed(() -> {
            isRunning = false;
            startActivity(new Intent(SplashActivity.this, MainActivity.class));
            finish();
        }, 6000);
    }

    private void startDotAnimation() {
        splashText.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!isRunning) return;
                dotCount = (dotCount + 1) % 4;
                splashText.setText("ЗАГРУЗКА" + ".".repeat(dotCount));
                splashText.postDelayed(this, 500);
            }
        }, 500);
    }
}