package com.example.aislechallenge.data.model.request;

import com.google.gson.annotations.SerializedName;

/**
 *  Request class to send phone number, OTP
 */
public class VerifyOTPParams {

    @SerializedName("number")
    String phoneNumber;

    @SerializedName("otp")
    String otp;

    public VerifyOTPParams(String phoneNumber, String otp) {
        this.phoneNumber = phoneNumber;
        this.otp = otp;
    }

}

