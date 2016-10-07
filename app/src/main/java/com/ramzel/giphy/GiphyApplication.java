package com.ramzel.giphy;

import android.app.Application;
import dagger.Component;
import javax.inject.Singleton;

public class GiphyApplication extends Application {

    @Singleton
    @Component(modules = GiphyApiModule.class)
    public interface ApplicationComponent {
        void inject(GiphyApplication application);
        void inject(SearchFragment searchFragment);
        void inject(GiphyManager giphyManager);
    }

    private ApplicationComponent component;

    @Override public void onCreate() {
        super.onCreate();
        component = DaggerGiphyApplication_ApplicationComponent.builder()
                .giphyApiModule(new GiphyApiModule(this))
                .build();
        component().inject(this);
    }

    public ApplicationComponent component() {
        return component;
    }
}