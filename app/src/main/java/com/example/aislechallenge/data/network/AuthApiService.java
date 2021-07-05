package com.example.aislechallenge.data.network;


import com.example.aislechallenge.data.model.request.VerifyPhoneNumParams;
import com.example.aislechallenge.data.model.response.VerifyPhoneNumResponse;
import com.example.aislechallenge.data.model.response.VerifyOTPResponse;
import com.example.aislechallenge.data.model.request.VerifyOTPParams;
import com.example.aislechallenge.data.model.response.GetProfileResponse;

import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

/**
 *     Interface which consists API end points
 */
public interface AuthApiService {

    @POST("/V1/users/phone_number_login")
    Single<VerifyPhoneNumResponse> verifyPhoneNumber(@Body VerifyPhoneNumParams phoneNumber);

    @POST("/V1/users/verify_otp")
    Single<VerifyOTPResponse> verifyOtp(@Body VerifyOTPParams otpParam);

    @GET("/V1/users/test_profile_list")
    Single<GetProfileResponse> getUserProfiles(@Header("Authorization") String token);
}

