package com.example.submission4madegdk2019.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.submission4madegdk2019.R;
import com.example.submission4madegdk2019.db.MovieFavHelper;
import com.example.submission4madegdk2019.model.MovieFav;
import com.example.submission4madegdk2019.model.Movies;

public class DetailMovieActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String SEND_MOVIE = "send_movie";

    public ProgressBar progressBar;

    public TextView tv_title, tv_release, tv_vote_average, tv_overview, tv_url_image;
    Button btnSaveMov;

    ImageView imageMov;

    public static final String SEND_MOVIE_FAV = "send_movie_fav";
    public static final String SEND_MOVIE_POSITION = "send_movie_position";

    private MovieFav movieFav;
    private int postion;

    private MovieFavHelper movieFavHelper;
    private boolean isEdit = false;
    public static final int RESULT_ADD = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movie);

        tv_title = findViewById(R.id.tv_title_mov);
        tv_release = findViewById(R.id.tv_release_mov);
        tv_vote_average = findViewById(R.id.tv_vote_average_mov);
        tv_overview = findViewById(R.id.tv_overview_mov);
        tv_url_image = findViewById(R.id.tv_url_image_mov);
        progressBar = findViewById(R.id.progress_Bar);
        imageMov = findViewById(R.id.iv_poster_mov);
        btnSaveMov = findViewById(R.id.btn_love);

        btnSaveMov.setOnClickListener(this);

        movieFavHelper = MovieFavHelper.getInstance(getApplicationContext());
        movieFavHelper.open();
        movieFav = getIntent().getParcelableExtra(SEND_MOVIE_FAV);
        if (movieFav != null){
            postion = getIntent().getIntExtra(SEND_MOVIE_POSITION,0);
            isEdit  = true;
            btnSaveMov.setVisibility(View.GONE);
        } else {
            movieFav = new MovieFav();
        }
        if (savedInstanceState != null){
            progressBar.setVisibility(View.INVISIBLE);
            Movies movies = getIntent().getParcelableExtra(SEND_MOVIE);

            String url_picMov = "https://image.tmdb.org/t/p/w500" + movies.getPosterPath();
            String vote_average = Double.toString(movies.getVoteAverage());

            tv_vote_average.setText(vote_average);
            tv_title.setText(movies.getTitle());
            tv_release.setText(movies.getReleaseDate());
            tv_overview.setText(movies.getOverview());
            tv_url_image.setText(url_picMov);

            Glide.with(DetailMovieActivity.this)
                    .load(url_picMov)
                    .placeholder(R.color.primaryColor)
                    .override(50,75)
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            progressBar.setVisibility(View.VISIBLE);
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            progressBar.setVisibility(View.GONE);
                            return false;
                        }
                    })
                    .into(imageMov);
        }else{
            final Handler handler = new Handler();

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try{
                        Thread.sleep(5000);
                    } catch (Exception e){
                    }
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            Movies movies = getIntent().getParcelableExtra(SEND_MOVIE);
                            String url_picMov = "https://image.tmdb.org/t/p/w500" + movies.getPosterPath();
                            String vote_average = Double.toString(movies.getVoteAverage());

                            tv_vote_average.setText(vote_average);
                            tv_title.setText(movies.getTitle());
                            tv_release.setText(movies.getReleaseDate());
                            tv_overview.setText(movies.getOverview());
                            tv_url_image.setText(url_picMov);

                            Glide.with(DetailMovieActivity.this)
                                    .load(url_picMov)
                                    .override(70,50)
                                    .placeholder(R.color.primaryColor)
                                    .into(imageMov);
                            progressBar.setVisibility(View.INVISIBLE);

                        }
                    });
                }
            }).start();
        }

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public void onClick(View view) {
        if (view.getId() == R.id.btn_favorite) {
            String titles       = tv_title.getText().toString().trim();
            String overviews    = tv_overview.getText().toString().trim();
            String release_date = tv_release.getText().toString().trim();
            String vote_average = tv_vote_average.getText().toString().trim();

            String url_poster = tv_url_image.getText().toString().trim();
            movieFav.setTitle(titles);
            movieFav.setOverview(overviews);
            movieFav.setReleaseDate(release_date);
            movieFav.setVoteAverage(vote_average);
            movieFav.setPosterPath(url_poster);

            Intent intent = new Intent();
            intent.putExtra(SEND_MOVIE_FAV, movieFav);
            intent.putExtra(SEND_MOVIE_POSITION, postion);

            if (!isEdit) {

                long result = movieFavHelper.insertMovie(movieFav);

                if (result > 0) {
                    movieFav.setId((int) result);
                    setResult(RESULT_ADD, intent);
                    Toast.makeText(DetailMovieActivity.this, getString(R.string.success_add), Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(DetailMovieActivity.this, getString(R.string.failed_add), Toast.LENGTH_SHORT).show();
                }
            }

        }
    }
}
