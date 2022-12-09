package com.CS571.myapplication.network;

import com.CS571.myapplication.model.detailYelp.detailResponse;
import com.CS571.myapplication.model.googleGeoLocationApi.locationResponse;
import com.CS571.myapplication.model.yelpApi.yelpResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface detailYelpApi {
    @GET("searchBusinessDetail")
    Call<detailResponse> sendDetailYelpRequest(@Query("businessId") String businessId);
}