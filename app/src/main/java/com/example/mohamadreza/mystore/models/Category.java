package com.example.mohamadreza.mystore.models;

import android.media.Image;

import com.google.gson.annotations.SerializedName;

public class Category {
    @SerializedName("id")
    private Long mId;

    @SerializedName("name")
    private String mName;

    @SerializedName("parent")
    private Long mParentId;

    @SerializedName("image")
    private Image mImage;

}