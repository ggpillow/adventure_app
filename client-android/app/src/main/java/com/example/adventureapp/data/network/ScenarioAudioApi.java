package com.example.adventureapp.data.network;

import com.example.adventureapp.data.model.ScenarioAudio;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ScenarioAudioApi {
    @GET("scenario-audio/by-scenario/{scenarioId}")
    Call<List<ScenarioAudio>> getAudioByScenario(@Path("scenarioId") Long scenarioId);
}