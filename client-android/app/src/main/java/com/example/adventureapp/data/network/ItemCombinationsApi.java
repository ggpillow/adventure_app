package com.example.adventureapp.data.network;

import com.example.adventureapp.data.model.ItemCombinations;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ItemCombinationsApi {
    @GET("item-combinations/search")
    Call<ItemCombinations> getCraftedItem(
            @Query("res1") Long firstId,
            @Query("res2") Long secondId
    );
}