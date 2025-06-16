package com.example.adventureapp.data.network;

import com.example.adventureapp.data.model.Resource;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;

public interface ResourceApi {
    @GET("resources")
    Call<List<Resource>> getAllResources();
}