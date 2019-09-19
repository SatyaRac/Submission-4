package com.example.submission4madegdk2019.model;

import android.os.Parcel;
import android.os.Parcelable;

public class TvFav extends MovieFav implements Parcelable {
    private int id;
    private String tv_name;
    private String tv_poster_path;
    private String tv_first_air_date;
    private String tv_overview;
    private String tv_vote_average;

    public TvFav(){

    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTv_name() {
        return tv_name;
    }

    public void setTv_name(String tv_name) {
        this.tv_name = tv_name;
    }

    public String getTv_poster_path() {
        return tv_poster_path;
    }

    public void setTv_poster_path(String tv_poster_path) {
        this.tv_poster_path = tv_poster_path;
    }

    public String getTv_first_air_date() {
        return tv_first_air_date;
    }

    public void setTv_first_air_date(String tv_first_air_date) {
        this.tv_first_air_date = tv_first_air_date;
    }

    public String getTv_overview() {
        return tv_overview;
    }

    public void setTv_overview(String tv_overview) {
        this.tv_overview = tv_overview;
    }

    public String getTv_vote_average() {
        return tv_vote_average;
    }

    public void setTv_vote_average(String tv_vote_average) {
        this.tv_vote_average = tv_vote_average;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.tv_name);
        dest.writeString(this.tv_poster_path);
        dest.writeString(this.tv_first_air_date);
        dest.writeString(this.tv_overview);
        dest.writeString(this.tv_vote_average);
    }

    protected TvFav(Parcel in) {
        this.id = in.readInt();
        this.tv_name = in.readString();
        this.tv_poster_path = in.readString();
        this.tv_first_air_date = in.readString();
        this.tv_overview = in.readString();
        this.tv_vote_average = in.readString();
    }

    public static final Creator<TvFav> CREATOR = new Creator<TvFav>() {
        @Override
        public TvFav createFromParcel(Parcel in) {
            return new TvFav(in);
        }

        @Override
        public TvFav[] newArray(int size) {
            return new TvFav[size];
        }
    };
}
