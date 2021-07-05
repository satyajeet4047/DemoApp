package com.example.aislechallenge;

import com.example.aislechallenge.di.component.DaggerApplicationComponent;
import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication;

/**
 *  AisleChallengeApplication is entry point of the application which builds
 *  dependency graph using dagger.
 */
public class AisleChallengeApplication extends DaggerApplication {

    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        DaggerApplicationComponent daggerApplicationComponent =
                (DaggerApplicationComponent)
                        DaggerApplicationComponent.builder()
                                .application(this)
                                .build();
        daggerApplicationComponent.inject(this);
        return daggerApplicationComponent;
    }

}
