package com.example.adventureapp.ui.scenario;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.core.content.ContextCompat;

import com.example.adventureapp.R;
import com.example.adventureapp.data.cache.DataCache;
import com.example.adventureapp.data.model.ItemCombinations;
import com.example.adventureapp.data.model.Resource;
import com.example.adventureapp.data.network.ApiClient;
import com.example.adventureapp.data.network.ItemCombinationsApi;
import com.example.adventureapp.data.network.ResourceApi;
import com.example.adventureapp.ui.BaseActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ItemCombinationsActivity extends BaseActivity {

    private LinearLayout resourcesContainer;
    private Button buttonCreate, buttonBack, buttonMemo;

    private final List<Resource> allResources = new ArrayList<>();
    private final List<CheckBox> checkBoxes = new ArrayList<>();

    private long scenarioId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_craft_item);

        resourcesContainer = findViewById(R.id.resourcesContainer);
        buttonCreate = findViewById(R.id.buttonCreate);
        buttonBack = findViewById(R.id.buttonBack);
        buttonMemo = findViewById(R.id.buttonMemo);

        backButtonId = R.id.buttonBack;

        scenarioId = getIntent().getLongExtra("scenarioId", -1);
        loadResourcesFromCacheOrApi();

        buttonBack.setOnClickListener(v -> {
            Intent intent = new Intent(this, StoryGameplayActivity.class);
            intent.putExtra("scenarioId", scenarioId);
            startActivity(intent);
            finish();
        });

        buttonMemo.setOnClickListener(v -> startActivity(new Intent(this, MemoActivity.class)));

        buttonCreate.setOnClickListener(v -> {
            List<Long> selected = new ArrayList<>();
            for (int i = 0; i < checkBoxes.size(); i++) {
                if (checkBoxes.get(i).isChecked()) {
                    selected.add(allResources.get(i).getId());
                }
            }

            if (selected.size() < 2) {
                Toast.makeText(this, "Выберите два ресурса", Toast.LENGTH_SHORT).show();
            } else if (selected.size() > 2) {
                Toast.makeText(this, "Можно выбрать только два ресурса", Toast.LENGTH_SHORT).show();
            } else {
                attemptCraft(selected.get(0), selected.get(1));
            }
        });
    }

    private void loadResourcesFromCacheOrApi() {
        List<Resource> cached = DataCache.resources;
        if (cached != null && !cached.isEmpty()) {
            displayResources(cached);
        } else {
            ResourceApi api = ApiClient.getClient().create(ResourceApi.class);
            api.getAllResources().enqueue(new Callback<List<Resource>>() {
                @Override
                public void onResponse(Call<List<Resource>> call, Response<List<Resource>> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        DataCache.resources = response.body();
                        displayResources(response.body());
                    } else {
                        Toast.makeText(ItemCombinationsActivity.this, "Ошибка загрузки ресурсов", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<List<Resource>> call, Throwable t) {
                    Toast.makeText(ItemCombinationsActivity.this, "Ошибка подключения", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
    private void displayResources(List<Resource> resources) {
        allResources.clear();
        allResources.addAll(resources);
        for (Resource res : allResources) {
            View view = getLayoutInflater().inflate(R.layout.item_resource_checkbox, resourcesContainer, false);
            TextView name = view.findViewById(R.id.resourceName);
            CheckBox checkBox = view.findViewById(R.id.resourceCheckbox);
            name.setText(res.getName());

            checkBox.setOnCheckedChangeListener((button, isChecked) -> {
                int selectedCount = (int) checkBoxes.stream().filter(CheckBox::isChecked).count();
                for (CheckBox cb : checkBoxes) {
                    cb.setEnabled(selectedCount < 2 || cb.isChecked());
                }
            });
            checkBoxes.add(checkBox);
            resourcesContainer.addView(view);
        }
    }

    private void attemptCraft(Long id1, Long id2) {
        int baseChance = 85;
        if (id1 > 5 && id2 > 5) baseChance += 5;

        boolean success = new Random().nextInt(100) < baseChance;

        if (!success) {
            Toast.makeText(this, "Крафт не удался. Сбросьте использованные ресурсы в стопку сброса.", Toast.LENGTH_LONG).show();
            resetSelections();
            return;
        }

        ItemCombinationsApi api = ApiClient.getClient().create(ItemCombinationsApi.class);
        api.getCraftedItem(id1, id2).enqueue(new Callback<ItemCombinations>() {
            @Override
            public void onResponse(Call<ItemCombinations> call, Response<ItemCombinations> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ItemCombinations item = response.body();

                    String name1 = getResourceNameById(id1);
                    String name2 = getResourceNameById(id2);

                    Intent intent = new Intent(ItemCombinationsActivity.this, CreatedItemActivity.class);
                    intent.putExtra("result", item.getResult());
                    intent.putExtra("res1", name1);
                    intent.putExtra("res2", name2);
                    intent.putExtra("imageItem", item.getImageItems());
                    intent.putExtra("scenarioId", scenarioId);
                    startActivity(intent);
                } else {
                    Toast.makeText(ItemCombinationsActivity.this, "Такой предмет не найден", Toast.LENGTH_SHORT).show();
                    resetSelections();
                }
            }

            @Override
            public void onFailure(Call<ItemCombinations> call, Throwable t) {
                Toast.makeText(ItemCombinationsActivity.this, "Ошибка при создании предмета", Toast.LENGTH_SHORT).show();
                resetSelections();
            }
        });
    }

    private String getResourceNameById(Long id) {
        return allResources.stream()
                .filter(r -> r.getId().equals(id))
                .map(Resource::getName)
                .findFirst()
                .orElse("неизвестно");
    }

    private void resetSelections() {
        for (CheckBox cb : checkBoxes) {
            cb.setChecked(false);
            cb.setEnabled(true);
        }
    }
}