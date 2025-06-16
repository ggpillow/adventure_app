package com.example.adventureapp.ui.scenario;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.adventureapp.R;
import com.example.adventureapp.data.model.ScenarioAudio;
import com.example.adventureapp.data.network.ApiClient;
import com.example.adventureapp.data.network.ApiConfig;
import com.example.adventureapp.data.network.ScenarioAudioApi;
import com.example.adventureapp.ui.BaseActivity;
import com.example.adventureapp.utils.MusicManager;
import com.example.adventureapp.utils.PreferenceManager;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StartScenarioTextActivity extends BaseActivity {

    private long scenarioId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_scenario_text);

        TextView storyTitle = findViewById(R.id.storyTitle);
        TextView storyDescription = findViewById(R.id.storyDescription);
        Button buttonContinue = findViewById(R.id.buttonContinue);

        // Назначение кнопки назад для BaseActivity
        backButtonId = R.id.button_back;

        Intent intent = getIntent();
        storyTitle.setText(intent.getStringExtra("title"));
        storyDescription.setText(intent.getStringExtra("description"));
        scenarioId = intent.getLongExtra("scenarioId", -1);

        // Загружаем озвучку, если включен звук
        if (PreferenceManager.isSoundEnabled(this)) {
            loadScenarioAudio();
        }

        buttonContinue.setOnClickListener(v -> {
            MusicManager.stopVoice(); // Остановить озвучку перед переходом

            Intent gameplayIntent = new Intent(this, StoryGameplayActivity.class);
            gameplayIntent.putExtra("scenarioId", scenarioId);
            startActivity(gameplayIntent);
        });
    }

    private void loadScenarioAudio() {
        ScenarioAudioApi api = ApiClient.getClient().create(ScenarioAudioApi.class);
        api.getAudioByScenario(scenarioId).enqueue(new Callback<List<ScenarioAudio>>() {
            @Override
            public void onResponse(Call<List<ScenarioAudio>> call, Response<List<ScenarioAudio>> response) {
                if (response.isSuccessful() && response.body() != null && !response.body().isEmpty()) {
                    String rawUrl = response.body().get(0).getAudioUrl();
                    if (rawUrl != null && !rawUrl.isEmpty()) {
                        if (rawUrl.startsWith("/")) rawUrl = rawUrl.substring(1);
                        String audioUrl = ApiConfig.BASE_URL + rawUrl;
                        Log.d("AUDIO_URL", "Финальный URL для аудио: " + audioUrl);
                        MusicManager.playVoice(StartScenarioTextActivity.this, audioUrl);
                    } else {
                        Log.e("AUDIO_URL", "URL аудио не найден");
                    }
                } else {
                    Log.e("AUDIO_URL", "Ответ неуспешный или тело пустое");
                }
            }

            @Override
            public void onFailure(Call<List<ScenarioAudio>> call, Throwable t) {
                Toast.makeText(StartScenarioTextActivity.this, "Ошибка загрузки озвучки", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MusicManager.stopVoice(); // Гарантированная остановка при выходе
    }
}