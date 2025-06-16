package com.example.adventureapp.ui.scenario;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.adventureapp.R;
import com.example.adventureapp.data.network.ApiConfig;

public class CreatedItemActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_created_item);

        ImageView resultImage = findViewById(R.id.createdItemImage);
        Button buttonBack = findViewById(R.id.buttonBack);

        Intent intent = getIntent();
        String imagePath = intent.getStringExtra("imageItem"); // путь к изображению
        long scenarioId = intent.getLongExtra("scenarioId", -1);

        // Безопасная загрузка изображения
        if (imagePath != null && !imagePath.trim().isEmpty()) {
            String fullUrl = imagePath.startsWith("http") ? imagePath : ApiConfig.BASE_URL + imagePath;
            Glide.with(this)
                    .load(fullUrl)
                    .error(R.drawable.error_image)
                    .into(resultImage);
            Log.d("IMAGE_URL", "Загружаем изображение по пути: " + fullUrl);
        } else {
            resultImage.setImageResource(R.drawable.error_image);
        }

        buttonBack.setOnClickListener(v -> {
            Intent backIntent = new Intent(this, StoryGameplayActivity.class);
            backIntent.putExtra("scenarioId", scenarioId);
            startActivity(backIntent);
            finish();
        });
    }
}