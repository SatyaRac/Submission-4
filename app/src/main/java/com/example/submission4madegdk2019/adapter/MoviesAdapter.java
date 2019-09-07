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
import com.bumptech.glide.request.RequestOptions;
import com.example.submission4madegdk2019.R;
import com.example.submission4madegdk2019.model.Movies;

import java.util.ArrayList;
import java.util.List;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MovieViewAdapter> {

    private List<Movies> mData = new ArrayList<>();
    private Activity activity;
    private Context context;

    public MoviesAdapter(Activity activity){
        this.activity = activity;
    }

    public void setData(List<Movies> items){
        this.mData.clear();
        this.mData.addAll(items);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MovieViewAdapter onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View Mview = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_movie,viewGroup,false);
        return new MovieViewAdapter(Mview);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewAdapter holder, int i) {

        final Movies movies = mData.get(i);

        holder.tvTitle.setText(mData.get(i).getTitle());
        holder.tvReleaseDate.setText(mData.get(i).getReleaseDate());
        holder.tvOverview.setText(mData.get(i).getOverview());
        String urlPosterMov = "https://image.tmdb.org/t/p/w500" + mData.get(i).getPosterPath();
        Glide.with(holder.itemView.getContext())
                .load(urlPosterMov)
                .apply(new RequestOptions().override(50,75))
                .into(holder.imgPos);

        /*holder.btnDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, DetailMovieActivity.class);
                intent.putExtra(DetailMovieAcyivity.SEND_MOVIE, movies);
                activity.startActivity(intent);
            }
        });*/
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class MovieViewAdapter extends RecyclerView.ViewHolder {
        TextView tvTitle, tvReleaseDate, tvOverview;
        Button btnDetail;
        ImageView imgPos;
        MovieViewAdapter(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_title_movie);
            tvReleaseDate = itemView.findViewById(R.id.tv_release_movie);
            tvOverview = itemView.findViewById(R.id.tv_overview_movie);
            btnDetail = itemView.findViewById(R.id.btn_detailmov);
            imgPos = itemView.findViewById(R.id.iv_poster_movie);
        }
    }
}
