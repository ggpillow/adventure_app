package com.example.adventureapp.ui.scenario;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.adventureapp.R;
import com.example.adventureapp.data.network.ApiConfig;

public class MemoActivity extends AppCompatActivity {

    private String sourceActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memo);

        ImageView memoImage = findViewById(R.id.memoImageView);
        Button buttonBack = findViewById(R.id.buttonBack);

        // Загружаем картинку
        String imageUrl = ApiConfig.BASE_URL + "images/memo/memo_table.jpg";
        Glide.with(this)
                .load(imageUrl)
                .error(R.drawable.error_image)
                .into(memoImage);

        // Получаем имя активности-источника
        sourceActivity = getIntent().getStringExtra("source");

        buttonBack.setOnClickListener(v -> {
            if ("StoryGameplayActivity".equals(sourceActivity)) {
                Intent intent = new Intent(this, StoryGameplayActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            } else if ("ParagraphDisplayActivity".equals(sourceActivity)) {
                Intent intent = new Intent(this, ParagraphDisplayActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            } else {
                finish(); // если неизвестен источник — просто закрыть
            }
        });
    }
}