package com.example.aislechallenge.ui.login.otp;

import android.annotation.SuppressLint;
import android.os.CountDownTimer;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.aislechallenge.data.BaseRepository;
import com.example.aislechallenge.data.model.request.VerifyOTPParams;
import com.example.aislechallenge.data.model.response.VerifyOTPResponse;
import com.example.aislechallenge.util.AppPreferencesHelper;
import com.example.aislechallenge.util.Status;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;


/**
 *  OTP view model holds data related to OTP fragment
 */
public class OTPViewModel extends ViewModel {

    private final BaseRepository mBaseRepository;
    private final CompositeDisposable mCompositeDisposable;
    private final MutableLiveData<Status> apiCallStatus = new MutableLiveData<>();
    private final MutableLiveData<String> countDownStatus = new MutableLiveData<>();

    private final CountDownTimer mCountDownTimer = new CountDownTimer(60 * 1000, 1000) {
        @SuppressLint("DefaultLocale")
        @Override
        public void onTick(long millisUntilFinished) {
            int seconds = (int) (millisUntilFinished / 1000);

            int hours = seconds / (60 * 60);
            int tempMint = (seconds - (hours * 60 * 60));
            int minutes = tempMint / 60;
            seconds = tempMint - (minutes * 60);
            countDownStatus.setValue(String.format("%02d", minutes)
                    + ":" + String.format("%02d", seconds));
        }

        @Override
        public void onFinish() {
            countDownStatus.setValue("");
        }
    };

    @Inject
    public AppPreferencesHelper mAppPreferencesHelper;

    @Inject
    public OTPViewModel(BaseRepository baseRepository) {
        this.mBaseRepository = baseRepository;
        mCompositeDisposable = new CompositeDisposable();
    }

    public MutableLiveData<Status> getApiCallStatus() {
        return apiCallStatus;
    }

    public MutableLiveData<String> getCountDownStatus() {
        return countDownStatus;
    }

    // Api call to verify OTP.
    public void verifyOtp(String phoneNumber, String otp) {
        if (otp.length() == 0) {
            apiCallStatus.setValue(Status.ERROR);
        } else {
            apiCallStatus.setValue(Status.LOADING);
            VerifyOTPParams verifyOTPParams = new VerifyOTPParams(phoneNumber, otp);
            mCompositeDisposable.add(
                    mBaseRepository.verifyOTP(verifyOTPParams)
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribeOn(Schedulers.io())
                            .subscribe(this::onSuccess, this::onFailure));
        }
    }


    private void onSuccess(VerifyOTPResponse response) {
        if (response.getToken() == null) {
            apiCallStatus.setValue(Status.ERROR);
        } else {
            mAppPreferencesHelper.setAccessToken(response.getToken());
            mAppPreferencesHelper.setLoggedIn(true);
            apiCallStatus.setValue(Status.SUCCESS);
        }
    }

    private void onFailure(Throwable throwable) {
        apiCallStatus.setValue(Status.ERROR);
    }

    public void startCountdown() {
        mCountDownTimer.start();
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        mCompositeDisposable.clear();
        mCountDownTimer.cancel();
    }
}