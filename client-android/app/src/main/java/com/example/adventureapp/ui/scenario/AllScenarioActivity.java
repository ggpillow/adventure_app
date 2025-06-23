package com.example.adventureapp.ui.scenario;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;

import com.example.adventureapp.data.cache.DataCache;
import com.example.adventureapp.data.model.Scenario;
import com.example.adventureapp.databinding.ActivityAllScenarioBinding;
import com.example.adventureapp.ui.BaseActivity;
import com.example.adventureapp.ui.adapters.ScenarioAdapter;

import java.util.ArrayList;
import java.util.List;

public class AllScenarioActivity extends BaseActivity {

    private ActivityAllScenarioBinding binding;
    private ScenarioAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAllScenarioBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        backButtonId = binding.buttonBack.getId();

        setupRecyclerView();
        displayCachedScenarios();
    }

    private void setupRecyclerView() {
        adapter = new ScenarioAdapter(this, new ArrayList<>());
        binding.scenarioRecyclerView.setLayoutManager(
                new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        );
        binding.scenarioRecyclerView.setAdapter(adapter);

        LinearSnapHelper snapHelper = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(binding.scenarioRecyclerView);
        adapter.attachToRecyclerView(binding.scenarioRecyclerView);

        adapter.setOnScenarioClickListener(scenario -> {
            Intent intent = new Intent(this, PreparationActivity.class);
            intent.putExtra("scenarioId", scenario.getId());
            intent.putExtra("title", scenario.getTitle());
            intent.putExtra("description", scenario.getStartDescr());
            intent.putExtra("difficulty", scenario.getDifficulty());
            intent.putExtra("image", scenario.getImageURL());
            startActivity(intent);
        });
    }

    private void displayCachedScenarios() {
        List<Scenario> cachedScenarios = DataCache.scenarios;

        if (cachedScenarios == null || cachedScenarios.isEmpty()) {
            Toast.makeText(this, "Нет данных сценариев. Попробуйте перезапустить приложение.", Toast.LENGTH_LONG).show();
            Log.e("SCENARIOS", "DataCache.scenarios пуст");
        } else {
            adapter.setScenarios(cachedScenarios);
            Log.d("SCENARIOS", "Сценариев из кэша: " + cachedScenarios.size());
        }
    }
}