package com.example.adventureapp.data.network;

import com.example.adventureapp.data.model.Effect;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface EffectApi {

    @GET("effects")
    Call<List<Effect>> getAllEffects();

    @GET("effects/{id}")
    Call<Effect> getEffectById(@Path("id") Long id);
}