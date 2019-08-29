package com.test_app.myapplication.model;

import com.google.gson.annotations.SerializedName;

public class Registration {
    @SerializedName("username")
    private String username;
    @SerializedName("lastname")
    private String lastname;
    @SerializedName("email")
    private String email;
    @SerializedName("password")
    private String password;

    public String getUsername() {
        return username;
    }

    public String getLastname() {
        return lastname;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
