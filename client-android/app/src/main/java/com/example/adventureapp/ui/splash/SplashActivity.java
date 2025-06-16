package com.example.adventureapp.ui.splash;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.Gravity;
import android.view.animation.AnimationUtils;
import android.widget.TextSwitcher;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;

import com.example.adventureapp.R;
import com.example.adventureapp.ui.main.MainActivity;

public class SplashActivity extends AppCompatActivity {

    private TextSwitcher splashTextSwitcher;
    private final Handler handler = new Handler(Looper.getMainLooper());
    private int dotCount = 0;
    private boolean isRunning = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        splashTextSwitcher = findViewById(R.id.splashTextSwitcher);

        splashTextSwitcher.setFactory(() -> {
            TextView textView = new TextView(SplashActivity.this);
            textView.setTextSize(45f); // как в MainActivity
            textView.setTextColor(ContextCompat.getColor(SplashActivity.this, android.R.color.white));
            textView.setGravity(Gravity.CENTER);
            textView.setTypeface(ResourcesCompat.getFont(SplashActivity.this, R.font.panton_rust_heavy_grsh));
            return textView;
        });

        splashTextSwitcher.setInAnimation(AnimationUtils.loadAnimation(this, android.R.anim.fade_in));
        splashTextSwitcher.setOutAnimation(AnimationUtils.loadAnimation(this, android.R.anim.fade_out));

        startDotAnimation();

        handler.postDelayed(() -> {
            isRunning = false;
            startActivity(new Intent(SplashActivity.this, MainActivity.class));
            finish();
        }, 6000);
    }

    private void startDotAnimation() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!isRunning) return;

                dotCount = (dotCount + 1) % 4;
                String dots = new String(new char[dotCount]).replace("\0", ".");
                splashTextSwitcher.setText("ЗАГРУЗКА" + dots);

                handler.postDelayed(this, 500);
            }
        }, 500);
    }
}