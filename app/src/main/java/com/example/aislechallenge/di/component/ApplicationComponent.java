package com.example.aislechallenge.di.component;


import android.app.Application;

import com.example.aislechallenge.AisleChallengeApplication;
import com.example.aislechallenge.di.module.AppModule;
import com.example.aislechallenge.di.module.ContextModule;
import com.example.aislechallenge.di.module.MainActivityModule;
import com.example.aislechallenge.di.module.NetworkModule;


import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;
import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication;
import dagger.android.support.AndroidSupportInjectionModule;
import javax.inject.Singleton;

/**
 *     Dagger Component to build dependency graph
 */
@Singleton
@Component(modules = {AndroidSupportInjectionModule.class, ContextModule.class,MainActivityModule.class, NetworkModule.class})
public interface ApplicationComponent extends AndroidInjector<DaggerApplication>{
     void injectApp(AisleChallengeApplication aisleChallengeApplication);

     @Component.Builder
     interface Builder {

          @BindsInstance
          Builder application(Application application);
          ApplicationComponent build();
     }
}
