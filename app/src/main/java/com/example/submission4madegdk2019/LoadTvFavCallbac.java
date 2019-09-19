package com.example.submission4madegdk2019;

import com.example.submission4madegdk2019.model.TvFav;

import java.util.ArrayList;

public interface LoadTvFavCallbac {
    void preExecute();
    void posExecute(ArrayList<TvFav> tvFav);
}
