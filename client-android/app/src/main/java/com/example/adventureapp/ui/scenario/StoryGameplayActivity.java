package com.example.adventureapp.ui.scenario;

import android.content.Intent;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import com.example.adventureapp.R;
import com.example.adventureapp.data.model.Paragraph;
import com.example.adventureapp.data.network.ApiClient;
import com.example.adventureapp.data.network.ParagraphApi;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StoryGameplayActivity extends AppCompatActivity {

    private EditText inputParagraphId;
    private Button buttonOk, buttonMemo, buttonCreateItem, buttonEndAdventure;
    private long scenarioId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story_gameplay);

        inputParagraphId = findViewById(R.id.paragraphInput);
        buttonOk = findViewById(R.id.buttonOk);
        buttonMemo = findViewById(R.id.buttonMemo);
        buttonCreateItem = findViewById(R.id.buttonCreateItem);
        buttonEndAdventure = findViewById(R.id.buttonEndAdventure);

        scenarioId = getIntent().getLongExtra("scenarioId", -1); // получаем ID

        buttonOk.setOnClickListener(v -> {
            String input = inputParagraphId.getText().toString().trim();
            if (input.isEmpty()) {
                Toast.makeText(this, "Введите номер абзаца", Toast.LENGTH_SHORT).show();
                return;
            }

            long paragraphId;
            try {
                paragraphId = Long.parseLong(input);
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Неверный формат номера", Toast.LENGTH_SHORT).show();
                return;
            }

            loadParagraph(paragraphId);
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
            intent.putExtra("scenarioId", scenarioId); // передаём ID
            startActivity(intent);
        });
    }

    private void loadParagraph(long paragraphNumber) {
        ParagraphApi api = ApiClient.getClient().create(ParagraphApi.class);
        api.getParagraphByNumber((int) paragraphNumber).enqueue(new Callback<Paragraph>() {
            @Override
            public void onResponse(Call<Paragraph> call, Response<Paragraph> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Paragraph paragraph = response.body();

                    Intent intent = new Intent(StoryGameplayActivity.this, ParagraphDisplayActivity.class);
                    intent.putExtra("paragraphText", paragraph.getParagraphDescr());
                    intent.putExtra("paragraphNumber", paragraph.getParagraphNumber()); // 🔸 если нужно
                    intent.putExtra("effectId", paragraph.getEffectId());               // 🔸 обязательно
                    intent.putExtra("scenarioId", scenarioId);
                    startActivity(intent);
                } else {
                    Toast.makeText(StoryGameplayActivity.this, "Абзац не найден", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Paragraph> call, Throwable t) {
                Toast.makeText(StoryGameplayActivity.this, "Ошибка загрузки: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}