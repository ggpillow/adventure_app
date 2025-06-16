package com.example.adventureapp.ui.scenario;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;

import com.example.adventureapp.data.model.Scenario;
import com.example.adventureapp.data.network.ApiClient;
import com.example.adventureapp.data.network.ScenarioApi;
import com.example.adventureapp.databinding.ActivityAllScenarioBinding;
import com.example.adventureapp.ui.BaseActivity;
import com.example.adventureapp.ui.adapters.ScenarioAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AllScenarioActivity extends BaseActivity {

    private ActivityAllScenarioBinding binding;
    private ScenarioAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAllScenarioBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Назначаем ID кнопки назад для BaseActivity
        backButtonId = binding.buttonBack.getId();

        setupRecyclerView();
        loadScenarios();
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

    private void loadScenarios() {
        ScenarioApi api = ApiClient.getClient().create(ScenarioApi.class);
        api.getAllScenarios().enqueue(new Callback<List<Scenario>>() {
            @Override
            public void onResponse(Call<List<Scenario>> call, Response<List<Scenario>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Scenario> scenarios = response.body();
                    if (scenarios.isEmpty()) {
                        Toast.makeText(AllScenarioActivity.this, "Сценарии не найдены", Toast.LENGTH_SHORT).show();
                    } else {
                        adapter.setScenarios(scenarios);
                        Log.d("SCENARIOS", "Сценариев пришло: " + scenarios.size());
                    }
                } else {
                    Toast.makeText(AllScenarioActivity.this, "Ошибка загрузки сценариев", Toast.LENGTH_SHORT).show();
                    Log.e("SCENARIOS", "Ответ неуспешный: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<Scenario>> call, Throwable t) {
                String userMessage;

                if (t instanceof java.net.UnknownHostException) {
                    userMessage = "Нет подключения к интернету. Проверьте сеть.";
                } else if (t instanceof java.net.SocketTimeoutException) {
                    userMessage = "Сервер не отвечает. Попробуйте позже.";
                } else {
                    userMessage = "Произошла ошибка при загрузке сценариев.";
                }

                Toast.makeText(AllScenarioActivity.this, userMessage, Toast.LENGTH_SHORT).show();
                Log.e("API_ERROR", "Ошибка загрузки сценариев: ", t);
            }
        });
    }
}