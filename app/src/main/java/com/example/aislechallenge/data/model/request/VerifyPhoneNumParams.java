package com.example.aislechallenge.data.model.request;

import com.google.gson.annotations.SerializedName;

/**
 *  Request class to send phone number
 */
public class VerifyPhoneNumParams {

    @SerializedName("number")
    private String phoneNumber;

    public VerifyPhoneNumParams(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
