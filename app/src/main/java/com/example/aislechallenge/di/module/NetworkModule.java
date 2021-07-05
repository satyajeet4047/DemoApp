package com.example.aislechallenge.di.module;

import com.example.aislechallenge.data.network.AuthApiService;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Network module provides retrofit client to make api calls.
 */
@Module
public class NetworkModule {


    static final String BASE_URL = "https://testa2.aisle.co";
    private static final int REQUEST_TIMEOUT = 60;

    @Singleton
    @Provides
    static Retrofit provideRetrofit(OkHttpClient okHttpClient) {
        return new Retrofit.Builder().baseUrl(BASE_URL)
                .client(okHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    @Singleton
    @Provides
    static OkHttpClient provideOkHttp() {
        OkHttpClient.Builder httpClient = new OkHttpClient().newBuilder()
                .connectTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS);

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        httpClient.addInterceptor(interceptor);

        httpClient.addInterceptor(chain -> {
            Request original = chain.request();
            Request.Builder requestBuilder = ((Request) original).newBuilder()
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Cookie", "__cfduid=df9b865983bd04a5de2cf5017994bbbc71618565720");

            Request request = requestBuilder.build();
            return chain.proceed(request);
        });

        return httpClient.build();
    }

    @Singleton
    @Provides
    AuthApiService provideRetrofitService(Retrofit retrofit) {
        return retrofit.create(AuthApiService.class);
    }

}
