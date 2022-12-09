package com.CS571.myapplication.network;

import android.util.Log;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class autoDetectIpRetrofitClient {
    private static final String API_KEY = "AIzaSyAII0DbVU9uKZI07_PMYNLyRDeiXXH3a98";
    private static final String BASE_URL = "https://ipinfo.io";

    public static Retrofit newInstance() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new autoDetectIpRetrofitClient.HeaderInterceptor())
                .build();
        Log.d("autoDetect", "sendRequest");
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();
    }

    private static class HeaderInterceptor implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request original = chain.request();
            Request request = original
                    .newBuilder()
                    .header("api-key", API_KEY)
                    .build();
            Log.d("autoDetect",request.toString());
            return chain.proceed(request);
        }
    }
}
