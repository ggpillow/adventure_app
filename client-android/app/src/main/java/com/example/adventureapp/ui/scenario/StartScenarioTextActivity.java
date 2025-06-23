package com.example.adventureapp.ui.scenario;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.adventureapp.R;
import com.example.adventureapp.data.cache.DataCache;
import com.example.adventureapp.data.model.ScenarioAudio;
import com.example.adventureapp.data.network.ApiConfig;
import com.example.adventureapp.ui.BaseActivity;
import com.example.adventureapp.utils.MusicManager;
import com.example.adventureapp.utils.PreferenceManager;

public class StartScenarioTextActivity extends BaseActivity {

    private long scenarioId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_scenario_text);

        TextView storyTitle = findViewById(R.id.storyTitle);
        TextView storyDescription = findViewById(R.id.storyDescription);
        Button buttonContinue = findViewById(R.id.buttonContinue);

        backButtonId = R.id.button_back;

        Intent intent = getIntent();
        storyTitle.setText(intent.getStringExtra("title"));
        storyDescription.setText(intent.getStringExtra("description"));
        scenarioId = intent.getLongExtra("scenarioId", -1);

        if (PreferenceManager.isSoundEnabled(this)) {
            playCachedScenarioAudio(scenarioId);
        }

        buttonContinue.setOnClickListener(v -> {
            MusicManager.stopVoice();
            Intent gameplayIntent = new Intent(this, StoryGameplayActivity.class);
            gameplayIntent.putExtra("scenarioId", scenarioId);
            startActivity(gameplayIntent);
        });
    }

    private void playCachedScenarioAudio(long scenarioId) {
        if (DataCache.audios != null) {
            for (ScenarioAudio audio : DataCache.audios) {
                if (audio.getScenarioId() == scenarioId) {
                    String rawUrl = audio.getAudioUrl();
                    if (rawUrl != null && !rawUrl.isEmpty()) {
                        if (rawUrl.startsWith("/")) rawUrl = rawUrl.substring(1);
                        String audioUrl = ApiConfig.BASE_URL + rawUrl;
                        Log.d("AUDIO_CACHE", "Озвучка из кэша: " + audioUrl);
                        MusicManager.playVoice(this, audioUrl);
                        return;
                    }
                }
            }
        }
        Log.w("AUDIO_CACHE", "Озвучка для сценария " + scenarioId + " не найдена в кэше");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MusicManager.stopVoice();
    }
}