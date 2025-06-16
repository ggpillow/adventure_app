package com.example.adventureapp.data.network;

import com.example.adventureapp.data.model.Scenario;
import com.example.adventureapp.data.model.Scheme;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ScenarioApi {
    @GET("scenarios")
    Call<List<Scenario>> getAllScenarios();
}