package com.example.submission4madegdk2019.activity;

import androidx.appcompat.app.AppCompatActivity;

import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.lifecycle.Observer;


import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;


import com.example.submission4madegdk2019.R;
import com.example.submission4madegdk2019.adapter.SearchAdapter;
import com.example.submission4madegdk2019.model.Movies;
import com.example.submission4madegdk2019.model.TvShow;
import com.example.submission4madegdk2019.viewModel.SearchViewModel;

import java.util.ArrayList;


public class SearchResultActivity extends AppCompatActivity {
    public static final String SEND_QUERY = "send_query";
    public static final String SEND_TYPE = "send_type";

    SearchAdapter searchAdapter;
    ProgressBar progressBar;
    SearchViewModel searchViewModel;
    TextView txtnotFound;
    RecyclerView recyclerView;
    Toolbar toolbar;

    private Observer<ArrayList<Movies>> getMovie = new Observer<ArrayList<Movies>>(){

        @Override
        public void onChanged(ArrayList<Movies> movies) {
            if (movies != null){
                searchAdapter.setListMovie(movies);
                showLoading(false);
            } else {
                showNotFound(true);
            }
        }
    };

    private Observer<ArrayList<TvShow>> getTv = new Observer<ArrayList<TvShow>>() {
        @Override
        public void onChanged(ArrayList<TvShow> tvShows) {
            if (tvShows != null){
                searchAdapter.setListTvShow(tvShows);
                showLoading(false);
            } else {
                showNotFound(true);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);

        String query = getIntent().getStringExtra(SEND_QUERY);
        boolean isMovie = getIntent().getBooleanExtra(SEND_TYPE, true);
        toolbar = findViewById(R.id.toolbar_search);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null){
            getSupportActionBar().setTitle(R.string.search_result);
            getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        }
        progressBar = findViewById(R.id.progress_Bar_search);
        txtnotFound = findViewById(R.id.txt_not_found);
        recyclerView = findViewById(R.id.rv_search_result);

        searchViewModel = ViewModelProviders.of(this).get(SearchViewModel.class);
        if (isMovie){
            searchViewModel.getMovies().observe(this,getMovie);
            searchViewModel.setResult(getApplicationContext(), isMovie, query);
            showLoading(true);
            showNotFound(false);
        } else {
            searchViewModel.getTvShow().observe(this,getTv);
            searchViewModel.setResult(getApplicationContext(), isMovie, query);
            showLoading(true);
            showNotFound(false);
        }

        searchAdapter = new SearchAdapter();
        searchAdapter.notifyDataSetChanged();

        recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));
        recyclerView.setAdapter(searchAdapter);
    }


    private void showLoading(Boolean state) {
        if (state) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }

    private void showNotFound(Boolean state) {
        if (state) {
            recyclerView.setVisibility(View.INVISIBLE);
            txtnotFound.setVisibility(View.VISIBLE);
        } else {
            recyclerView.setVisibility(View.VISIBLE);
            txtnotFound.setVisibility(View.INVISIBLE);
        }
    }

}
