package com.example.submission4madegdk2019.viewModel;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.submission4madegdk2019.R;
import com.example.submission4madegdk2019.model.Movies;
import com.example.submission4madegdk2019.model.TvShow;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.example.submission4madegdk2019.BuildConfig.API_KEY;

public class SearchViewModel extends ViewModel {
    private MutableLiveData<ArrayList<Movies>> movieRes = new MutableLiveData<>();
    private MutableLiveData<ArrayList<TvShow>> tvshowRes = new MutableLiveData<>();

    public void setResult(final Context context, boolean isMovie, String query) {
        if (isMovie) {
            String urlmovie = "https://api.themoviedb.org/3/search/movie?api_key=" + API_KEY + "&language=en-US&query=" + query;
            final ArrayList<Movies> list = new ArrayList<>();
            JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.GET, urlmovie, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        JSONArray responseArray = response.getJSONArray("results");
                        for (int i = 0; i < responseArray.length(); i++) {
                            JSONObject movie = responseArray.getJSONObject(i);
                            Movies moviesItem = new Movies(movie);
                            list.add(moviesItem);
                        }
                        movieRes.postValue(list);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("onFailur", String.valueOf(error));
                    Toast.makeText(context, R.string.cant_load_the_data, Toast.LENGTH_SHORT).show();
                }
            });
            RequestQueue queue = Volley.newRequestQueue(context);
            queue.add(objectRequest);
        } else if (!isMovie) {
            String url_tv = "https://api.themoviedb.org/3/search/tv?api_key=" + API_KEY + "&language=en-US&query=" + query;
            final ArrayList<TvShow> list = new ArrayList<>();
            JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.GET, url_tv, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        JSONArray responseArray = response.getJSONArray("results");
                        for (int i = 0; i < responseArray.length(); i++) {
                            JSONObject tv = responseArray.getJSONObject(i);
                            TvShow tvitem = new TvShow(tv);
                            list.add(tvitem);
                        }
                        tvshowRes.postValue(list);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("onFailure", String.valueOf(error));
                    Toast.makeText(context, R.string.cant_load_the_data, Toast.LENGTH_SHORT).show();
                }
            });
            RequestQueue queue = Volley.newRequestQueue(context);
            queue.add(objectRequest);
        }
    }

    public LiveData<ArrayList<Movies>> getMovies(){
        return movieRes;
    }

    public LiveData<ArrayList<TvShow>> getTvShow(){
        return tvshowRes;
    }
}
