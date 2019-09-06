package com.example.submission4madegdk2019.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import static com.example.submission4madegdk2019.db.DbContract.MovieFavorite.TABLE_NAME;

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

            TABLE_NAME,
            DbContract.MovieFavorite._ID,
            DbContract.MovieFavorite.TITLE,
            DbContract.MovieFavorite.DESCRIPTION,
            DbContract.MovieFavorite.RELEASE_DATE,
            DbContract.MovieFavorite.PHOTO,
            DbContract.MovieFavorite.VOTE_AVERAGE
    );

    DatabaseHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public DbHelper(@Nullable Context context) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE_MOVIE_FAV);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS" + TABLE_NAME);
        onCreate(db);
    }
}
