package com.example.submission4madegdk2019.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.submission4madegdk2019.model.MovieFav;

import java.util.ArrayList;

import static android.provider.BaseColumns._ID;
import static com.example.submission4madegdk2019.db.DbContract.MovieListFavorite.ID_MOVIE;
import static com.example.submission4madegdk2019.db.DbContract.MovieListFavorite.OVERVIEW;
import static com.example.submission4madegdk2019.db.DbContract.MovieListFavorite.POSTER_PATH;
import static com.example.submission4madegdk2019.db.DbContract.MovieListFavorite.RELEASE_DATE;
import static com.example.submission4madegdk2019.db.DbContract.MovieListFavorite.TITLE;
import static com.example.submission4madegdk2019.db.DbContract.MovieListFavorite.VOTE_AVERAGE;
import static com.example.submission4madegdk2019.db.DbContract.MovieListFavorite.TABLE_MOVIE;
import static cz.msebera.android.httpclient.HttpHeaders.FROM;

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

    public void open() throws SQLException{
        sqLiteDatabase = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();

        if (sqLiteDatabase.isOpen())
            sqLiteDatabase.close();
    }

    public ArrayList<MovieFav> getAllMoviesFav() {
        ArrayList<MovieFav> arrayList = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.query(DATABASE_TABLE, null,
                null,
                null,
                null,
                null,
                _ID + " ASC",
                null);
        cursor.moveToFirst();
        MovieFav movieFavorite;
        if (cursor.getCount() > 0) {
            do {
                movieFavorite = new MovieFav();
                movieFavorite.setId(cursor.getInt(cursor.getColumnIndexOrThrow(_ID)));
                movieFavorite.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(TITLE)));
                movieFavorite.setOverview(cursor.getString(cursor.getColumnIndexOrThrow(OVERVIEW)));
                movieFavorite.setRelease_date(cursor.getString(cursor.getColumnIndexOrThrow(RELEASE_DATE)));
                movieFavorite.setVote_average(cursor.getString(cursor.getColumnIndexOrThrow(VOTE_AVERAGE)));
                movieFavorite.setPoster_path(cursor.getString(cursor.getColumnIndexOrThrow(POSTER_PATH)));

                arrayList.add(movieFavorite);
                cursor.moveToNext();

            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }

    public long insertMovie(MovieFav movieFavorite) {
        ContentValues args = new ContentValues();
        args.put(_ID, movieFavorite.getId());
        args.put(TITLE, movieFavorite.getTitle());
        args.put(OVERVIEW, movieFavorite.getOverview());
        args.put(RELEASE_DATE, movieFavorite.getRelease_date());
        args.put(VOTE_AVERAGE, movieFavorite.getVote_average());
        args.put(POSTER_PATH, movieFavorite.getPoster_path());
        return sqLiteDatabase.insert(DATABASE_TABLE, null, args);

    }



    public int deleteMovie(int id) {
        return sqLiteDatabase.delete(TABLE_MOVIE, _ID + " = '" + id + "'", null);
    }




}
