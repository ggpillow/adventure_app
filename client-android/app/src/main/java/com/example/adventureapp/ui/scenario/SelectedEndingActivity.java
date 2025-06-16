package com.example.adventureapp.ui.scenario;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.example.adventureapp.R;
import com.example.adventureapp.ui.main.MainActivity;

public class SelectedEndingActivity extends AppCompatActivity {

    private TextView titleTextView, contentTextView;
    private Button finishButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_ending);

        titleTextView = findViewById(R.id.textTitleEnding);
        contentTextView = findViewById(R.id.textEndingContent);
        finishButton = findViewById(R.id.buttonFinishAdventure);

        // Получаем данные из Intent
        String title = getIntent().getStringExtra("ending_title");
        String content = getIntent().getStringExtra("ending_text");

        titleTextView.setText(title != null ? title : "Неизвестная концовка");
        contentTextView.setText(content != null ? content : "Описание недоступно.");

        finishButton.setOnClickListener(v -> {
            // Возврат на главный экран
            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        });
    }
}
