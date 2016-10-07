package com.ramzel.giphy;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ramzel.giphy.models.Datum;
import com.ramzel.giphy.models.GiphyResponse;
import com.ramzel.giphy.models.Pagination;

import javax.inject.Inject;

import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class SearchFragment extends Fragment implements SearchPresenter.Listener {

    @Nullable @Inject GiphyManager giphyManager;

    @Nullable private SearchPresenter presenter;
    @NonNull private Pagination pagination = new Pagination();
    @NonNull private String query = "";
    @Nullable private Listener listener;
    private Subscription subscription;

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
    public void onAttach(Context context) {
        super.onAttach(context);
        GiphyApplication.ApplicationComponent component =
                ((GiphyApplication) getActivity().getApplication()).component();
        if (component != null) {
            component.inject(this);
        }
    }

    @Override
    public void onStop() {
        stopPendingSubsciption();
        super.onStop();
    }

    public void setListener(@Nullable Listener listener) {
        this.listener = listener;
    }

    public void setPresenter(@Nullable SearchPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void wantMoreGiphs(int offset) {
        if (offset < pagination.total_count) {
            loadMoreGiphs(offset);
        }
    }

    @Override
    public void giphSelected(@NonNull Datum datum) {
        if (listener != null) {
            listener.wantDetail(datum);
        }
    }

    public void loadMoreGiphs(int offset) {
        if (listener != null) {
            listener.startLoading();
        }
        if (giphyManager != null) {
            stopPendingSubsciption();
            subscription = giphyManager.search(query, offset)
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
    }

    private void stopPendingSubsciption() {
        if (subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
            subscription = null;
        }
    }

    @Override
    public void queryChanged(@NonNull String query) {
        this.query = query;
        if (presenter != null) {
            presenter.clear();
        }
        loadMoreGiphs(0);
    }

    interface Listener {

        void startLoading();

        void finishLoading();

        void wantDetail(Datum datum);
    }
}
