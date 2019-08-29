package com.test_app.myapplication.model;

import com.google.gson.annotations.SerializedName;

public class Typing {
    @SerializedName("typing")
    private String typing;
    @SerializedName("p_k")
    private String p_k;

    public String getTyping() {
        return typing;
    }

    public String getP_k() {
        return p_k;
    }
}
