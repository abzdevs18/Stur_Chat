package com.test_app.myapplication.model;

import com.google.gson.annotations.SerializedName;

public class SignIn {
    @SerializedName("success_login")
    private String success_login;
    @SerializedName("email")
    private String email;
    @SerializedName("password")
    private String password;
    @SerializedName("p_k")
    private String p_k;

    public String getSuccess_login() {
        return success_login;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getP_k() {
        return p_k;
    }
}
