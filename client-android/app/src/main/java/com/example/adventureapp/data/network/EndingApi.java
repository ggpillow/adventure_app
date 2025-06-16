package com.example.adventureapp.data.network;

import com.example.adventureapp.data.model.Ending;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface EndingApi {

    // Получение всех концовок по сценарию
    @GET("endings/scenario/{scenarioId}")
    Call<List<Ending>> getEndingsByScenario(@Path("scenarioId") Long scenarioId);

    // Получение конкретной концовки по сценарию и названию
    @GET("endings/scenario/{scenarioId}/title")
    Call<Ending> getEndingByScenarioAndTitle(
            @Path("scenarioId") Long scenarioId,
            @Query("titleEnding") String titleEnding
    );
}
