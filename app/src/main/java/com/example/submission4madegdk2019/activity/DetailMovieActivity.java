package com.example.submission4madegdk2019.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.submission4madegdk2019.R;
import com.example.submission4madegdk2019.db.MovieFavHelper;
import com.example.submission4madegdk2019.model.MovieFav;
import com.example.submission4madegdk2019.model.Movies;
import com.example.submission4madegdk2019.viewModel.MovieViewModel;



public class DetailMovieActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String SEND_MOVIE = "send_movie";

    private ProgressBar progressBar;

    public TextView  tv_title, tv_release, tv_vote_average, tv_overview, tv_url_image;
    Button btnSaveMov;

    ImageView imageMov;


    public static final String SEND_MOVIE_FAV = "send_movie_fav";
    public static final String SEND_POSITION = "send_position";
    private MovieFav movieFav;
    private int position;

    private MovieFavHelper movieFavHelper;
    private boolean isInsert = false;

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
            position = getIntent().getIntExtra(SEND_POSITION,0);
            isInsert  = true;
            btnSaveMov.setVisibility(View.GONE);
        } else {
            movieFav = new MovieFav();


            final Handler handler = new Handler();

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try{
                        Thread.sleep(30);
                    } catch (Exception e){
                    }
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            Movies movies = getIntent().getParcelableExtra(SEND_MOVIE);
                            String url_picMov = "https://image.tmdb.org/t/p/w500" + movies.getPoster_path();
                            String vote_average = Double.toString(movies.getVote_average());

                            tv_vote_average.setText(vote_average);
                            tv_title.setText(movies.getTitle());
                            tv_release.setText(movies.getRelease_date());
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


    public void onClick(View view) {
        if (view.getId() == R.id.btn_love) {

            String titles = tv_title.getText().toString().trim();
            String overview = tv_overview.getText().toString().trim();
            String release_date = tv_release.getText().toString().trim();
            String vote_average = tv_vote_average.getText().toString().trim();

            String url_poster = tv_url_image.getText().toString().trim();

            movieFav.setTitle(titles);
            movieFav.setOverview(overview);
            movieFav.setRelease_date(release_date);
            movieFav.setVote_average(vote_average);
            movieFav.setPoster_path(url_poster);

            Intent intent = new Intent();
            intent.putExtra(SEND_MOVIE_FAV, movieFav);
            intent.putExtra(SEND_POSITION, position);


            if (!isInsert) {

                long result = movieFavHelper.insertMovie(movieFav);

                if (result > 0) {
                    movieFav.setId((int) result);
                    setResult(RESULT_ADD, intent);
                    Toast.makeText(DetailMovieActivity.this, getString(R.string.success_add), Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(DetailMovieActivity.this, getString(R.string.failed_add), Toast.LENGTH_SHORT).show();
                    finish();
                }


            }
        }
    }

}
