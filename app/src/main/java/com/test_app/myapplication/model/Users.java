package com.test_app.myapplication.model;

import com.google.gson.annotations.SerializedName;

public class Users {
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
    @SerializedName("unreadMessage")
    private String unreadMessage;
    @SerializedName("time")
    private String time;

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

    public String getUnreadMessage() {
        return unreadMessage;
    }

    public String getTime() {
        return time;
    }
}
