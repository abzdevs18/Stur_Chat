package com.test_app.myapplication.model;

import com.google.gson.annotations.SerializedName;

public class Is_Typing {
    @SerializedName("typing")
    private String typing;
    @SerializedName("reciever")
    private String reciever;
    @SerializedName("sender")
    private String sender;
    @SerializedName("p_k")
    private String p_k;


    public String getTyping() {
        return typing;
    }

    public String getReciever() {
        return reciever;
    }

    public String getSender() {
        return sender;
    }

    public String getP_k() {
        return p_k;
    }
}
