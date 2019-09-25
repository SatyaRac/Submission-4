package com.example.submission4madegdk2019.db;

import android.provider.BaseColumns;

public class DbContract {
    static final class MovieListFavorite implements BaseColumns {
        static final String TABLE_MOVIE = "movie_favorites";

        static final String ID_MOVIE = "id_movie";
        static final String TITLE = "movie_title";
        static final String OVERVIEW = "movie_overview";
        static final String RELEASE_DATE = "movie_release_date";
        static final String POSTER_PATH = "movie_photo";
        static final String VOTE_AVERAGE = "movie_vote_average";
    }
    static final class TvListFavorite implements BaseColumns{
        static final String TABLE_TV_SHOW = "tv_show_favorites";

        static final String ID_TV = "id_tv";
        static final String TV_NAME = "tv_title";
        static final String TV_OVERVIEW = "tv_overview";
        static final String TV_FIRST_AIR_DATE = "tv_first_air_date";
        static final String TV_POSTER_PATH = "tv_photo";
        static final String TV_VOTE_AVERAGE = "tv_vote_average";



    }
}
