package com.example.submission4madegdk2019.model;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

public class Movies implements Parcelable {

    private int id;
    private String title;
    private String posterPath;
    private String releaseDate;
    private String overview;
    private Double voteAverage;


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

    public Double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(Double voteAverage) {
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
    protected Movies(Parcel in) {
        this.id = in.readInt();
        this.title = in.readString();
        this.posterPath = in.readString();
        this.releaseDate = in.readString();
        this.overview = in.readString();
        this.voteAverage = (Double) in.readValue(Double.class.getClassLoader());
    }
    public static final Creator<Movies> CREATOR = new Creator<Movies>() {
        @Override
        public Movies createFromParcel(Parcel in) {
            return new Movies(in);
        }

        @Override
        public Movies[] newArray(int size) {
            return new Movies[size];
        }
    };

    public Movies(JSONObject object){
        try {
            int id = object.getInt("id");
            String title = object.getString("title");
            String poster_path = object.getString("poster_path");
            String release_date = object.getString("release_date");
            String overview = object.getString("overview");
            Double vote_average = object.getDouble("vote_average");

            this.id = id;
            this.title = title;
            this.posterPath = poster_path;
            this.releaseDate = release_date;
            this.overview = overview;
            this.voteAverage = vote_average;
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
