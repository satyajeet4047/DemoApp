package com.example.aislechallenge.ui.login;

import com.example.aislechallenge.ui.dashboard.discovery.DiscoveryFragment;
import com.example.aislechallenge.ui.login.mobilenumber.MobileNumberFragment;
import com.example.aislechallenge.ui.login.otp.OTPFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 *  LoginBindingModule binds fragments related to login activity.
 */
@Module
abstract public class LoginBindingModule {

    @ContributesAndroidInjector
    abstract public MobileNumberFragment contributeMobileNumberFragment();

    @ContributesAndroidInjector
    abstract public OTPFragment contributeOtpFragment();

}
