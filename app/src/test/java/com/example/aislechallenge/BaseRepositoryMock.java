package com.example.aislechallenge;

import com.example.aislechallenge.data.network.AuthApiService;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import okhttp3.mockwebserver.MockWebServer;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class BaseRepositoryMock {

    static MockWebServer mockWebServer = new MockWebServer();
    public static AuthApiService mockAuthApi = provideRetrofit().create(AuthApiService.class);

    static public Retrofit provideRetrofit() {
        return new Retrofit.Builder().baseUrl(mockWebServer.url("/"))
                .client(provideOkHttp())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }


    static private OkHttpClient provideOkHttp() {
        OkHttpClient.Builder httpClient = new OkHttpClient().newBuilder();
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

}
