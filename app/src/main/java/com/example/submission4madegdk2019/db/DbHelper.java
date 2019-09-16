package com.example.submission4madegdk2019.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.example.submission4madegdk2019.db.DbContract.MovieListFavorite.TABLE_MOVIE;
import static com.example.submission4madegdk2019.db.DbContract.TvListFavorite.TABLE_TV_SHOW;

public class DbHelper  extends SQLiteOpenHelper {

    private static  final String DATABASE_NAME = "movielist";
    private static final int DATABASE_VERSION = 1;
    private static final String SQL_CREATE_TABLE_MOVIE_FAV = String.format("CREATE TABLE %s"
            + " (%s INTEGER PRIMARY KEY AUTOINCREMENT," +
            " %s TEXT NOT NULL," +
            " %s TEXT NOT NULL," +
            " %s TEXT NOT NULL," +
            " %s TEXT NOT NULL," +
            " %s TEXT NOT NULL)",

            TABLE_MOVIE,
            DbContract.MovieListFavorite._ID,
            DbContract.MovieListFavorite.MOVIE_TITLE,
            DbContract.MovieListFavorite.MOVIE_OVERVIEW,
            DbContract.MovieListFavorite.MOVIE_RELEASE_DATE,
            DbContract.MovieListFavorite.MOVIE_PHOTO,
            DbContract.MovieListFavorite.MOVIE_VOTE_AVERAGE
    );

    private static final String SQL_CREATE_TABLE_TV_SHOW_FAV = String.format("CREATE TABLE %s"
                    + " (%s INTEGER PRIMARY KEY AUTOINCREMENT," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL)",

            TABLE_TV_SHOW,
            DbContract.TvListFavorite._ID,
            DbContract.TvListFavorite.TV_NAME,
            DbContract.TvListFavorite.TV_OVERVIEW,
            DbContract.TvListFavorite.TV_FIRST_AIR_DATE,
            DbContract.TvListFavorite.TV_PHOTO,
            DbContract.TvListFavorite.TV_VOTE_AVERAGE
    );


    DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE_MOVIE_FAV);
        db.execSQL(SQL_CREATE_TABLE_TV_SHOW_FAV);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MOVIE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TV_SHOW);
        onCreate(db);
    }
}
