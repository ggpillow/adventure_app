package com.example.adventureapp.ui.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.adventureapp.R;
import com.example.adventureapp.data.model.Scenario;
import com.example.adventureapp.data.network.ApiConfig;

import java.util.ArrayList;
import java.util.List;

public class ScenarioAdapter extends RecyclerView.Adapter<ScenarioAdapter.ScenarioViewHolder> {

    private final Context context;
    private final List<Scenario> scenarioList = new ArrayList<>();
    private OnScenarioClickListener clickListener;

    public ScenarioAdapter(Context context, List<Scenario> initialList) {
        this.context = context;
        if (initialList != null) scenarioList.addAll(initialList);
    }

    @NonNull
    @Override
    public ScenarioViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_scenario_card, parent, false);
        return new ScenarioViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ScenarioViewHolder holder, int position) {
        Scenario scenario = scenarioList.get(position);

        holder.title.setText(scenario.getTitle());
        holder.miniDescription.setText(scenario.getMiniDescription());
        holder.difficulty.setText(scenario.getDifficulty());

        Glide.with(context)
                .load(ApiConfig.BASE_URL + scenario.getImageURL())
                .placeholder(R.drawable.gameplay_background)
                .error(R.drawable.error_image)
                .into(holder.image);

        holder.itemView.setOnClickListener(v -> {
            if (clickListener != null) clickListener.onScenarioClick(scenario);
        });
    }

    @Override
    public int getItemCount() {
        return scenarioList.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setScenarios(List<Scenario> newList) {
        scenarioList.clear();
        scenarioList.addAll(newList);
        notifyDataSetChanged();
    }

    public void attachToRecyclerView(@NonNull RecyclerView recyclerView) {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView rv, int dx, int dy) {
                float center = rv.getWidth() / 2f;

                for (int i = 0; i < rv.getChildCount(); i++) {
                    View child = rv.getChildAt(i);
                    float childCenter = (child.getLeft() + child.getRight()) / 2f;
                    float distance = Math.abs(center - childCenter);

                    float scale = 1 - (distance / center) * 0.2f;
                    float alpha = 1 - (distance / center) * 0.4f;

                    child.setScaleX(Math.max(scale, 0.8f));
                    child.setScaleY(Math.max(scale, 0.8f));
                    child.setAlpha(Math.max(alpha, 0.5f));
                }
            }
        });
    }

    public void setOnScenarioClickListener(OnScenarioClickListener listener) {
        this.clickListener = listener;
    }

    public interface OnScenarioClickListener {
        void onScenarioClick(Scenario scenario);
    }

    static class ScenarioViewHolder extends RecyclerView.ViewHolder {
        final TextView title, miniDescription, difficulty;
        final ImageView image;

        ScenarioViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.scenario_title);
            miniDescription = itemView.findViewById(R.id.scenario_description);
            difficulty = itemView.findViewById(R.id.scenario_difficulty);
            image = itemView.findViewById(R.id.scenario_image);
        }
    }
}