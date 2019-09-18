package com.example.submission4madegdk2019.model;

import android.os.Parcel;
import android.os.Parcelable;

public class MovieFav implements Parcelable {

    private int id;
    private String title;
    private String poster_path;
    private String release_date;
    private String overview;
    private String vote_average;

    public MovieFav() {

    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getVote_average() {
        return vote_average;
    }

    public void setVote_average(String vote_average) {
        this.vote_average = vote_average;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.title);
        dest.writeString(this.poster_path);
        dest.writeString(this.release_date);
        dest.writeString(this.overview);
        dest.writeValue(this.vote_average);
    }

    public MovieFav(Parcel in) {
        this.id = in.readInt();
        this.title = in.readString();
        this.poster_path = in.readString();
        this.release_date = in.readString();
        this.overview = in.readString();
        this.vote_average = (String) in.readValue(Double.class.getClassLoader());
    }

    public static final Creator<MovieFav> CREATOR = new Creator<MovieFav>() {
        @Override
        public MovieFav createFromParcel(Parcel in) {
            return new MovieFav(in);
        }

        @Override
        public MovieFav[] newArray(int size) {
            return new MovieFav[size];
        }
    };
}
