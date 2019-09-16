package com.example.submission4madegdk2019.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.submission4madegdk2019.model.MovieFav;

import java.util.ArrayList;

import static android.provider.ContactsContract.Contacts.Photo.PHOTO;
import static android.provider.MediaStore.Audio.Playlists.Members._ID;
import static com.example.submission4madegdk2019.db.DbContract.MovieListFavorite.MOVIE_OVERVIEW;
import static com.example.submission4madegdk2019.db.DbContract.MovieListFavorite.MOVIE_RELEASE_DATE;
import static com.example.submission4madegdk2019.db.DbContract.MovieListFavorite.MOVIE_TITLE;
import static com.example.submission4madegdk2019.db.DbContract.MovieListFavorite.MOVIE_VOTE_AVERAGE;
import static com.example.submission4madegdk2019.db.DbContract.MovieListFavorite.TABLE_MOVIE;

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
                movieFavorite.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(MOVIE_TITLE)));
                movieFavorite.setOverview(cursor.getString(cursor.getColumnIndexOrThrow(MOVIE_OVERVIEW)));
                movieFavorite.setReleaseDate(cursor.getString(cursor.getColumnIndexOrThrow(MOVIE_RELEASE_DATE)));
                movieFavorite.setPosterPath(cursor.getString(cursor.getColumnIndexOrThrow(PHOTO)));
                movieFavorite.setVoteAverage(cursor.getString(cursor.getColumnIndexOrThrow(MOVIE_VOTE_AVERAGE)));

                arrayList.add(movieFavorite);
                cursor.moveToNext();

            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }

    public long insertMovie(MovieFav movieFavorite) {
        ContentValues args = new ContentValues();
        args.put(MOVIE_TITLE, movieFavorite.getTitle());
        args.put(MOVIE_OVERVIEW, movieFavorite.getOverview());
        args.put(MOVIE_RELEASE_DATE, movieFavorite.getReleaseDate());
        args.put(MOVIE_VOTE_AVERAGE, movieFavorite.getVoteAverage());
        args.put(PHOTO, movieFavorite.getPosterPath());
        return sqLiteDatabase.insert(DATABASE_TABLE, null, args);
    }

    public int deleteMovie(int id) {
        return sqLiteDatabase.delete(TABLE_MOVIE, _ID + " = '" + id + "'", null);
    }


}
