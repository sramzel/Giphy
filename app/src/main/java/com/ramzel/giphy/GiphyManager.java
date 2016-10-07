package com.ramzel.giphy;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.ramzel.giphy.models.GiphyResponse;

import javax.inject.Inject;

import rx.Observable;

class GiphyManager {

    private static final String API_KEY = "dc6zaTOxFJmzC";
    @Inject GiphyService giphyService;

    GiphyManager(GiphyApplication.ApplicationComponent component) {
        component.inject(this);
    }

    @NonNull
    Observable<GiphyResponse> search(@Nullable String query, int offset) {
        return giphyService.search(query, offset, API_KEY);
    }
}
