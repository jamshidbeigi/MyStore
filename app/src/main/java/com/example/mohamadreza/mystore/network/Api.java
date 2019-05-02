package com.example.mohamadreza.mystore.network;


import com.example.mohamadreza.mystore.models.Category;
import com.example.mohamadreza.mystore.models.Product;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface Api {


    @GET("products/?consumer_key=" +
            "ck_88598b2f6dcbd898469bab54ebed17181f657f4a" +
            "&consumer_secret=" +
            "cs_2155b7b3787139f6b0cd6af37e3104241aec550e")
    Call<List<Product>> getProducts();

    @GET("products/categories/?consumer_key=" +
            "ck_88598b2f6dcbd898469bab54ebed17181f657f4a" +
            "&consumer_secret=" +
            "cs_2155b7b3787139f6b0cd6af37e3104241aec550e")
    Call<List<Category>> getCategories(@Query("parent") long parent);
}