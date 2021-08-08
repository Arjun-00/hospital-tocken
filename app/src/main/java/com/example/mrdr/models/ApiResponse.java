package com.example.mrdr.models;

import com.google.gson.annotations.SerializedName;

public class ApiResponse {
    @SerializedName("status")
    private String status;

    @SerializedName("result_code")
    private int resultCode;

    @SerializedName("name")
    private String name;

    @SerializedName("age")
    private String age;

    @SerializedName("place")
    private String place;

    @SerializedName("image")
    private String image;


    public String getImage() {
        return image;
    }

    public String getStatus() {
        return status;
    }

    public int getResultCode() {
        return resultCode;
    }

    public String getName() {
        return name;
    }

    public String getAge() {
        return age;
    }

    public String getPlace() {
        return place;
    }
}
