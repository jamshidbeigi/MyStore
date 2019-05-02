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

    public Category(Long id, String name, Long parentId, Image image) {
        mId = id;
        mName = name;
        mParentId = parentId;
        mImage = image;
    }

    public Long getId() {
        return mId;
    }

    public String getName() {
        return mName;
    }

    public Long getParentId() {
        return mParentId;
    }

    public Image getImage() {
        return mImage;
    }

}