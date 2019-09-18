package com.example.submission4madegdk2019.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import com.example.submission4madegdk2019.LoadMovFavCallbac;
import com.example.submission4madegdk2019.R;
import com.example.submission4madegdk2019.adapter.MoviesFavAdapter;
import com.example.submission4madegdk2019.db.MovieFavHelper;
import com.example.submission4madegdk2019.model.MovieFav;
import com.google.android.material.snackbar.Snackbar;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import static com.example.submission4madegdk2019.activity.DetailMovieFavoriteActivity.REQUEST_UPDATE;

public class ListFavMovActivity extends AppCompatActivity implements View.OnClickListener, LoadMovFavCallbac {

    private static final String SEND_STATE = "SEND_STATE";
    private MovieFavHelper movieFavHelper;
    private RecyclerView rvMovFav;
    private ProgressBar progressBar;
    private MoviesFavAdapter moviesFavAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_fav_mov);

        movieFavHelper = MovieFavHelper.getInstance(getApplicationContext());
        movieFavHelper.open();

        rvMovFav = findViewById(R.id.rv_movie_favorite);
        rvMovFav.setLayoutManager(new LinearLayoutManager(this));
        rvMovFav.setHasFixedSize(true);

        moviesFavAdapter = new MoviesFavAdapter(this);
        progressBar = findViewById(R.id.progress_Bar);
        rvMovFav.setAdapter(moviesFavAdapter);

        if (savedInstanceState == null) {
            new LoadMovAsync(movieFavHelper, this).execute();
        } else {
            ArrayList<MovieFav> movieFavArrayList = savedInstanceState.getParcelableArrayList(SEND_STATE);
            if (movieFavArrayList != null) {
                moviesFavAdapter.setMovieFavList(movieFavArrayList);
            }
        }
    }


    @Override
    public void onClick(View v) {

    }

    @Override
    protected void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(SEND_STATE, moviesFavAdapter.getAllMoviesFav());
    }

    @Override
    public void preExecute() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progressBar.setVisibility(View.VISIBLE);
            }
        });

    }

    @Override
    public void posExecute(ArrayList<MovieFav> movieFav) {
        progressBar.setVisibility(View.INVISIBLE);
        moviesFavAdapter.setMovieFavList(movieFav);
    }


    private static class LoadMovAsync
            extends AsyncTask<Void, Void, ArrayList<MovieFav>> {

        private final WeakReference<MovieFavHelper> weakReference;
        private final WeakReference<LoadMovFavCallbac> weakCallback;

        private LoadMovAsync(MovieFavHelper movieFavHelper, LoadMovFavCallbac callback) {
            weakReference = new WeakReference<>(movieFavHelper);
            weakCallback = new WeakReference<>(callback);
        }

        @Override
        protected ArrayList<MovieFav> doInBackground(Void... voids) {

            return weakReference.get().getAllMoviesFav();
        }
        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            weakCallback.get().preExecute();
        }
        @Override
        protected void onPostExecute(ArrayList<MovieFav> movieFav){
            super.onPostExecute(movieFav);
            weakCallback.get().posExecute(movieFav);
        }
    }
    @Override
    protected void onDestroy(){
        super.onDestroy();
        movieFavHelper.close();
    }
    @Override
    protected  void onActivityResult(int reqcode, int rescode, Intent data){
        super.onActivityResult(reqcode,rescode, data);
        if (data != null){
            if (reqcode == REQUEST_UPDATE) {
                if (rescode == DetailMovieFavoriteActivity.RESULT_DELETE){
                    int pos = data.getIntExtra(DetailMovieFavoriteActivity.SEND_POSITION, 0);
                    moviesFavAdapter.removeItem(pos);
                    showSnackbarMessage(getString(R.string.notify_delete_mov));
                }
            }
        }
    }
    private void showSnackbarMessage(String message){
        Snackbar.make(rvMovFav, message, Snackbar.LENGTH_SHORT).show();
    }

}
