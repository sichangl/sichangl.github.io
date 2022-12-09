package com.CS571.myapplication.network;

import android.util.Log;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BackendYelpRetrofitClient {
    private static final String API_KEY = "AIzaSyAII0DbVU9uKZI07_PMYNLyRDeiXXH3a98";
    private static final String BASE_URL = "https://hw8-backend-368020.wl.r.appspot.com/";
    public static Retrofit newInstance() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new HeaderInterceptor())
                .build();
        Log.d("sichang", "sendRequest");
        return  new Retrofit.Builder()
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
            Log.d("sichang",request.toString());
            return chain.proceed(request);
        }
    }
}
