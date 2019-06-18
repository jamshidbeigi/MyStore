package com.example.mohamadreza.mystore.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

//attributes of any product

public class Attribute {

    @SerializedName("id")
    private Long mId;

    @SerializedName("name")
    private String mName;

    @SerializedName("options")
    private List<String> mOptions;

    public Attribute(Long id, String name, List<String> options) {
        mId = id;
        mName = name;
        mOptions = options;
    }

    public Long getId() {
        return mId;
    }

    public String getName() {
        return mName;
    }

    public List<String> getOptions() {
        return mOptions;
    }}
