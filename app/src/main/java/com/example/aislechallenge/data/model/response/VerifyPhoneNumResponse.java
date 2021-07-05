package com.example.aislechallenge.data.model.response;

import com.google.gson.annotations.SerializedName;

public class VerifyPhoneNumResponse {

    @SerializedName("status")
    Boolean status;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
}
