package com.example.mohamadreza.mystore.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Products {
    @SerializedName("products")
    private List<Product> mProducts;

    public Products(List<Product> products) {
        mProducts = products;
    }

    public List<Product> getProducts() {
        return mProducts;
    }
}