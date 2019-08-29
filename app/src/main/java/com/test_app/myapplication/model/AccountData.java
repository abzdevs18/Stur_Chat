package com.test_app.myapplication.model;

import com.google.gson.annotations.SerializedName;

public class AccountData {
    @SerializedName("p_k")
    private String p_k;
    @SerializedName("username")
    private String username;
    @SerializedName("lastname")
    private String lastname;
    @SerializedName("email")
    private String email;
    @SerializedName("image")
    private String image;

    public String getP_k() {
        return p_k;
    }

    public String getUsername() {
        return username;
    }

    public String getLastname() {
        return lastname;
    }

    public String getEmail() {
        return email;
    }

    public String getImage() {
        return image;
    }
}
