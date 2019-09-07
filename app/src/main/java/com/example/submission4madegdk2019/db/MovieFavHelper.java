package com.example.submission4madegdk2019.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.example.submission4madegdk2019.db.DbContract.ListFavorite.TABLE_MOVIE;

public class MovieFavHelper {
    private static final String DATABASE_TABLE = TABLE_MOVIE;
    private static DbHelper dbHelper;
    private static MovieFavHelper INSTANCE;
    private static SQLiteDatabase sqLiteDatabase;

    private MovieFavHelper(Context context){
        dbHelper = new DbHelper(context);
    }

    public static MovieFavHelper getInstance(Context context){
        if (INSTANCE == null){
            synchronized (SQLiteOpenHelper.class){
                if (INSTANCE == null){
                    INSTANCE = new MovieFavHelper(context);
                }
            }
        }
        return INSTANCE;
    }

}
