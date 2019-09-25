package com.example.submission4madegdk2019.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import com.example.submission4madegdk2019.LoadTvFavCallbac;
import com.example.submission4madegdk2019.R;
import com.example.submission4madegdk2019.adapter.TvFavAdapter;
import com.example.submission4madegdk2019.db.TvFavHelper;
import com.example.submission4madegdk2019.model.TvFav;
import com.google.android.material.snackbar.Snackbar;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import static com.example.submission4madegdk2019.activity.DetailTvFavoriteActivity.REQUEST_UPDATE;

public class ListFavTvActivity extends AppCompatActivity implements View.OnClickListener, LoadTvFavCallbac {

    private static final String SEND_STATE = "send_state";
    private TvFavHelper tvFavHelper;
    private RecyclerView rvTvFav;
    private ProgressBar progressBar;
    private TvFavAdapter tvFavAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_fav_tv);

        tvFavHelper = TvFavHelper.getInstance(getApplicationContext());
        tvFavHelper.open();

        tvFavAdapter = new TvFavAdapter(this);

        rvTvFav = findViewById(R.id.rv_tv_favorite);
        rvTvFav.setLayoutManager(new LinearLayoutManager(this));
        rvTvFav.setHasFixedSize(true);
        progressBar = findViewById(R.id.progress_Bar);
        rvTvFav.setAdapter(tvFavAdapter);

        if (savedInstanceState == null){
            new LoadTvAsync(tvFavHelper, this).execute();
        } else {
            ArrayList<TvFav> listsTvFav = savedInstanceState.getParcelableArrayList(SEND_STATE);
            if (listsTvFav != null){
                tvFavAdapter.setTvFavList(listsTvFav);
            }
        }
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    protected void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(SEND_STATE, tvFavAdapter.getAllTvFav());
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
    public void posExecute(ArrayList<TvFav> listsTvFav) {
        progressBar.setVisibility(View.INVISIBLE);
       tvFavAdapter.setTvFavList(listsTvFav);
    }

    private static class LoadTvAsync extends AsyncTask<Void, Void, ArrayList<TvFav>> {

        private final WeakReference<TvFavHelper> weakReference;
        private final WeakReference<LoadTvFavCallbac> weakCallback;

        private LoadTvAsync(TvFavHelper tvFavHelper, LoadTvFavCallbac callback) {
            weakReference = new WeakReference<>(tvFavHelper);
            weakCallback = new WeakReference<>(callback);
        }

        @Override
        protected ArrayList<TvFav> doInBackground(Void... voids) {

            return weakReference.get().getAllTvFav();
        }
        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            weakCallback.get().preExecute();
        }
        @Override
        protected void onPostExecute(ArrayList<TvFav> tvFav){
            super.onPostExecute(tvFav);
            weakCallback.get().posExecute(tvFav);
        }
    }
    @Override
    protected void onDestroy(){
        super.onDestroy();
        tvFavHelper.close();
    }
    @Override
    protected  void onActivityResult(int reqcode, int rescode, Intent data){
        super.onActivityResult(reqcode,rescode, data);
        if (data != null){
            if (reqcode == REQUEST_UPDATE) {
                if (rescode == DetailTvFavoriteActivity.RESULT_DELETE){
                    int pos = data.getIntExtra(DetailTvFavoriteActivity.SEND_POSITION, 0);
                    tvFavAdapter.removeItem(pos);
                    showSnackbarMessage(getString(R.string.notify_delete_tv));
                }
            }
        }
    }
    private void showSnackbarMessage(String message){
        Snackbar.make(rvTvFav, message, Snackbar.LENGTH_SHORT).show();
    }
}
