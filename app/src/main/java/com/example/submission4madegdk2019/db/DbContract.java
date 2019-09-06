package com.example.submission4madegdk2019.db;

import android.provider.BaseColumns;

public class DbContract {
    static final class MovieFavorite implements BaseColumns{
        static final String TABLE_NAME = "movie_favorite";
        static final String TITLE = "title";
        static final String DESCRIPTION = "description";
        static final String RELEASE_DATE = "release_date";
        static final String PHOTO = "photo";
        static final String VOTE_AVERAGE = "vote_avergae";

    }
}
