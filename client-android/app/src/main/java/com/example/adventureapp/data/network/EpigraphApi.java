package com.example.adventureapp.data.network;

import com.example.adventureapp.data.model.Epigraph;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface EpigraphApi {

    @GET("epigraphs/random")
    Call<Epigraph> getRandomEpigraph();

    @GET("epigraphs")
    Call<List<Epigraph>> getAllEpigraphs();
}
