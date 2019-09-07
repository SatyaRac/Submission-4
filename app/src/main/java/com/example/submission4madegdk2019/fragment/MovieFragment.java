package com.example.submission4madegdk2019.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.submission4madegdk2019.R;
import com.example.submission4madegdk2019.adapter.MoviesAdapter;
import com.example.submission4madegdk2019.model.Movies;
import com.example.submission4madegdk2019.viewModel.MovieViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class MovieFragment extends Fragment {

    private RecyclerView rvMovie;
    private ProgressBar progressBar;
    private MovieViewModel movieViewModel;
    private MoviesAdapter moviesAdapter;
    private List<Movies> movies;


    public MovieFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movie, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rvMovie = view.findViewById(R.id.menu_movie);
        progressBar = view.findViewById(R.id.progress_circular);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);

        movieViewModel = ViewModelProviders.of(this).get(MovieViewModel.class);
        movieViewModel.getMovies().observe(this, getMovies);

        moviesAdapter = new MoviesAdapter(getActivity());
        moviesAdapter.notifyDataSetChanged();

        rvMovie.setHasFixedSize(true);
        rvMovie.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvMovie.setAdapter(moviesAdapter);

        movieViewModel.setMovies();
        showLoading(false);
    }

    private Observer<ArrayList<Movies>> getMovies = new Observer<ArrayList<Movies>>() {
        @Override
        public void onChanged(ArrayList<Movies> movies) {
            if (movies != null){
                moviesAdapter.setData(movies);
                showLoading(true);
            }
        }
    };

    private void showLoading(Boolean b){
        if (b){
            progressBar.setVisibility(View.GONE);
        }else {
            progressBar.setVisibility(View.VISIBLE);
        }
    }

}
