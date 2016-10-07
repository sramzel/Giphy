package com.ramzel.giphy;

import com.ramzel.giphy.models.GiphyResponse;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

interface GiphyService {

    String BASE_URL = "http://api.giphy.com/v1/";

    @GET("gifs/search")
    Observable<GiphyResponse> search(
            @Query("q") String query,
            @Query("offset") int offset,
            @Query("api_key") String apiKey);
}
