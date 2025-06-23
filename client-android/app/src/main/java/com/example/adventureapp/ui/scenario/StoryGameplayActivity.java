package com.example.adventureapp.ui.scenario;

import android.content.Intent;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

import com.example.adventureapp.R;
import com.example.adventureapp.data.model.Paragraph;
import com.example.adventureapp.data.network.ApiClient;
import com.example.adventureapp.data.network.ParagraphApi;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StoryGameplayActivity extends AppCompatActivity {

    private EditText inputParagraphId;
    private Button buttonOk, buttonMemo, buttonCreateItem, buttonEndAdventure;
    private long scenarioId;

    private final Map<Integer, Paragraph> paragraphCache = new HashMap<>();
    private boolean isRequestInProgress = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story_gameplay);

        inputParagraphId = findViewById(R.id.paragraphInput);
        buttonOk = findViewById(R.id.buttonOk);
        buttonMemo = findViewById(R.id.buttonMemo);
        buttonCreateItem = findViewById(R.id.buttonCreateItem);
        buttonEndAdventure = findViewById(R.id.buttonEndAdventure);

        scenarioId = getIntent().getLongExtra("scenarioId", -1);

        buttonOk.setOnClickListener(v -> {
            if (isRequestInProgress) return;

            String input = inputParagraphId.getText().toString().trim();
            if (input.isEmpty()) {
                Toast.makeText(this, "Введите номер абзаца", Toast.LENGTH_SHORT).show();
                return;
            }

            int paragraphNumber;
            try {
                paragraphNumber = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Неверный формат номера", Toast.LENGTH_SHORT).show();
                return;
            }

            fetchOrLoadParagraph(paragraphNumber);
        });

        buttonMemo.setOnClickListener(v -> {
            Intent intent = new Intent(this, MemoActivity.class);
            intent.putExtra("source", "StoryGameplayActivity");
            startActivity(intent);
        });

        buttonCreateItem.setOnClickListener(v -> {
            Intent intent = new Intent(this, ItemCombinationsActivity.class);
            intent.putExtra("scenarioId", scenarioId);
            startActivity(intent);
        });

        buttonEndAdventure.setOnClickListener(v -> {
            Intent intent = new Intent(this, EndingActivity.class);
            intent.putExtra("scenarioId", scenarioId);
            startActivity(intent);
        });
    }

    private void fetchOrLoadParagraph(int number) {
        if (paragraphCache.containsKey(number)) {
            openParagraph(paragraphCache.get(number));
            return;
        }

        isRequestInProgress = true;
        buttonOk.setEnabled(false);

        ParagraphApi api = ApiClient.getClient().create(ParagraphApi.class);
        api.getParagraphByNumber(number).enqueue(new Callback<Paragraph>() {
            @Override
            public void onResponse(Call<Paragraph> call, Response<Paragraph> response) {
                isRequestInProgress = false;
                buttonOk.setEnabled(true);

                if (response.isSuccessful() && response.body() != null) {
                    Paragraph paragraph = response.body();
                    paragraphCache.put(number, paragraph);
                    openParagraph(paragraph);
                } else {
                    Toast.makeText(StoryGameplayActivity.this, "Абзац не найден", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Paragraph> call, Throwable t) {
                isRequestInProgress = false;
                buttonOk.setEnabled(true);
                Toast.makeText(StoryGameplayActivity.this, "Ошибка: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void openParagraph(Paragraph paragraph) {
        Intent intent = new Intent(this, ParagraphDisplayActivity.class);
        intent.putExtra("paragraphText", paragraph.getParagraphDescr());
        intent.putExtra("paragraphNumber", paragraph.getParagraphNumber());
        intent.putExtra("effectId", paragraph.getEffectId());
        intent.putExtra("scenarioId", scenarioId);
        startActivity(intent);
    }
}