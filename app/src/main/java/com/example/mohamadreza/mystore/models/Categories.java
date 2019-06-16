package com.example.mohamadreza.mystore.models;

import com.google.gson.annotations.SerializedName;

public class Categories {
    @SerializedName("id")
    private Long mId;

    @SerializedName("name")
    private String mName;

    public Categories(Long id, String name) {
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
