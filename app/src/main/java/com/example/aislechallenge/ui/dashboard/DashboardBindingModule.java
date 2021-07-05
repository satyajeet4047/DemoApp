package com.example.aislechallenge.ui.dashboard;

import com.example.aislechallenge.ui.dashboard.discovery.DiscoveryFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class DashboardBindingModule {

    @ContributesAndroidInjector
    abstract public DiscoveryFragment discoveryFragment();
}
