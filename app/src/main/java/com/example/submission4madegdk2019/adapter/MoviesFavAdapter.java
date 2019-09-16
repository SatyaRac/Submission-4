package com.example.submission4madegdk2019.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.submission4madegdk2019.R;
import com.example.submission4madegdk2019.activity.DetailMovieFavoriteActivity;
import com.example.submission4madegdk2019.model.MovieFav;

import java.util.ArrayList;
import java.util.List;

public class MoviesFavAdapter extends RecyclerView.Adapter<MoviesFavAdapter.MovieViewHolder> {

    private ArrayList<MovieFav> movieFavList = new ArrayList<>();
    private final Activity activity;
    private Context context;

    public MoviesFavAdapter(Activity activity){
        this.activity = activity;
    }

    public void addItem(MovieFav movieFav){
        this.movieFavList.add(movieFav);
        notifyItemInserted(movieFavList.size() - 1);
    }

    public void removeItem(int i){
        this.movieFavList.remove(i);
        notifyItemRemoved(i);
        notifyItemRangeChanged(i, movieFavList.size());
    }

    public ArrayList<MovieFav> getAllMoviesFav(){
        return movieFavList;
    }

    public void setData(List<MovieFav> items){
        movieFavList.clear();
        movieFavList.addAll(items);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        View Mview = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_favorite_movie, viewGroup, false);
        return new MoviesFavAdapter.MovieViewHolder(Mview);
    }

    @Override
    public void onBindViewHolder(@NonNull final MovieViewHolder holder, final int i) {
        final MovieFav movieFav = movieFavList.get(i);

        holder.tvTitleFavMov.setText(movieFavList.get(i).getTitle());
        holder.tvReleaseFavMov.setText(movieFavList.get(i).getReleaseDate());
        String url_image = movieFavList.get(i).getPosterPath();
        Glide.with(holder.itemView.getContext())
                .load(url_image)
                .placeholder(R.color.primaryColor)
                .override(50,75)
                .into(holder.imgPhoto);
        holder.btnDetailMovFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent moveMovieFav = new Intent(activity, DetailMovieFavoriteActivity.class);
                moveMovieFav.putExtra(DetailMovieFavoriteActivity.SEND_MOVIE, i);
                moveMovieFav.putExtra(DetailMovieFavoriteActivity.SEND_MOVIE_FAVORITE, movieFavList.get(i));
                moveMovieFav.putExtra(DetailMovieFavoriteActivity.SEND_MOVIE_FAVORITE, movieFav);
                ((Activity) activity.getApplicationContext()).startActivityForResult(moveMovieFav, DetailMovieFavoriteActivity.REQUEST_UPDATE);
            }
        });

    }

    @Override
    public int getItemCount() {
        return movieFavList.size();
    }

    class MovieViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitleFavMov,tvReleaseFavMov;
        Button btnDetailMovFav;
        ImageView imgPhoto;
        MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitleFavMov = itemView.findViewById(R.id.tv_title_mov_fav);
            tvReleaseFavMov = itemView.findViewById(R.id.tv_release_mov_fav);
            btnDetailMovFav = itemView.findViewById(R.id.btn_detail_mov_fav);
        }
    }

    public void setMovieFavList(ArrayList<MovieFav> movieFavList){
        if (movieFavList.size() > 0){
            this.movieFavList.clear();
        }
        this.movieFavList.addAll(movieFavList);

        notifyDataSetChanged();
    }
}
