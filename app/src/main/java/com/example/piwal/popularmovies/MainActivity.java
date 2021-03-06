package com.example.piwal.popularmovies;

import android.content.Context;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;

import com.example.piwal.popularmovies.data.MovieData;
import com.example.piwal.popularmovies.utilities.NetworkUtils;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private PopularMovieAdapter mPopularMovieAdapter;
    private RecyclerView mRecyclerView;
    private int currentSortOrder = NetworkUtils.SORT_MOST_POPULAR;
    private int currentPosition = -1;
    private int currentSortSelected = R.id.action_sort_most_popular;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState != null && savedInstanceState.containsKey(getString(R.string.state_sort_order)))
            currentSortOrder = savedInstanceState.getInt(getString(R.string.state_sort_order));
        if (savedInstanceState != null && savedInstanceState.containsKey(getString(R.string.state_grid_view_position)))
            currentPosition = savedInstanceState.getInt(getString(R.string.state_grid_view_position));
        if (savedInstanceState != null && savedInstanceState.containsKey(getString(R.string.state_action_sort_order)))
            currentSortSelected = savedInstanceState.getInt(getString(R.string.state_action_sort_order));

        mRecyclerView = (RecyclerView) findViewById(R.id.movies_recycler_view);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            gridLayoutManager.setOrientation(GridLayoutManager.HORIZONTAL);
            gridLayoutManager.setSpanCount(1);
        }
        mRecyclerView.setLayoutManager(gridLayoutManager);

        if (mPopularMovieAdapter == null) {
            mPopularMovieAdapter = new PopularMovieAdapter(new ArrayList<MovieData>(), this);
        }
        mRecyclerView.setAdapter(mPopularMovieAdapter);

        loadMovieData(currentSortOrder);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        currentSortOrder = savedInstanceState.getInt(getString(R.string.state_sort_order));
        currentPosition = savedInstanceState.getInt(getString(R.string.state_grid_view_position));
        currentSortSelected = savedInstanceState.getInt(getString(R.string.state_action_sort_order));
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(getString(R.string.state_sort_order),currentSortOrder);
        currentPosition = ((GridLayoutManager)mRecyclerView.getLayoutManager()).findFirstVisibleItemPosition();
        outState.putInt(getString(R.string.state_grid_view_position),currentPosition);
        outState.putInt(getString(R.string.state_action_sort_order), currentSortSelected);
        super.onSaveInstanceState(outState);
    }

    private void loadMovieData(int sort) {
        currentSortOrder = sort;
        new FetchMoviesTask(sort, mPopularMovieAdapter, this, getWindow().findViewById(Window.ID_ANDROID_CONTENT), currentPosition).execute();
        getSupportActionBar().setSubtitle((currentSortOrder == NetworkUtils.SORT_MOST_POPULAR ?
                R.string.menu_most_popular : R.string.menu_highest_rated));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemThatWasClickedId = item.getItemId();

        if(itemThatWasClickedId != currentSortSelected) {
            currentPosition = -1;
            currentSortSelected = itemThatWasClickedId;
            switch (itemThatWasClickedId) {
                case R.id.action_sort_most_popular:
                    loadMovieData(NetworkUtils.SORT_MOST_POPULAR);
                    getSupportActionBar().setSubtitle(R.string.menu_most_popular);
                    return true;
                case R.id.action_sort_highest_rated:
                    loadMovieData(NetworkUtils.SORT_HIGHEST_RATED);
                    getSupportActionBar().setSubtitle(R.string.menu_highest_rated);
                    return true;
            }
        }



        return super.onOptionsItemSelected(item);
    }
}
