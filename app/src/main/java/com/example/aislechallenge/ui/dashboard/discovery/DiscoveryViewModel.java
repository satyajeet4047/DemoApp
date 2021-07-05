package com.example.aislechallenge.ui.dashboard.discovery;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.aislechallenge.data.BaseRepository;
import com.example.aislechallenge.data.model.response.GetProfileResponse;
import com.example.aislechallenge.util.AppPreferencesHelper;
import com.example.aislechallenge.util.Status;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class DiscoveryViewModel extends ViewModel {

    @Inject
    public AppPreferencesHelper mAppPreferencesHelper;
    private MutableLiveData<GetProfileResponse> mProfiles = new MutableLiveData<>();
    private MutableLiveData<Status> apiCallStatus = new MutableLiveData<>();
    private final BaseRepository mBaseRepository;
    private final CompositeDisposable mCompositeDisposable;

    @Inject
    public DiscoveryViewModel(BaseRepository baseRepository) {
        this.mBaseRepository = baseRepository;
        mCompositeDisposable = new CompositeDisposable();
    }


    public MutableLiveData<GetProfileResponse> getProfiles() {
        return mProfiles;
    }

    public MutableLiveData<Status> getApiCallStatus() {
        return apiCallStatus;
    }

    public void fetchProfiles() {
        apiCallStatus.setValue(Status.LOADING);
        mCompositeDisposable.add(
                mBaseRepository.getUserProfiles(mAppPreferencesHelper.getAccessToken())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribe(this::onSuccess, this::onFailure));
    }

    private void onFailure(Throwable throwable) {
        apiCallStatus.setValue(Status.ERROR);
    }

    private void onSuccess(GetProfileResponse response) {
        apiCallStatus.setValue(Status.SUCCESS);
        mProfiles.setValue(response);
    }


    @Override
    protected void onCleared() {
        super.onCleared();
        mCompositeDisposable.clear();
    }
}