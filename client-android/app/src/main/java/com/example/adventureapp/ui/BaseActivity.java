package com.example.adventureapp.ui;

import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.adventureapp.R;

public abstract class BaseActivity extends AppCompatActivity {

    protected int backButtonId = R.id.button_back;

    // Тайминг защиты от двойных нажатий
    private long lastClickTime = 0;
    private static final long CLICK_DELAY_MS = 800; // 0.8 сек

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        View backButton = findViewById(backButtonId);
        if (backButton instanceof Button) {
            backButton.setOnClickListener(v -> {
                if (isClickAllowed()) {
                    finish();
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                }
            });
        }
    }

    /**
     * Проверяет, прошло ли достаточно времени с предыдущего клика,
     * чтобы считать текущее нажатие допустимым.
     *
     * @return true — если нажатие допустимо, false — если нажато слишком быстро повторно
     */
    protected boolean isClickAllowed() {
        long currentTime = SystemClock.elapsedRealtime();
        if (currentTime - lastClickTime > CLICK_DELAY_MS) {
            lastClickTime = currentTime;
            return true;
        }
        return false;
    }
}