package com.example.submission4madegdk2019.db;

import android.provider.BaseColumns;

public class DbContract {
    static final class ListFavorite implements BaseColumns{
        static final String TABLE_MOVIE = "movie_favorites";
        static final String TABLE_TV_SHOW = "tv_show_favorites";

        static final String MOVIE_TITLE = "movie_title";
        static final String MOVIE_OVERVIEW = "movie_overview";
        static final String MOVIE_RELEASE_DATE = "movie_release_date";
        static final String MOVIE_PHOTO = "movie_photo";
        static final String MOVIE_VOTE_AVERAGE = "movie_vote_average";

        static final String TV_NAME = "tv_title";
        static final String TV_OVERVIEW = "tv_overview";
        static final String TV_FIRST_AIR_DATE = "tv_first_air_date";
        static final String TV_PHOTO = "tv_photo";
        static final String TV_VOTE_AVERAGE = "tv_vote_average";



    }
}
