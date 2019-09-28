package com.example.submission4madegdk2019.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.submission4madegdk2019.model.TvFav;

import java.util.ArrayList;

import static android.provider.BaseColumns._ID;
import static com.example.submission4madegdk2019.db.DbContract.TvListFavorite.ID_TV;
import static com.example.submission4madegdk2019.db.DbContract.TvListFavorite.TABLE_TV_SHOW;
import static com.example.submission4madegdk2019.db.DbContract.TvListFavorite.TV_FIRST_AIR_DATE;
import static com.example.submission4madegdk2019.db.DbContract.TvListFavorite.TV_NAME;
import static com.example.submission4madegdk2019.db.DbContract.TvListFavorite.TV_OVERVIEW;
import static com.example.submission4madegdk2019.db.DbContract.TvListFavorite.TV_POSTER_PATH;
import static com.example.submission4madegdk2019.db.DbContract.TvListFavorite.TV_VOTE_AVERAGE;

public class TvFavHelper {
    private static final String DATABASE_TABLE = TABLE_TV_SHOW;
    private static DbHelper dbHelper;
    private static TvFavHelper INSTANCE;
    private static SQLiteDatabase sqLiteDatabase;

    private TvFavHelper(Context context){
        dbHelper = new DbHelper(context);
    }
    public static TvFavHelper getInstance(Context context){
        if (INSTANCE == null){
            synchronized (SQLiteOpenHelper.class){
                if (INSTANCE == null){
                    INSTANCE = new TvFavHelper(context);
                }
            }
        }
        return INSTANCE;
    }
    public void open() throws SQLException {
        sqLiteDatabase = dbHelper.getWritableDatabase();
    }
    public void close() {
        dbHelper.close();

        if (sqLiteDatabase.isOpen())
            sqLiteDatabase.close();
    }
    public ArrayList<TvFav> getAllTvFav() {
        ArrayList<TvFav> arrayList = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.query(DATABASE_TABLE, null,
                null,
                null,
                null,
                null,
                ID_TV + " ASC",
                null);
        cursor.moveToFirst();
        TvFav tvFav;
        if (cursor.getCount() > 0) {
            do {
                tvFav = new TvFav();
                tvFav.setId(cursor.getInt(cursor.getColumnIndexOrThrow(ID_TV)));
                tvFav.setTv_name(cursor.getString(cursor.getColumnIndexOrThrow(TV_NAME)));
                tvFav.setTv_overview(cursor.getString(cursor.getColumnIndexOrThrow(TV_OVERVIEW)));
                tvFav.setTv_first_air_date(cursor.getString(cursor.getColumnIndexOrThrow(TV_FIRST_AIR_DATE)));
                tvFav.setTv_vote_average(cursor.getString(cursor.getColumnIndexOrThrow(TV_VOTE_AVERAGE)));
                tvFav.setTv_poster_path(cursor.getString(cursor.getColumnIndexOrThrow(TV_POSTER_PATH)));

                arrayList.add(tvFav);
                cursor.moveToNext();

            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }

    public long insertTv(TvFav tvFav) {
        ContentValues args = new ContentValues();
        args.put(ID_TV, tvFav.getId());
        args.put(TV_NAME, tvFav.getTv_name());
        args.put(TV_OVERVIEW, tvFav.getTv_overview());
        args.put(TV_FIRST_AIR_DATE, tvFav.getTv_first_air_date());
        args.put(TV_VOTE_AVERAGE, tvFav.getTv_vote_average());
        args.put(TV_POSTER_PATH, tvFav.getTv_poster_path());
        return sqLiteDatabase.insert(DATABASE_TABLE, null, args);
    }
    public int deleteTv(int id) {
        return sqLiteDatabase.delete(TABLE_TV_SHOW, ID_TV + " = '" + id + "'", null);
    }


}
