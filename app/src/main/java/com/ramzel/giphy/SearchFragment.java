package com.ramzel.giphy;

import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SearchView;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ramzel.giphy.models.Datum;
import com.ramzel.giphy.models.GiphyResponse;
import com.ramzel.giphy.models.Pagination;

import javax.inject.Inject;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class SearchFragment extends Fragment implements SearchPresenter.Listener {

    private static final String DEFAULT_QUERY = "Kittens";

    @Inject GiphyManager giphyManager;

    @Nullable SearchPresenter presenter;
    @NonNull private Pagination pagination = new Pagination();
    @NonNull private String query = "";
    @Nullable Listener listener;

    public SearchFragment() {
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        presenter = initPresenter(view);
    }

    @NonNull
    private SearchPresenter initPresenter(View view) {
        //Measure the screen so the rows can be presized for smooth scrolling
        Display display = getActivity().getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x/2;
        return new SearchPresenter(view, this, width);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        GiphyApplication.ApplicationComponent component =
                ((GiphyApplication) getActivity().getApplication()).component();
        component.inject(this);
    }

    public void setListener(@Nullable Listener listener) {
        this.listener = listener;
    }

    @Override
    public void wantMoreGiphs(int offset) {
        if (offset < pagination.total_count) {
            loadMoreGiphs(offset);
        }
    }

    @Override
    public void giphSelected(Datum datum) {
        if (listener != null) {
            listener.wantDetail(datum);
        }
    }

    private void loadMoreGiphs(int offset) {
        if (listener != null) {
            listener.startLoading();
        }
        giphyManager.search(query, offset)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<GiphyResponse>() {
                    @Override
                    public void onCompleted() {
                        if (listener != null) {
                            listener.finishLoading();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(GiphyResponse giphyResponse) {
                        if (presenter != null && giphyResponse.data != null) {
                            presenter.addGiphs(giphyResponse.data);
                        }
                        if (giphyResponse.pagination != null) {
                            pagination = giphyResponse.pagination;
                        }
                    }
                });
    }

    @Override
    public void queryChanged(@NonNull String query) {
        this.query = query;
        if (presenter != null) {
            presenter.clear();
        }
        loadMoreGiphs(0);
    }

    public void setSearchView(SearchView searchView) {
        if (presenter != null) {
            presenter.setSearchView(searchView);
            searchView.setQuery(DEFAULT_QUERY, false);
        }
    }

    interface Listener {

        void startLoading();

        void finishLoading();

        void wantDetail(Datum datum);
    }
}
