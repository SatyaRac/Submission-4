package com.example.submission4madegdk2019.viewModel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.submission4madegdk2019.BuildConfig;
import com.example.submission4madegdk2019.model.Movies;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class MovieViewModel extends ViewModel {
    private static final String KEY = BuildConfig.API_KEY;
    private MutableLiveData<ArrayList<Movies>> listMovies = new MutableLiveData<>();

    public void setMovies(final String movies){
        AsyncHttpClient client = new AsyncHttpClient();
        final ArrayList<Movies> listItem = new ArrayList<>();
        String urlApiMovie = "https://api.themoviedb.org/3/discover/movie?api_key=" + KEY + "&language=en-US";

        client.get(urlApiMovie, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String resultMovie = new String(responseBody);
                    JSONObject movieObj = new JSONObject(resultMovie);


                    JSONArray list = movieObj.getJSONArray("results");
                    for (int i = 0 ; i < list.length(); i++){
                        JSONObject movie = list.getJSONObject(i);
                        Movies moviesI = new Movies(movie);
                        listItem.add(moviesI);
                    }
                    listMovies.postValue(listItem);
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
    public LiveData<ArrayList<Movies>> getMovies(){
        return listMovies;
    }
}
