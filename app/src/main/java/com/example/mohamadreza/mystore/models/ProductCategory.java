package com.example.mohamadreza.mystore.models;

import com.google.gson.annotations.SerializedName;

// category of any product
public class ProductCategory {
    @SerializedName("id")
    private Long mId;

    @SerializedName("name")
    private String mName;

    public ProductCategory(Long id, String name) {
        mId = id;
        mName = name;
    }

    public Long getId() {
        return mId;
    }

    public String getName() {
        return mName;
    }
}
