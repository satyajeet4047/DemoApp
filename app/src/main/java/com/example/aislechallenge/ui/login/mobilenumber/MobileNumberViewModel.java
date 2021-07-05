package com.example.aislechallenge.ui.login.mobilenumber;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.aislechallenge.data.BaseRepository;
import com.example.aislechallenge.data.model.response.VerifyPhoneNumResponse;
import com.example.aislechallenge.util.AppPreferencesHelper;
import com.example.aislechallenge.util.Status;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Mobile Number ViewModel holds data related to MobileNumberFragment
 */
public class MobileNumberViewModel extends ViewModel {
    @Inject
    public AppPreferencesHelper mAppPreferencesHelper;
    private final BaseRepository mBaseRepository;
    private final CompositeDisposable mCompositeDisposable;
    private final MutableLiveData<String> isPhoneNumberValid = new MutableLiveData<>();
    private final MutableLiveData<Status> apiCallStatus = new MutableLiveData<>();
    private final MutableLiveData<Boolean> loginStatus = new MutableLiveData<>();

    @Inject
    public MobileNumberViewModel(BaseRepository baseRepository) {
        this.mBaseRepository = baseRepository;
        mCompositeDisposable = new CompositeDisposable();
    }

    public LiveData<String> getIsPhoneNumberValid() {
        return isPhoneNumberValid;
    }

    public LiveData<Status> getApiCallStatus() {
        return apiCallStatus;
    }

    public LiveData<Boolean> getLoginStatus() { return loginStatus; }

    public void checkLoginStatus() { loginStatus.setValue(mAppPreferencesHelper.isLoggedIn()); }


    // APi Call to check validity of number
    public void verifyPhoneNumber(String phoneNumber) {
        apiCallStatus.setValue(Status.LOADING);
        mCompositeDisposable.add(
                mBaseRepository.verifyPhoneNumber(phoneNumber)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribe(this::onSuccess, this::onFailure));
    }

    private void onFailure(Throwable throwable) {
        apiCallStatus.setValue(Status.ERROR);
    }

    private void onSuccess(VerifyPhoneNumResponse isSuccess) {
        if (isSuccess.getStatus()) {
            apiCallStatus.setValue(Status.SUCCESS);
        } else {
            apiCallStatus.setValue(Status.ERROR);
        }
    }

    //Validate Phone number entered
    public void isValidPhoneNumber(String phoneNumber) {
        if (phoneNumber.length() == 10 && android.util.Patterns.PHONE.matcher(phoneNumber).matches()) {
            isPhoneNumberValid.setValue(phoneNumber);
        } else {
            isPhoneNumberValid.setValue("Error");
        }
    }


    @Override
    protected void onCleared() {
        super.onCleared();
        mCompositeDisposable.clear();
    }
}