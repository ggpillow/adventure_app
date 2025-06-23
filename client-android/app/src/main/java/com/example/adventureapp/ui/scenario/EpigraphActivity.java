package com.example.adventureapp.ui.scenario;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.adventureapp.R;
import com.example.adventureapp.data.cache.DataCache;
import com.example.adventureapp.data.model.Epigraph;
import com.example.adventureapp.data.network.ApiClient;
import com.example.adventureapp.data.network.EpigraphApi;

import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EpigraphActivity extends AppCompatActivity {

    private Handler handler = new Handler(Looper.getMainLooper());
    private Runnable goNextRunnable;
    private boolean hasNavigated = false;

    private TextView epigraphText;
    private TextView epigraphAuthor;
    private FrameLayout rootLayout;

    private long scenarioId;
    private String title;
    private String description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_epigraph);

        rootLayout = findViewById(R.id.rootLayout);
        epigraphText = findViewById(R.id.epigraph_text);
        epigraphAuthor = findViewById(R.id.epigraph_author);

        scenarioId = getIntent().getLongExtra("scenarioId", -1);
        title = getIntent().getStringExtra("title");
        description = getIntent().getStringExtra("description");

        loadEpigraph();

        goNextRunnable = () -> {
            if (!hasNavigated) navigateToNextScreen();
        };
        handler.postDelayed(goNextRunnable, 5000);

        rootLayout.setOnClickListener(v -> {
            if (!hasNavigated) {
                handler.removeCallbacks(goNextRunnable);
                navigateToNextScreen();
            }
        });
    }

    private void loadEpigraph() {
        List<Epigraph> cached = DataCache.epigraphs;
        if (cached != null && !cached.isEmpty()) {
            Epigraph epigraph = cached.get(new Random().nextInt(cached.size()));
            showEpigraph(epigraph);
        } else {
            // fallback
            EpigraphApi api = ApiClient.getClient().create(EpigraphApi.class);
            api.getRandomEpigraph().enqueue(new Callback<Epigraph>() {
                @Override
                public void onResponse(Call<Epigraph> call, Response<Epigraph> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        showEpigraph(response.body());
                    } else {
                        showError("Не удалось загрузить эпиграф");
                    }
                }

                @Override
                public void onFailure(Call<Epigraph> call, Throwable t) {
                    showError("Ошибка загрузки эпиграфа: " + t.getMessage());
                }
            });
        }
    }

    private void showEpigraph(Epigraph epigraph) {
        epigraphText.setAlpha(0f);
        epigraphAuthor.setAlpha(0f);

        epigraphText.setText(epigraph.getQuote());
        epigraphAuthor.setText("— " + epigraph.getAuthor());

        epigraphText.animate().alpha(1f).setDuration(1000).start();
        epigraphAuthor.animate().alpha(1f).setDuration(1000).start();
    }

    private void navigateToNextScreen() {
        hasNavigated = true;
        Intent intent = new Intent(this, StartScenarioTextActivity.class);
        intent.putExtra("scenarioId", scenarioId);
        intent.putExtra("title", title);
        intent.putExtra("description", description);
        startActivity(intent);
        finish();
    }

    private void showError(String message) {
        epigraphText.setText("Ошибка");
        epigraphAuthor.setText("");
        Toast.makeText(EpigraphActivity.this, message, Toast.LENGTH_SHORT).show();
    }
}