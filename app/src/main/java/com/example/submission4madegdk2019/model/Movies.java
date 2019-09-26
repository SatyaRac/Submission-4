package com.example.submission4madegdk2019.model;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

public class Movies implements Parcelable {

    private int id;
    private String title;
    private String poster_path;
    private String release_date;
    private String overview;
    private Double vote_average;



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

    public Double getVote_average() {
        return vote_average;
    }

    public void setVote_average(Double vote_average) {
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
    protected Movies(Parcel in) {
        this.id = in.readInt();
        this.title = in.readString();
        this.poster_path = in.readString();
        this.release_date = in.readString();
        this.overview = in.readString();
        this.vote_average = (Double) in.readValue(Double.class.getClassLoader());
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
            this.poster_path = poster_path;
            this.release_date = release_date;
            this.overview = overview;
            this.vote_average = vote_average;
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


}
