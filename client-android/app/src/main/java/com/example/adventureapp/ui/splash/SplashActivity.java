package com.example.adventureapp.ui.splash;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.adventureapp.R;
import com.example.adventureapp.data.cache.DataCache;
import com.example.adventureapp.data.model.*;
import com.example.adventureapp.data.network.*;
import com.example.adventureapp.ui.main.MainActivity;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashActivity extends AppCompatActivity {

    private TextView splashText;
    private TextView tipsText;
    private ProgressBar progressBar;
    private final Handler handler = new Handler();
    private final AtomicInteger completedRequests = new AtomicInteger(0);
    private final int totalRequests = 6; // Было 5, теперь с концовками

    private final String[] tips = {
            "Работайте сообща — это увеличит ваши шансы.",
            "Обменивайтесь найденными вещами.",
            "Сохраняйте человечность в трудных ситуациях.",
            "Думайте, прежде чем действовать.",
            "У каждого выбора есть последствия."
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        splashText = findViewById(R.id.splashText);
        tipsText = findViewById(R.id.splashTips);
        progressBar = findViewById(R.id.splashProgress);

        tipsText.setText(tips[(int) (Math.random() * tips.length)]);

        loadScenarios();
        loadSchemes();
        loadEffects();
        loadResources();
        loadEpigraphs();
        loadEndings();
    }

    private void checkAllLoaded() {
        int progress = (int) ((completedRequests.incrementAndGet() / (float) totalRequests) * 100);
        progressBar.setProgress(progress);

        if (progress >= 100) {
            startActivity(new Intent(SplashActivity.this, MainActivity.class));
            finish();
        }
    }

    private void loadScenarios() {
        ScenarioApi api = ApiClient.getClient().create(ScenarioApi.class);
        api.getAllScenarios().enqueue(new Callback<List<Scenario>>() {
            public void onResponse(Call<List<Scenario>> call, Response<List<Scenario>> response) {
                if (response.isSuccessful()) {
                    DataCache.scenarios = response.body();
                }
                checkAllLoaded();
            }

            public void onFailure(Call<List<Scenario>> call, Throwable t) {
                checkAllLoaded();
            }
        });
    }

    private void loadSchemes() {
        SchemeApi api = ApiClient.getClient().create(SchemeApi.class);
        api.getAllSchemes().enqueue(new Callback<List<Scheme>>() {
            @Override
            public void onResponse(Call<List<Scheme>> call, Response<List<Scheme>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    DataCache.schemes = response.body();
                    Log.d("SplashSchemes", "BASE_URL: " + ApiConfig.BASE_URL);
                    Log.d("SplashSchemes", "Загружено схем: " + DataCache.schemes.size());
                    for (Scheme scheme : DataCache.schemes) {
                        String url = scheme.getImageSchemes();
                        Log.d("SplashSchemes", "Схема: id=" + scheme.getId() + ", path=" + url);
                        if (url == null || url.isEmpty()) continue;

                        String fullUrl = url.startsWith("http") ? url : ApiConfig.BASE_URL + url;
                        Glide.with(getApplicationContext()).load(fullUrl).preload();
                        Log.d("SplashSchemes", "Предзагрузка схемы: " + fullUrl);
                    }
                } else {
                    Log.w("SplashSchemes", "Не удалось загрузить схемы или список пуст");
                }
                checkAllLoaded();
            }

            @Override
            public void onFailure(Call<List<Scheme>> call, Throwable t) {
                Log.e("SplashSchemes", "Ошибка загрузки схем: " + t.getMessage(), t);
                checkAllLoaded();
            }
        });
    }
    private void loadEffects() {
        EffectApi api = ApiClient.getClient().create(EffectApi.class);
        api.getAllEffects().enqueue(new Callback<List<Effect>>() {
            public void onResponse(Call<List<Effect>> call, Response<List<Effect>> response) {
                if (response.isSuccessful()) {
                    DataCache.effects = response.body();
                }
                checkAllLoaded();
            }

            public void onFailure(Call<List<Effect>> call, Throwable t) {
                checkAllLoaded();
            }
        });
    }

    private void loadResources() {
        ResourceApi api = ApiClient.getClient().create(ResourceApi.class);
        api.getAllResources().enqueue(new Callback<List<Resource>>() {
            public void onResponse(Call<List<Resource>> call, Response<List<Resource>> response) {
                if (response.isSuccessful()) {
                    DataCache.resources = response.body();
                }
                checkAllLoaded();
            }

            public void onFailure(Call<List<Resource>> call, Throwable t) {
                checkAllLoaded();
            }
        });
    }

    private void loadEpigraphs() {
        EpigraphApi api = ApiClient.getClient().create(EpigraphApi.class);
        api.getAllEpigraphs().enqueue(new Callback<List<Epigraph>>() {
            public void onResponse(Call<List<Epigraph>> call, Response<List<Epigraph>> response) {
                if (response.isSuccessful()) {
                    DataCache.epigraphs = response.body();
                }
                checkAllLoaded();
            }

            public void onFailure(Call<List<Epigraph>> call, Throwable t) {
                checkAllLoaded();
            }
        });
    }

    private void loadEndings() {
        EndingApi api = ApiClient.getClient().create(EndingApi.class);
        api.getAllEndings().enqueue(new Callback<List<Ending>>() {
            @Override
            public void onResponse(Call<List<Ending>> call, Response<List<Ending>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    DataCache.endings = response.body();
                    Log.d("SplashEndings", "Загружено концовок: " + DataCache.endings.size());
                    for (Ending e : DataCache.endings) {
                        Log.d("SplashEndings", "ID=" + e.getId()
                                + ", title=" + e.getTitleEnding()
                                + ", scenarioId=" + e.getScenarioId());
                    }
                } else {
                    Log.w("SplashEndings", "Ответ успешен, но тело пустое или null");
                }
                checkAllLoaded();
            }

            @Override
            public void onFailure(Call<List<Ending>> call, Throwable t) {
                Log.e("SplashEndings", "Ошибка загрузки концовок: " + t.getMessage(), t);
                checkAllLoaded();
            }
        });
    }
}