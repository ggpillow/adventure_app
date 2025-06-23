package com.example.adventureapp.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.adventureapp.R;
import com.example.adventureapp.databinding.ActivityMainBinding;
import com.example.adventureapp.ui.BaseActivity;
import com.example.adventureapp.ui.scenario.AllScenarioActivity;
import com.example.adventureapp.ui.settings.SettingActivity;
import com.example.adventureapp.utils.MusicManager;

public class MainActivity extends BaseActivity {


    private static final String TAG = "MainActivity";
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "MainActivity started");

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //MusicManager.start(this); // Воспроизводим музыку, если включена в настройках

        setupListeners();
    }

    private void setupListeners() {
        binding.buttonStart.setOnClickListener(v -> startGame());
        binding.buttonSettings.setOnClickListener(v -> openSettings());
        binding.buttonExit.setOnClickListener(v -> exitApp());
    }

    private void startGame() {
        Log.d(TAG, "Start button clicked");
        startActivity(new Intent(this, AllScenarioActivity.class));
    }

    private void openSettings() {
        Log.d(TAG, "Settings button clicked");
        startActivity(new Intent(this, SettingActivity.class));
    }

    private void exitApp() {
        Log.d(TAG, "Exit button clicked");
        MusicManager.forceStop(); // Принудительно останавливаем музыку
        finishAffinity(); // Закрываем все активити
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Не останавливаем музыку здесь — она управляется централизованно
    }
}