package com.ramzel.giphy;

import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Display;
import android.view.View;

import com.ramzel.giphy.models.Datum;

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
        if (view != null) {
            if (snackbar == null) {
                snackbar = Snackbar.make(view, R.string.loading, Snackbar.LENGTH_INDEFINITE);
            }
            if (!snackbar.isShown() && view != null) {
                snackbar.show();
            }
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
}
