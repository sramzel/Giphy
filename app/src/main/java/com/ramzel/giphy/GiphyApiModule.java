package com.ramzel.giphy;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
class GiphyApiModule {

    @NonNull private final GiphyApplication application;

    GiphyApiModule(@NonNull GiphyApplication application) {
        this.application = application;
    }

    @Provides
    @Singleton
    GiphyService provideGiphyService() {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(httpLoggingInterceptor)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .baseUrl(GiphyService.BASE_URL)
                .build();

        return retrofit.create(GiphyService.class);
    }

    @Provides
    @Singleton
    @Nullable
    GiphyManager provideGiphyManager() {
        GiphyApplication.ApplicationComponent component = application.component();
        if (component != null) {
            return new GiphyManager(component);
        }

        return null;
    }
}

