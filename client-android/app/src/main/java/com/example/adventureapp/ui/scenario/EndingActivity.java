package com.example.adventureapp.ui.scenario;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.adventureapp.R;
import com.example.adventureapp.data.cache.DataCache;
import com.example.adventureapp.data.model.Ending;
import com.example.adventureapp.data.network.ApiClient;
import com.example.adventureapp.data.network.EndingApi;
import com.example.adventureapp.ui.BaseActivity;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EndingActivity extends BaseActivity {

    private long scenarioId;

    private Button btnAllSurvived;
    private Button btnSomeoneDied;
    private Button btnAlone;
    private Button btnAltEnding;
    private Button btnBack;

    private Ending endingBest, endingSad, endingAlone, endingAlt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ending);

        scenarioId = getIntent().getLongExtra("scenarioId", -1);
        if (scenarioId == -1) {
            Toast.makeText(this, "Ошибка: сценарий не передан", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        btnAllSurvived = findViewById(R.id.buttonAllSurvived);
        btnSomeoneDied = findViewById(R.id.buttonSomeoneDied);
        btnAlone = findViewById(R.id.buttonAlone);
        btnAltEnding = findViewById(R.id.buttonAltEnding);
        btnBack = findViewById(R.id.buttonBack);

        btnBack.setOnClickListener(v -> {
            if (!isClickAllowed()) return;
            Intent intent = new Intent(this, StoryGameplayActivity.class);
            intent.putExtra("scenarioId", scenarioId);
            startActivity(intent);
            finish();
        });

        btnAllSurvived.setText("Спаслись все");
        btnSomeoneDied.setText("Кто-то из нас пал");
        btnAlone.setText("Никто не выжил");
        btnAltEnding.setText("Альтернатива");

        loadEndingsFromCacheOrApi();
    }

    private void loadEndingsFromCacheOrApi() {
        List<Ending> all = DataCache.endings;
        if (all != null) {
            Log.d("EndingDebug", "Загружаем концовки из кэша");
            processEndings(all);
        } else {
            Log.d("EndingDebug", "Кэш пуст. Загружаем с сервера");
            loadEndingsFromApi();
        }
    }

    private void loadEndingsFromApi() {
        EndingApi api = ApiClient.getClient().create(EndingApi.class);
        api.getEndingsByScenario(scenarioId).enqueue(new Callback<List<Ending>>() {
            @Override
            public void onResponse(Call<List<Ending>> call, Response<List<Ending>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    processEndings(response.body());
                } else {
                    Toast.makeText(EndingActivity.this, "Ошибка загрузки концовок", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Ending>> call, Throwable t) {
                Toast.makeText(EndingActivity.this, "Ошибка подключения: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("EndingDebug", "Ошибка запроса концовок", t);
            }
        });
    }

    private void processEndings(List<Ending> list) {
        Log.d("EndingDebug", "Обработка концовок для сценария ID=" + scenarioId);

        for (Ending e : list) {
            Log.d("EndingDebug", "Кандидат: id=" + e.getId()
                    + ", title=" + e.getTitleEnding()
                    + ", scenarioId=" + e.getScenarioId());

            if (!scenarioIdEquals(e.getScenarioId())) continue;

            String title = e.getTitleEnding() != null ? e.getTitleEnding().trim() : "";
            String alt = e.getAltQuestion();

            if (title.equalsIgnoreCase("Лучший исход")) {
                endingBest = e;
            } else if (title.equalsIgnoreCase("Тяжелая утрата")) {
                endingSad = e;
            } else if (title.equalsIgnoreCase("Безысходность")) {
                endingAlone = e;
            } else if (alt != null && !alt.trim().isEmpty()) {
                endingAlt = e;
            }
        }

        Log.d("EndingDebug", "endingAlone is " + (endingAlone == null ? "NULL" : "NOT NULL"));
        setupButtonActions();
    }

    private boolean scenarioIdEquals(Long id) {
        return id != null && id.equals(scenarioId);
    }

    private void setupButtonActions() {
        if (endingBest != null) {
            btnAllSurvived.setOnClickListener(v -> {
                if (!isClickAllowed()) return;
                openEndingSelection(endingBest.getId(), null);
            });
        } else {
            btnAllSurvived.setEnabled(false);
        }

        if (endingSad != null) {
            btnSomeoneDied.setOnClickListener(v -> {
                if (!isClickAllowed()) return;
                openEndingSelection(endingSad.getId(), null);
            });
        } else {
            btnSomeoneDied.setEnabled(false);
        }

        Log.d("EndingDebug", "endingAlone is " + (endingAlone == null ? "NULL" : "NOT NULL"));

        if (endingAlone != null) {
            btnAlone.setOnClickListener(v -> {
                if (!isClickAllowed()) return;
                openEndingSelection(endingAlone.getId(), null);
            });
        } else {
            btnAlone.setEnabled(false);
        }

        if (endingAlt != null) {
            btnAltEnding.setOnClickListener(v -> {
                if (!isClickAllowed()) return;
                String question = endingAlt.getAltQuestion();
                if (question != null && question.length() > 500) {
                    question = question.substring(0, 500);
                }
                showCustomAltDialog(question, endingAlt.getId());
            });
        } else {
            btnAltEnding.setEnabled(false);
        }
    }

    private void showCustomAltDialog(String question, long endingId) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.AlertDialogCustom);
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_alt_ending, null);
        builder.setView(dialogView);
        AlertDialog dialog = builder.create();

        TextView textQuestion = dialogView.findViewById(R.id.textAltQuestion);
        Button buttonYes = dialogView.findViewById(R.id.buttonAltYes);
        Button buttonNo = dialogView.findViewById(R.id.buttonAltNo);

        String safeText = (question != null && !question.trim().isEmpty())
                ? question.trim() : "Вы действительно хотите выбрать альтернативную концовку?";
        if (safeText.length() > 200) {
            safeText = safeText.substring(0, 200) + "...";
        }
        textQuestion.setText(safeText);

        String finalSafeText = safeText;
        buttonYes.setOnClickListener(v -> {
            dialog.dismiss();
            Log.d("EndingDebug", "Подтверждена альт. концовка ID=" + endingId);
            openEndingSelection(endingId, finalSafeText);
        });

        buttonNo.setOnClickListener(v -> dialog.dismiss());

        dialog.show();
    }

    private void openEndingSelection(long endingId, String altQuestion) {
        Intent intent = new Intent(this, EndingSelectionActivity.class);
        intent.putExtra("scenarioId", scenarioId);
        intent.putExtra("endingId", endingId);
        if (altQuestion != null && !altQuestion.trim().isEmpty()) {
            String trimmed = altQuestion.trim();
            if (trimmed.length() > 500) trimmed = trimmed.substring(0, 500);
            intent.putExtra("altQuestion", trimmed);
        }
        Log.d("EndingDebug", "Переход к EndingSelectionActivity: id=" + endingId + ", alt=" + altQuestion);
        startActivity(intent);
    }
}