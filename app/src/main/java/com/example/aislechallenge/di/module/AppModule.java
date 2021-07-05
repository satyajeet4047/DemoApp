package com.example.aislechallenge.di.module;

import com.example.aislechallenge.util.AppPreferencesHelper;
import com.example.aislechallenge.util.ImageLoaderUtil;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 *     AppModule to build dependency graph
 */

@Module
public class AppModule {

    @Provides
    @Singleton
    AppPreferencesHelper providePreferencesHelper(AppPreferencesHelper appPreferencesHelper) {
        return appPreferencesHelper;
    }

    @Provides
    @Singleton
    ImageLoaderUtil provideImageLoaderUtil(ImageLoaderUtil imageLoaderUtil){
        return imageLoaderUtil;
    }

}
