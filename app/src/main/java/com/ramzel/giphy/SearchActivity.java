package com.ramzel.giphy;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.*;
import android.support.v7.widget.SearchView;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.ramzel.giphy.models.Datum;

import javax.inject.Singleton;

import dagger.Provides;

public class SearchActivity extends AppCompatActivity implements SearchFragment.Listener {

    @Nullable private Snackbar snackbar;
    @Nullable private View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        view = findViewById(android.R.id.content);
        SearchView searchView = (SearchView) findViewById(R.id.search_view);

        SearchFragment searchFragment =
                (SearchFragment) getSupportFragmentManager().findFragmentById(R.id.fragment);

        searchFragment.setListener(this);
        searchFragment.setSearchView(searchView);
    }

    @Override
    public View onCreateView(String name, Context context, AttributeSet attrs) {
        return super.onCreateView(name, context, attrs);

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
}
