package com.example.aislechallenge.data;

import com.example.aislechallenge.data.model.request.VerifyPhoneNumParams;
import com.example.aislechallenge.data.model.request.VerifyOTPParams;
import com.example.aislechallenge.data.model.response.GetProfileResponse;
import com.example.aislechallenge.data.model.response.VerifyPhoneNumResponse;
import com.example.aislechallenge.data.model.response.VerifyOTPResponse;
import com.example.aislechallenge.data.network.AuthApiService;

import javax.inject.Inject;

import io.reactivex.Single;

/**
 *     BaseRepository class exposes methods to make api call.
 */
public class BaseRepository {

    private final AuthApiService mAuthApiService;

    @Inject
    public BaseRepository(AuthApiService authApiService) {
        this.mAuthApiService = authApiService;
    }

    public Single<VerifyPhoneNumResponse> verifyPhoneNumber(String phoneNumber) {
        return mAuthApiService.verifyPhoneNumber(new VerifyPhoneNumParams(phoneNumber));
    }

    public Single<VerifyOTPResponse> verifyOTP(VerifyOTPParams verifyOTPParams) {
        return mAuthApiService.verifyOtp(verifyOTPParams);
    }

    public Single<GetProfileResponse> getUserProfiles(String token) {
        return mAuthApiService.getUserProfiles(token);
    }

}
