package com.CS571.myapplication.network;

import com.CS571.myapplication.model.googleGeoLocationApi.locationResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GoogleApi {
    @GET("json")
    Call<locationResponse> getGoogleLocation(@Query("address") String address, @Query("key") String API_KEY );
}
