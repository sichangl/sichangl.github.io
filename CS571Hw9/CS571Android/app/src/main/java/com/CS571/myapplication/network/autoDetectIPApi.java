package com.CS571.myapplication.network;

import androidx.annotation.Keep;

import com.CS571.myapplication.model.autoComplete.autoCompleteResponse;
import com.CS571.myapplication.model.autoDetect.autoDetectResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface autoDetectIPApi {
    @GET("/")
    Call<autoDetectResponse> sendAutoDetectIpRequest(@Query("token") String myToken);
}
