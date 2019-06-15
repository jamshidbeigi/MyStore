package com.example.mohamadreza.mystore.network;


import com.example.mohamadreza.mystore.models.Category;
import com.example.mohamadreza.mystore.models.Product;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface Api {

    @GET("products/?consumer_key=ck_88598b2f6dcbd898469bab54ebed17181f657f4a&consumer_secret=cs_2155b7b3787139f6b0cd6af37e3104241aec550e")
    Call<List<Product>> getProducts();

    @GET("products/?consumer_key=" +
            "ck_9c71c8861720068e73b5a5262a309f2703e0ce9e" +
            "&consumer_secret=" +
            "cs_40c14b983a9aaf064710624122478c74fcfbf06f")
    Call<List<Product>> getProducts(@Query("orderby") String orderBy);

    @GET("products/{id}?consumer_key=" +
            "ck_9c71c8861720068e73b5a5262a309f2703e0ce9e" +
            "&consumer_secret=" +
            "cs_40c14b983a9aaf064710624122478c74fcfbf06f")
    Call<Product> getProduct(@Path("id") long id);

    //    @GET()
//    Call<Product> getProduct(@Url String url);


    @GET("products/categories/?consumer_key=ck_88598b2f6dcbd898469bab54ebed17181f657f4a&consumer_secret=cs_2155b7b3787139f6b0cd6af37e3104241aec550e")
    Call<List<Category>> getCategories(@Query("parent") long parent);
}