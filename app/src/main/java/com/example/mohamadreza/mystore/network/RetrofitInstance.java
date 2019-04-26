package com.example.mohamadreza.mystore.network;

import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.Retrofit;


public class RetrofitInstance {
    private static Retrofit instance;
    private static final String BASE_URL = "https://woocommerce.maktabsharif.ir/wp-json/wc/v3/";

    public static Retrofit getInstance() {
        if (instance == null)
            instance = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

        return instance;
    }
}