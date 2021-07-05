package com.example.aislechallenge.data.model.response;

import com.google.gson.annotations.SerializedName;

public class VerifyOTPResponse {

    @SerializedName("token")
    String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
