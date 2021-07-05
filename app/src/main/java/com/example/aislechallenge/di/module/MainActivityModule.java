package com.example.aislechallenge.di.module;

import com.example.aislechallenge.di.ActivityScope;
import com.example.aislechallenge.ui.dashboard.DashboardActivity;
import com.example.aislechallenge.ui.dashboard.DashboardBindingModule;
import com.example.aislechallenge.ui.login.LoginBindingModule;
import com.example.aislechallenge.ui.login.LoginActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 *  Module to contribute activities
 */
@Module
abstract public class MainActivityModule {

    @ActivityScope
    @ContributesAndroidInjector(modules = {LoginBindingModule.class})
    abstract public LoginActivity provideMainActivity();

    @ActivityScope
    @ContributesAndroidInjector(modules = {DashboardBindingModule.class})
    abstract public DashboardActivity provideDashboardActivity();
}
