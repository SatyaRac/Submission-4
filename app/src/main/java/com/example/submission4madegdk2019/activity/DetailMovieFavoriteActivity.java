package com.example.submission4madegdk2019.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.submission4madegdk2019.R;
import com.example.submission4madegdk2019.db.MovieFavHelper;
import com.example.submission4madegdk2019.model.MovieFav;

public class DetailMovieFavoriteActivity extends AppCompatActivity implements View.OnClickListener{

    public static final String SEND_MOVIE_FAVORITE = "send_movie_favorite";
    public static final String SEND_MOVIE = "send_movie";

    TextView tvTitleMovFav;
    TextView tvReleaseDateMovFav;
    TextView tvVoteAverageMovFav;
    TextView tvOverviewMovFav;
    TextView tvUrlMovFav;
    ImageView imageView;
    Button btnDeleteMovFav;

    private ProgressBar progressBar;

    public static final int REQUEST_UPDATE = 200;
    public static final int RESULT_DELETE = 301;

    private final int ALERT_DIALOG_CLOSE = 10;
    private final int ALERT_DIALOG_DELETE = 20;

    private MovieFav movieFav;
    private int position;

    private MovieFavHelper movieFavHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movie_favorite);

        tvTitleMovFav = findViewById(R.id.tv_title_mov_fav);
        tvReleaseDateMovFav = findViewById(R.id.tv_release_mov_fav);
        tvVoteAverageMovFav = findViewById(R.id.tv_vote_average_mov_fav);
        tvOverviewMovFav = findViewById(R.id.tv_overview_mov_fav);
        tvUrlMovFav = findViewById(R.id.tv_url_image_mov_fav);

        btnDeleteMovFav = findViewById(R.id.btn_delete);
        btnDeleteMovFav.setOnClickListener(this);

        movieFavHelper = MovieFavHelper.getInstance(getApplicationContext());
        movieFav = getIntent().getParcelableExtra(SEND_MOVIE_FAVORITE);
        progressBar = findViewById(R.id.progress_Bar_fav);
        if (savedInstanceState != null) {
            progressBar.setVisibility(View.INVISIBLE);
            MovieFav movieFav = getIntent().getParcelableExtra(SEND_MOVIE_FAVORITE);

            tvTitleMovFav.setText(movieFav.getTitle());
            tvReleaseDateMovFav.setText(movieFav.getReleaseDate());
            tvVoteAverageMovFav.setText(movieFav.getVoteAverage());
            tvOverviewMovFav.setText(movieFav.getOverview());

            Glide.with(getApplicationContext())
                    .load(movieFav.getPosterPath())
                    .placeholder(R.color.primaryColor)
                    .override(50, 75)
                    .into(imageView);

        }else {
            progressBar.setVisibility(View.VISIBLE);
            final Handler handler = new Handler();

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(5000);
                    } catch (Exception e) {
                    }
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            MovieFav movieFav = getIntent().getParcelableExtra(SEND_MOVIE_FAVORITE);

                            tvTitleMovFav.setText(movieFav.getTitle());
                            tvReleaseDateMovFav.setText(movieFav.getReleaseDate());
                            tvVoteAverageMovFav.setText(movieFav.getVoteAverage());
                            tvOverviewMovFav.setText(movieFav.getOverview());

                            Glide.with(DetailMovieFavoriteActivity.this)
                                    .load(movieFav.getPosterPath())
                                    .placeholder(R.color.primaryColor)
                                    .override(50, 75)
                                    .into(imageView);
                            progressBar.setVisibility(View.INVISIBLE);
                        }

                    });
                }
            }).start();
        }


    }


    private void showAlertDialog(int type) {
        final boolean isDialogClose = type == ALERT_DIALOG_CLOSE;
        String dialogTitle, dialogMessage;

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        if (!isDialogClose) {
            dialogMessage = getString(R.string.notify_ques_delete);
            dialogTitle   = getString(R.string.notify_delete_mov);

            alertDialogBuilder.setTitle(dialogTitle);
            alertDialogBuilder
                    .setMessage(dialogMessage)
                    .setCancelable(false)
                    .setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            long result = movieFavHelper.deleteMovie(movieFav.getId());
                            if (result > 0) {
                                Intent intent = new Intent();
                                intent.putExtra(SEND_MOVIE, position);
                                setResult(RESULT_DELETE, intent);
                                finish();
                            } else {
                                Toast.makeText(DetailMovieFavoriteActivity.this, getString(R.string.notify_failed_delete_data), Toast.LENGTH_SHORT).show();
                            }
                        }
                    })
                    .setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        } else {
            finish();
        }
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
    }



    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_delete) {
            showAlertDialog(ALERT_DIALOG_DELETE);
        }

    }

    @Override
    public void onBackPressed() {
        showAlertDialog(ALERT_DIALOG_CLOSE);
    }


}

