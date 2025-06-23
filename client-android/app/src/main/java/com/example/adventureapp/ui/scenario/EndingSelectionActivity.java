package com.example.adventureapp.ui.scenario;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.adventureapp.R;
import com.example.adventureapp.data.model.Ending;
import com.example.adventureapp.data.network.ApiClient;
import com.example.adventureapp.data.network.EndingApi;
import com.example.adventureapp.ui.BaseActivity;
import com.example.adventureapp.ui.main.MainActivity;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EndingSelectionActivity extends BaseActivity {

    private TextView titleTextView;
    private TextView descriptionTextView;
    private Button menuButton;

    private long endingId;
    private long scenarioId;
    private String altQuestion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_ending);

        titleTextView = findViewById(R.id.textTitleEnding);
        descriptionTextView = findViewById(R.id.textEndingContent);
        menuButton = findViewById(R.id.buttonFinishAdventure);

        Intent intent = getIntent();
        endingId = intent.getLongExtra("endingId", -1);
        scenarioId = intent.getLongExtra("scenarioId", -1);
        altQuestion = intent.getStringExtra("altQuestion");

        if (endingId == -1 || scenarioId == -1) {
            Toast.makeText(this, "Ошибка загрузки данных концовки", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        loadEnding();

        menuButton.setOnClickListener(v -> {
            if (!isClickAllowed()) return;
            Intent backToMain = new Intent(this, MainActivity.class);
            backToMain.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(backToMain);
            finish();
        });
    }

    private void loadEnding() {
        EndingApi api = ApiClient.getClient().create(EndingApi.class);
        api.getEndingsByScenario(scenarioId).enqueue(new Callback<List<Ending>>() {
            @Override
            public void onResponse(Call<List<Ending>> call, Response<List<Ending>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    for (Ending e : response.body()) {
                        if (e.getId() == endingId) {
                            String displayTitle = e.getTitleEnding();
                            if ("Альтернативная концовка".equalsIgnoreCase(displayTitle)
                                    && altQuestion != null && !altQuestion.trim().isEmpty()) {
                                displayTitle = altQuestion.trim();
                                if (displayTitle.length() > 200) {
                                    displayTitle = displayTitle.substring(0, 200) + "...";
                                }
                                titleTextView.setTextColor(getResources().getColor(R.color.light_red));
                            }

                            Log.d("EndingDebug", "Концовка найдена: ID=" + endingId + ", Заголовок=" + displayTitle);

                            titleTextView.setText(displayTitle);

                            String rawDescr = e.getEndDescr();
                            if (rawDescr == null) rawDescr = "";
                            String formattedDescr = rawDescr.replace("<br><br>", "\n\n");
                            descriptionTextView.setAlpha(0f);
                            descriptionTextView.setText(formattedDescr);
                            descriptionTextView.animate()
                                    .alpha(1f)
                                    .setDuration(1200)
                                    .setStartDelay(200)
                                    .start();
                            return;
                        }
                    }
                    Toast.makeText(EndingSelectionActivity.this, "Концовка не найдена", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(EndingSelectionActivity.this, "Ошибка загрузки концовок", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Ending>> call, Throwable t) {
                Toast.makeText(EndingSelectionActivity.this, "Ошибка подключения: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}