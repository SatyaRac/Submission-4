package com.example.submission4madegdk2019.viewModel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.submission4madegdk2019.BuildConfig;
import com.example.submission4madegdk2019.model.TvShow;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class TvViewModel extends ViewModel {
    private static final String KEY = BuildConfig.API_KEY;
    private MutableLiveData<ArrayList<TvShow>> listTv = new MutableLiveData<>();

    public void setTv(final String tv){
        AsyncHttpClient client = new AsyncHttpClient();
        final ArrayList<TvShow> listItem = new ArrayList<>();
        String urlApiTv = "https://api.themoviedb.org/3/discover/tv?api_key=" + KEY + "&language=en-US";

        client.get(urlApiTv, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String resultTv = new String(responseBody);
                    JSONObject tvObj = new JSONObject(resultTv);
                    JSONArray list = tvObj.getJSONArray("results");
                    for (int i = 0; i < list.length(); i++) {
                        JSONObject tv = list.getJSONObject(i);
                        TvShow tvShow = new TvShow(tv);
                        listItem.add(tvShow);
                    }
                    listTv.postValue(listItem);

                } catch (JSONException e) {
                    Log.d("Exception", e.getMessage());
                }
            }

                @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                    Log.d("onFailure", error.getMessage());
            }
        });
    }
    public LiveData<ArrayList<TvShow>> getTv(){
        return listTv;
    }
}
