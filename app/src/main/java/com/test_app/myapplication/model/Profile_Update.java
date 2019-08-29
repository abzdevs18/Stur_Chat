package com.test_app.myapplication.model;

import com.google.gson.annotations.SerializedName;

public class Profile_Update {
    @SerializedName("p_k")
    private String p_k;
    @SerializedName("photo")
    private String photo;
    @SerializedName("success")
    private String success;
    @SerializedName("error")
    private String error;

    public String getSuccess() {
        return success;
    }

    public String getError() {
        return error;
    }
}
