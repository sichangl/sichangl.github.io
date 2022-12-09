package com.CS571.myapplication.network;

import com.CS571.myapplication.model.detailYelp.detailResponse;
import com.CS571.myapplication.model.reviewYelp.reviewResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface reviewYelpApi {
    @GET("review")
    Call<reviewResponse> sendReviewYelpRequest(@Query("businessId") String businessId);
}
