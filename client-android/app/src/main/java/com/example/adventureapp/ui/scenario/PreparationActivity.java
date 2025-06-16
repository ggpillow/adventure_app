package com.example.adventureapp.ui.scenario;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.adventureapp.data.model.Scheme;
import com.example.adventureapp.data.network.ApiClient;
import com.example.adventureapp.data.network.ApiConfig;
import com.example.adventureapp.data.network.SchemeApi;
import com.example.adventureapp.databinding.ActivityPreparationBinding;
import com.example.adventureapp.ui.BaseActivity;
import com.example.adventureapp.utils.MusicManager;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PreparationActivity extends BaseActivity {

    private ActivityPreparationBinding binding;

    private long scenarioId;
    private String title;
    private String description;
    private String schemeUrl;
    private String audioUrl;

    private boolean schemeLoaded = false;
    private boolean isNavigating = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPreparationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Назначаем ID кнопки "Назад" для BaseActivity
        backButtonId = binding.backButton.getId();

        getExtrasFromIntent();
        setContent();

        if (audioUrl != null && !audioUrl.isEmpty()) {
            MusicManager.playVoice(this, audioUrl);
        }

        binding.startButton.setOnClickListener(v -> {
            if (isNavigating) return;
            isNavigating = true;

            MusicManager.stopVoice(); // останавливаем озвучку
            Intent intent = new Intent(this, EpigraphActivity.class);
            intent.putExtra("scenarioId", scenarioId);
            intent.putExtra("title", title);
            intent.putExtra("description", description);
            startActivity(intent);
            finish();
        });

        loadSchemeForScenario(scenarioId);
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

        // Загружаем вспомогательные изображения
        loadImage(ApiConfig.BASE_URL + "images/components/cards/imageTails.jpg", binding.tilesImage);
        loadImage(ApiConfig.BASE_URL + "images/components/cards/imageCards.jpg", binding.cardsImage);
    }

    private void loadImage(String url, android.widget.ImageView imageView) {
        String fullUrl = url.startsWith("http") ? url : ApiConfig.BASE_URL + url;
        Log.d("IMAGE_LOAD", "Loading image from URL: " + fullUrl);

        Glide.with(this)
                .load(fullUrl)
                .into(imageView);
    }

    private void loadSchemeForScenario(long scenarioId) {
        if (schemeLoaded) return;

        SchemeApi api = ApiClient.getClient().create(SchemeApi.class);
        api.getSchemesByScenario(scenarioId).enqueue(new Callback<List<Scheme>>() {
            @Override
            public void onResponse(Call<List<Scheme>> call, Response<List<Scheme>> response) {
                if (response.isSuccessful() && response.body() != null && !response.body().isEmpty()) {
                    String url = response.body().get(0).getImageSchemes();
                    Log.d("PreparationActivity", "Схема: " + url);
                    schemeLoaded = true;
                    loadImage(url, binding.fieldImage);
                } else {
                    Toast.makeText(PreparationActivity.this, "Ошибка загрузки схемы", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<List<Scheme>> call, Throwable t) {
                Log.e("PreparationActivity", "Ошибка загрузки схемы: " + t.getMessage());
                Toast.makeText(PreparationActivity.this, "Не удалось загрузить схему", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MusicManager.stopVoice(); // на случай, если пользователь просто закрыл экран
    }
}