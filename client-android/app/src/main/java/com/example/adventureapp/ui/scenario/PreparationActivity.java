package com.example.adventureapp.ui.scenario;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.adventureapp.R;
import com.example.adventureapp.data.cache.DataCache;
import com.example.adventureapp.data.model.Scheme;
import com.example.adventureapp.data.network.ApiConfig;
import com.example.adventureapp.databinding.ActivityPreparationBinding;
import com.example.adventureapp.ui.BaseActivity;
import com.example.adventureapp.utils.MusicManager;

import java.util.List;

public class PreparationActivity extends BaseActivity {

    private ActivityPreparationBinding binding;

    private long scenarioId;
    private String title;
    private String description;
    private String schemeUrl;
    private String audioUrl;

    private boolean isNavigating = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPreparationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        backButtonId = binding.backButton.getId();

        getExtrasFromIntent();
        setContent();

        if (audioUrl != null && !audioUrl.isEmpty()) {
            MusicManager.playVoice(this, audioUrl);
        }

        binding.startButton.setOnClickListener(v -> {
            if (isNavigating) return;
            isNavigating = true;
            MusicManager.stopVoice();
            Intent intent = new Intent(this, EpigraphActivity.class);
            intent.putExtra("scenarioId", scenarioId);
            intent.putExtra("title", title);
            intent.putExtra("description", description);
            startActivity(intent);
            finish();
        });

        loadSchemeFromCache(scenarioId);
    }

    private void getExtrasFromIntent() {
        Intent intent = getIntent();
        scenarioId = intent.getLongExtra("scenarioId", -1);
        title = intent.getStringExtra("title");
        description = intent.getStringExtra("description");
        schemeUrl = intent.getStringExtra("image");
        audioUrl = intent.getStringExtra("audioUrl");
    }

    private void setContent() {
        binding.titleText.setText(title);

        // Загружаем карточки и тайлы через BASE_URL
        String base = ApiConfig.BASE_URL;
        loadImage(base + "images/components/cards/imageTails.jpg", binding.tilesImage);
        loadImage(base + "images/components/cards/imageCards.jpg", binding.cardsImage);
    }

    private void loadImage(String url, android.widget.ImageView imageView) {
        Glide.with(this)
                .load(url)
                .error(R.drawable.error_image)
                .into(imageView);
    }

    private void loadSchemeFromCache(long scenarioId) {
        List<Scheme> allSchemes = DataCache.schemes;

        Log.d("PreparationActivity", "Поиск схемы для сценария ID=" + scenarioId);

        if (allSchemes != null && !allSchemes.isEmpty()) {
            Log.d("PreparationActivity", "Размер кэша схем: " + allSchemes.size());

            for (Scheme scheme : allSchemes) {
                Long schemeScenarioId = scheme.getScenarioId();
                String path = scheme.getImageSchemes();

                Log.d("PreparationActivity", "Кандидат: id=" + scheme.getId()
                        + ", scenarioId=" + schemeScenarioId
                        + ", path=" + path);

                if (schemeScenarioId != null && schemeScenarioId.equals(scenarioId) && path != null && !path.trim().isEmpty()) {
                    String fullUrl = path.startsWith("http") ? path : ApiConfig.BASE_URL + path;

                    Glide.with(this)
                            .load(fullUrl)
                            .error(R.drawable.error_image)
                            .into(binding.fieldImage);

                    Log.d("PreparationActivity", "Схема найдена и загружена: " + fullUrl);
                    return;
                }
            }

            Log.w("PreparationActivity", "Схема для сценария ID=" + scenarioId + " не найдена.");
            Toast.makeText(this, "Схема не найдена для выбранного сценария", Toast.LENGTH_SHORT).show();
        } else {
            Log.e("PreparationActivity", "Кэш схем пуст");
            Toast.makeText(this, "Схемы не загружены. Перезапустите приложение.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MusicManager.stopVoice();
    }
}