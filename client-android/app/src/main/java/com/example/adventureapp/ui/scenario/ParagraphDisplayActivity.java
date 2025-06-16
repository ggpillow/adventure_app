package com.example.adventureapp.ui.scenario;

import android.content.Intent;
import android.os.Bundle;
import android.widget.*;
import androidx.core.content.ContextCompat;

import com.example.adventureapp.R;
import com.example.adventureapp.data.model.Effect;
import com.example.adventureapp.data.network.ApiClient;
import com.example.adventureapp.data.network.EffectApi;
import com.example.adventureapp.ui.BaseActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ParagraphDisplayActivity extends BaseActivity {

    private TextView paragraphTextView;
    private TextView effectTextView;
    private EditText paragraphInput;
    private Button buttonOk, buttonMemo, buttonBack;
    private long scenarioId;
    private boolean isLoading = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paragraph_display);

        paragraphTextView = findViewById(R.id.paragraphTextView);
        effectTextView = findViewById(R.id.effectTextView);
        paragraphInput = findViewById(R.id.paragraphInput);
        buttonOk = findViewById(R.id.buttonOk);
        buttonBack = findViewById(R.id.buttonBack);
        buttonMemo = findViewById(R.id.buttonMemo);

        // Назначение ID кнопки "Назад" для BaseActivity
        backButtonId = R.id.buttonBack;

        // Получаем данные из Intent
        Intent intent = getIntent();
        String paragraphText = intent.getStringExtra("paragraphText");
        long effectId = intent.getLongExtra("effectId", -1);
        scenarioId = intent.getLongExtra("scenarioId", -1);

        if (paragraphText != null) {
            paragraphTextView.setText(paragraphText);
        }

        if (effectId != -1) {
            loadEffect(effectId);
        } else {
            effectTextView.setText("Эффект не указан");
            effectTextView.setTextColor(ContextCompat.getColor(this, R.color.gray));
        }

        // Повторная загрузка абзаца по номеру
        buttonOk.setOnClickListener(v -> {
            if (isLoading) return;

            String input = paragraphInput.getText().toString().trim();
            if (input.isEmpty()) {
                Toast.makeText(this, "Введите номер абзаца", Toast.LENGTH_SHORT).show();
                return;
            }

            int number;
            try {
                number = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Неверный номер", Toast.LENGTH_SHORT).show();
                return;
            }

            isLoading = true;
            loadParagraph(number);
        });

        buttonMemo.setOnClickListener(v -> {
            startActivity(new Intent(this, MemoActivity.class));
        });
    }

    private void loadParagraph(int number) {
        ApiClient.getClient().create(com.example.adventureapp.data.network.ParagraphApi.class)
                .getParagraphByNumber(number)
                .enqueue(new Callback<com.example.adventureapp.data.model.Paragraph>() {
                    @Override
                    public void onResponse(Call<com.example.adventureapp.data.model.Paragraph> call, Response<com.example.adventureapp.data.model.Paragraph> response) {
                        isLoading = false;

                        if (response.isSuccessful() && response.body() != null) {
                            com.example.adventureapp.data.model.Paragraph paragraph = response.body();
                            paragraphTextView.setText(paragraph.getParagraphDescr());
                            Long effectId = paragraph.getEffectId();
                            if (effectId != null) {
                                loadEffect(effectId);
                            } else {
                                effectTextView.setText("Эффект не указан");
                                effectTextView.setTextColor(ContextCompat.getColor(ParagraphDisplayActivity.this, R.color.gray));
                            }
                        } else {
                            Toast.makeText(ParagraphDisplayActivity.this, "Абзац не найден", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<com.example.adventureapp.data.model.Paragraph> call, Throwable t) {
                        isLoading = false;
                        Toast.makeText(ParagraphDisplayActivity.this, "Ошибка загрузки", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void loadEffect(Long effectId) {
        ApiClient.getClient().create(EffectApi.class)
                .getEffectById(effectId)
                .enqueue(new Callback<Effect>() {
                    @Override
                    public void onResponse(Call<Effect> call, Response<Effect> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            Effect effect = response.body();
                            effectTextView.setText(effect.getDescription());

                            int color;
                            switch (effect.getEffectType()) {
                                case "positive":
                                    color = R.color.effect_positive;
                                    break;
                                case "negative":
                                    color = R.color.effect_negative;
                                    break;
                                default:
                                    color = R.color.effect_neutral;
                                    break;
                            }
                            effectTextView.setTextColor(ContextCompat.getColor(ParagraphDisplayActivity.this, color));
                        } else {
                            effectTextView.setText("Эффект не найден");
                            effectTextView.setTextColor(ContextCompat.getColor(ParagraphDisplayActivity.this, R.color.gray));
                        }
                    }

                    @Override
                    public void onFailure(Call<Effect> call, Throwable t) {
                        effectTextView.setText("Ошибка загрузки эффекта");
                        effectTextView.setTextColor(ContextCompat.getColor(ParagraphDisplayActivity.this, R.color.gray));
                    }
                });
    }
}