package com.example.adventureapp.data.network;

import com.example.adventureapp.data.model.Scheme;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface SchemeApi {
    @GET("schemes/scenario/{scenarioId}")
    Call<List<Scheme>> getSchemesByScenario(@Path("scenarioId") long scenarioId);

    @GET("schemes")
    Call<List<Scheme>> getAllSchemes();
}
