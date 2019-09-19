
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
import com.example.submission4madegdk2019.db.TvFavHelper;
import com.example.submission4madegdk2019.model.TvFav;
import com.example.submission4madegdk2019.model.TvShow;

public class DetailTvActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String SEND_TV = "send_tv";
    private ProgressBar progressBar;
    public TextView tvName, tvFirstAirDate, tvVoteAverage, tvOverview, tvUrlPicTv;
    Button btnSaveTv;
    ImageView imageTv;

    public static final String SEND_TV_FAV = "send_tv_fav";
    public static final String SEND_POSITION = "send_position";

    private TvFav tvFav;
    private int position;

    private TvFavHelper tvFavHelper;
    private boolean isEdit = false;
    public static final int RESULT_ADD = 101;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_tv);

        tvName = findViewById(R.id.tv_name_tv);
        tvOverview = findViewById(R.id.tv_overview_tv);
        tvVoteAverage = findViewById(R.id.tv_vote_average_tv);
        tvUrlPicTv = findViewById(R.id.tv_url_image_tv);
        tvFirstAirDate = findViewById(R.id.tv_first_air_date_tv);
        progressBar = findViewById(R.id.progress_Bar);
        imageTv = findViewById(R.id.iv_poster_tv);
        btnSaveTv = findViewById(R.id.btn_favoriteTv);

        btnSaveTv.setOnClickListener(this);

        tvFavHelper = TvFavHelper.getInstance(getApplicationContext());
        tvFavHelper.open();
        tvFav = getIntent().getParcelableExtra(SEND_TV_FAV);if (tvFav != null){
            position = getIntent().getIntExtra(SEND_POSITION,0);
            isEdit  = true;
            btnSaveTv.setVisibility(View.GONE);
        } else {
            tvFav = new TvFav();
        }
        if (savedInstanceState != null){
            progressBar.setVisibility(View.INVISIBLE);
            TvShow tvShow = getIntent().getParcelableExtra(SEND_TV);

            String url_picTv = "https://image.tmdb.org/t/p/w500" + tvShow.getPosterPath();
            String vote_average = Double.toString(tvShow.getVoteAverage());

            tvVoteAverage.setText(vote_average);
            tvName.setText(tvShow.getName());
            tvFirstAirDate.setText(tvShow.getFirstAirDate());
            tvOverview.setText(tvShow.getOverview());
            tvUrlPicTv.setText(url_picTv);

            Glide.with(DetailTvActivity.this)
                    .load(url_picTv)
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
                    .into(imageTv);
        }else{
            final Handler handler = new Handler();

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try{
                        Thread.sleep(3000);
                    } catch (Exception e){
                    }
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            TvShow tvShow = getIntent().getParcelableExtra(SEND_TV);
                            String url_picTv = "https://image.tmdb.org/t/p/w500" + tvShow.getPosterPath();
                            String vote_average = Double.toString(tvShow.getVoteAverage());

                            tvVoteAverage.setText(vote_average);
                            tvName.setText(tvShow.getName());
                            tvFirstAirDate.setText(tvShow.getFirstAirDate());
                            tvOverview.setText(tvShow.getOverview());
                            tvUrlPicTv.setText(url_picTv);

                            Glide.with(DetailTvActivity.this)
                                    .load(url_picTv)
                                    .override(70,50)
                                    .placeholder(R.color.primaryColor)
                                    .into(imageTv);
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

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_favorite){
            String tv_name       = tvName.getText().toString().trim();
            String tv_overview    = tvOverview.getText().toString().trim();
            String tv_first_air_date = tvFirstAirDate.getText().toString().trim();
            String tv_vote_average = tvVoteAverage.getText().toString().trim();

            String tvurl_poster = tvUrlPicTv.getText().toString().trim();
            tvFav.setTv_name(tv_name);
            tvFav.setTv_overview(tv_overview);
            tvFav.setTv_first_air_date(tv_first_air_date);
            tvFav.setTv_vote_average(tv_vote_average);
            tvFav.setTv_poster_path(tvurl_poster);

            Intent intent = new Intent();
            intent.putExtra(SEND_TV_FAV, tvFav);
            intent.putExtra(SEND_POSITION, position);

            if (!isEdit) {

                long result = tvFavHelper.insertTv(tvFav);

                if (result > 0) {
                    tvFav.setId((int) result);
                    setResult(RESULT_ADD, intent);
                    Toast.makeText(DetailTvActivity.this, getString(R.string.success_add), Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(DetailTvActivity.this, getString(R.string.failed_add), Toast.LENGTH_SHORT).show();
                }
            }
        }

    }
}
