package com.example.mohamadreza.mystore.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Attributes {
    @SerializedName("id")
    private Long mId;
    @SerializedName("name")
    private String mName;
    @SerializedName("options")
    private List<String> mOptions;

    public Long getId() {
        return mId;
    }

    public String getName() {
        return mName;
    }

    public List<String> getOptions() {
        return mOptions;
    }

    public Attributes(Long id, String name, List<String> options) {
        mId = id;
        mName = name;
        mOptions = options;
    }
}
