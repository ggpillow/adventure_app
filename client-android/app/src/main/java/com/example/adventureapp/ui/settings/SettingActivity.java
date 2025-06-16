package com.example.adventureapp.ui.settings;

import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Toast;

import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.adventureapp.databinding.ActivitySettingBinding;
import com.example.adventureapp.ui.BaseActivity;
import com.example.adventureapp.utils.MusicManager;
import com.example.adventureapp.utils.PreferenceManager;

public class SettingActivity extends BaseActivity {

    private ActivitySettingBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySettingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Назначение ID кнопки назад для BaseActivity
        backButtonId = binding.buttonBack.getId();

        // Установка начальных состояний переключателей
        binding.switchMusic.setChecked(PreferenceManager.isMusicEnabled(this));
        binding.switchVoice.setChecked(PreferenceManager.isSoundEnabled(this));

        // Слушатель переключателя музыки
        binding.switchMusic.setOnCheckedChangeListener((CompoundButton buttonView, boolean isChecked) -> {
            PreferenceManager.setMusicEnabled(this, isChecked);
            if (isChecked) {
                MusicManager.start(this);
                Toast.makeText(this, "Музыка включена", Toast.LENGTH_SHORT).show();
            } else {
                MusicManager.stopWithFadeOut();
                Toast.makeText(this, "Музыка выключена", Toast.LENGTH_SHORT).show();
            }
        });

        // Слушатель переключателя озвучки
        binding.switchVoice.setOnCheckedChangeListener((CompoundButton buttonView, boolean isChecked) -> {
            PreferenceManager.setSoundEnabled(this, isChecked);
            Toast.makeText(this,
                    isChecked ? "Голос включён" : "Голос выключен",
                    Toast.LENGTH_SHORT
            ).show();
        });

        // Обработка отступов от системных панелей
        ViewCompat.setOnApplyWindowInsetsListener(binding.getRoot(), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}