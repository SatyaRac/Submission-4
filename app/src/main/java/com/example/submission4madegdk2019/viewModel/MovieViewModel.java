package com.example.submission4madegdk2019.viewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class MovieViewModel extends ViewModel {
    private static final String KEY = "8bb4ed2c9577dbd754635bae6c2ca0d9";
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
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        })
    }
}
