package com.example.userlistexample;

import java.util.concurrent.TimeUnit;

import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIClient {

    private static final String BASE_URL = "";
    private static Retrofit retrofit = null;

    public static Retrofit getClient() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).connectTimeout(5, TimeUnit.MINUTES)
                .connectTimeout(5, TimeUnit.MINUTES).retryOnConnectionFailure(true).readTimeout(5, TimeUnit.MINUTES)
                .connectionPool(new ConnectionPool(0, 1, TimeUnit.NANOSECONDS)).build();
        retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create())
                .client(client).build();
        return retrofit;
    }

    public static ApiService getService() {
        return getClient().create(ApiService.class);
    }
}
