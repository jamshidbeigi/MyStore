package com.example.mohamadreza.mystore.models;

import com.google.gson.annotations.SerializedName;

public class Image {

    @SerializedName("id")
    private Long mId;
    @SerializedName("src")
    private String mPath;

    public Image(Long id, String path) {
        mId = id;
        mPath = path;
    }

    public Long getId() {
        return mId;
    }

    public String getPath() {
        return mPath;
    }
}