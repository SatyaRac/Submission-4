package com.example.submission4madegdk2019.model;

import android.os.Parcel;
import android.os.Parcelable;

public class MovieFav implements Parcelable {

    private int id;
    private String title;
    private String posterPath;
    private String releaseDate;
    private String overview;
    private String voteAverage;

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

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(String voteAverage) {
        this.voteAverage = voteAverage;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.title);
        dest.writeString(this.posterPath);
        dest.writeString(this.releaseDate);
        dest.writeString(this.overview);
        dest.writeValue(this.voteAverage);
    }

    public MovieFav(Parcel in) {
        this.id = in.readInt();
        this.title = in.readString();
        this.posterPath = in.readString();
        this.releaseDate = in.readString();
        this.overview = in.readString();
        this.voteAverage = (String) in.readValue(Double.class.getClassLoader());
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
