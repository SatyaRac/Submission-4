package com.example.submission4madegdk2019.model;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

public class TvShow implements Parcelable {

    private int id;
    private String name;
    private String posterPath;
    private String firstAirDate;
    private String overview;
    private Double voteAverage;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getFirstAirDate() {
        return firstAirDate;
    }

    public void setFirstAirDate(String firstAirDate) {
        this.firstAirDate = firstAirDate;
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
        dest.writeString(this.name);
        dest.writeString(this.posterPath);
        dest.writeString(this.firstAirDate);
        dest.writeString(this.overview);
        dest.writeValue(this.voteAverage);


    }
    protected TvShow(Parcel in) {
        this.id = in.readInt();
        this.name = in.readString();
        this.posterPath = in.readString();
        this.firstAirDate = in.readString();
        this.overview = in.readString();
        this.voteAverage = (Double) in.readValue(Double.class.getClassLoader());
    }

    public static final Creator<TvShow> CREATOR = new Creator<TvShow>() {
        @Override
        public TvShow createFromParcel(Parcel in) {
            return new TvShow(in);
        }

        @Override
        public TvShow[] newArray(int size) {
            return new TvShow[size];
        }
    };

    public TvShow(JSONObject object){
        try {
            int id = object.getInt("id");
            String name = object.getString("name");
            String poster_path = object.getString("poster_path");
            String first_air_date = object.getString("first_air_date");
            String overview = object.getString("tv_overview");
            Double vote_average = object.getDouble("tv_vote_average");

            this.name = name;
            this.posterPath = poster_path;
            this.firstAirDate = first_air_date;
            this.overview = overview;
            this.voteAverage = vote_average;
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
