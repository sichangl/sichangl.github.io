package com.CS571.myapplication.network;

import com.CS571.myapplication.model.autoComplete.autoCompleteResponse;
import com.CS571.myapplication.model.reviewYelp.reviewResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface autoCompleteApi {
    @GET("autoComplete")
    Call<autoCompleteResponse> sendAutoCompleteYelpRequest(@Query("text") String text);
}
