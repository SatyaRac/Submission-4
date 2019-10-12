package com.example.submission4madegdk2019.activity;

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

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.submission4madegdk2019.R;
import com.example.submission4madegdk2019.db.TvFavHelper;
import com.example.submission4madegdk2019.model.TvFav;

public class DetailTvFavoriteActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String SEND_TV_FAVORITE = "send_tv_favorite";
    public static final String SEND_POSITION = "send_position";

    TextView tvNameTvFav;
    TextView tvFirstAirDAteTvFav;
    TextView tvVoteAverageTvFav;
    TextView tvOverviewTvFav;
    TextView tvUrlTvFav;
    ImageView imageView;
    Button btnDeleteTvFav;

    private ProgressBar progressBar;

    public static final int REQUEST_UPDATE = 200;
    public static final int RESULT_DELETE = 301;

    private final int ALERT_DIALOG_CLOSE = 10;
    private final int ALERT_DIALOG_DELETE = 20;

    private TvFav tvFav;
    private int position;

    private TvFavHelper tvFavHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_tv_favorite);

        tvNameTvFav = findViewById(R.id.tv_name_tv_fav);
        tvFirstAirDAteTvFav = findViewById(R.id.tv_first_air_date_tv_fav);
        tvOverviewTvFav = findViewById(R.id.tv_overview_tv_fav);
        tvVoteAverageTvFav = findViewById(R.id.tv_vote_average_tv_fav);
        tvUrlTvFav = findViewById(R.id.tv_url_image_tv_fav);

        imageView = findViewById(R.id.iv_poster_tv_fav);

        btnDeleteTvFav = findViewById(R.id.btn_delete);
        btnDeleteTvFav.setOnClickListener(this);

        tvFavHelper = TvFavHelper.getInstance(getApplicationContext());
        tvFav = getIntent().getParcelableExtra(SEND_TV_FAVORITE);
        progressBar = findViewById(R.id.progress_Bar);

        if (savedInstanceState != null){
            progressBar.setVisibility(View.INVISIBLE);
            TvFav tvFav = getIntent().getParcelableExtra(SEND_TV_FAVORITE);

            tvNameTvFav.setText(tvFav.getTv_name());
            tvFirstAirDAteTvFav.setText(tvFav.getTv_first_air_date());
            tvVoteAverageTvFav.setText(tvFav.getTv_vote_average());
            tvOverviewTvFav.setText(tvFav.getTv_overview());

            Glide.with(DetailTvFavoriteActivity.this)
                    .load(tvFav.getTv_poster_path())
                    .placeholder(R.color.primaryColor)
                    .override(50,75)
                    .into(imageView);
        } else {
            progressBar.setVisibility(View.VISIBLE);
            final Handler handler = new Handler();

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(30);
                    } catch (Exception e){

                    }handler.post(new Runnable() {
                        @Override
                        public void run() {
                            TvFav tvFav = getIntent().getParcelableExtra(SEND_TV_FAVORITE);

                            tvNameTvFav.setText(tvFav.getTv_name());
                            tvFirstAirDAteTvFav.setText(tvFav.getTv_first_air_date());
                            tvVoteAverageTvFav.setText(tvFav.getTv_vote_average());
                            tvOverviewTvFav.setText(tvFav.getTv_overview());

                            Glide.with(DetailTvFavoriteActivity.this)
                                    .load(tvFav.getTv_poster_path())
                                    .placeholder(R.color.primaryColor)
                                    .override(50,75)
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
            dialogTitle   = getString(R.string.notify_delete_tv);

            alertDialogBuilder.setTitle(dialogTitle);
            alertDialogBuilder
                    .setMessage(dialogMessage)
                    .setCancelable(false)
                    .setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            long result = tvFavHelper.deleteTv(tvFav.getId());
                            if (result > 0) {
                                Intent intent = new Intent(getBaseContext(), MainActivity.class);
                                intent.putExtra(SEND_POSITION, position);
                                startActivity(intent);
                                finish();
                            } else {
                                Toast.makeText(DetailTvFavoriteActivity.this, getString(R.string.notify_failed_delete_data), Toast.LENGTH_SHORT).show();
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
    protected void onDestroy() {
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


