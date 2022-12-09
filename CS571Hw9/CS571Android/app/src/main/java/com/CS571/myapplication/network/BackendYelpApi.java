package com.CS571.myapplication.network;

import com.CS571.myapplication.model.googleGeoLocationApi.locationResponse;
import com.CS571.myapplication.model.yelpApi.yelpResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface BackendYelpApi {
    @GET("search")
    Call<yelpResponse> sendYelpRequest(@Query("term") String keyWord, @Query("categories") String category, @Query("distance") String distance,
                                             @Query("latitude") String latitude, @Query("longitude") String longitude);
}
