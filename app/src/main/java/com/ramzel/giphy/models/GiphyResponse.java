package com.ramzel.giphy.models;

import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class GiphyResponse {

    @Nullable public List<Datum> data = new ArrayList<>();
    @Nullable public Meta meta;
    @Nullable public Pagination pagination;

}
