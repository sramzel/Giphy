package com.ramzel.giphy;

import android.app.Application;
import android.support.annotation.Nullable;

import javax.inject.Singleton;

import dagger.Component;

public class GiphyApplication extends Application {

    @Singleton
    @Component(modules = GiphyApiModule.class)
    public interface ApplicationComponent {
        void inject(SearchFragment searchFragment);
        void inject(GiphyManager giphyManager);
    }

    @Nullable private ApplicationComponent component;

    @Override public void onCreate() {
        super.onCreate();
        component = DaggerGiphyApplication_ApplicationComponent.builder()
                .giphyApiModule(new GiphyApiModule(this))
                .build();
    }

    @Nullable
    public ApplicationComponent component() {
        return component;
    }
}