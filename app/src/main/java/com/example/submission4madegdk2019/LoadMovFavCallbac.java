package com.example.submission4madegdk2019;

import com.example.submission4madegdk2019.model.MovieFav;

import java.util.ArrayList;

public interface LoadMovFavCallbac {
    void preExecute();
    void posExecute(ArrayList<MovieFav> movieFav);

}
