package com.ramzel.giphy;

import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.*;
import android.support.v7.widget.SearchView;
import android.util.AttributeSet;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;

import com.ramzel.giphy.models.Datum;

import javax.inject.Singleton;

import dagger.Provides;

public class SearchActivity extends AppCompatActivity implements SearchFragment.Listener {

    private static final String DEFAULT_QUERY = "Kittens";
    @Nullable private Snackbar snackbar;
    @Nullable private View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        view = findViewById(android.R.id.content);

        initSearchFragment();
    }

    private void initSearchFragment() {
        SearchFragment searchFragment =
                (SearchFragment) getSupportFragmentManager().findFragmentById(R.id.fragment);
        SearchView searchView = (SearchView) findViewById(R.id.search_view);

        SearchPresenter searchFragmentPresenter = createSearchFragmentPresenter(searchFragment);

        if (searchFragmentPresenter != null) {
            searchFragmentPresenter.setSearchView(searchView);
            searchFragment.setPresenter(searchFragmentPresenter);
        }
        searchFragment.setListener(this);
        searchFragment.loadMoreGiphs(0);

        searchView.setQuery(DEFAULT_QUERY, false);
    }

    @Nullable
    private SearchPresenter createSearchFragmentPresenter(@NonNull SearchFragment searchFragment) {
        View view = searchFragment.getView();
        if (view != null) {
            //Measure the screen so the rows can be presized for smooth scrolling
            Display display = getWindowManager().getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            int width = size.x/2;
            return new SearchPresenter(view, searchFragment, width);
        }

        return null;
    }

    @Override
    public void startLoading() {
        if (snackbar != null && snackbar.isShown()) {
            snackbar.dismiss();
        }
        if (view != null) {
            snackbar = Snackbar.make(view, "Loading...", Snackbar.LENGTH_INDEFINITE);
            snackbar.show();
        }
    }

    @Override
    public void finishLoading() {
        if (snackbar != null && snackbar.isShown()) {
            snackbar.dismiss();
        }
    }


    @Override
    public void wantDetail(Datum datum) {
        FragmentManager fm = getSupportFragmentManager();
        DetailDialog detailDialog = new DetailDialog();
        Bundle arguments = new Bundle();
        arguments.putParcelable(DetailDialog.KEY_DATUM, datum);
        detailDialog.setArguments(arguments);
        detailDialog.show(fm, "fragment_detail");
    }

    @Override
    public void onSearchFragmentViewCreated() {
    }
}
