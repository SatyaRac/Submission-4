package com.example.submission4madegdk2019.adapter;

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
import com.example.submission4madegdk2019.activity.DetailMovieActivity;
import com.example.submission4madegdk2019.activity.DetailTvActivity;
import com.example.submission4madegdk2019.model.Movies;
import com.example.submission4madegdk2019.model.TvShow;

import static com.example.submission4madegdk2019.activity.DetailMovieActivity.SEND_MOVIE;
import static com.example.submission4madegdk2019.activity.DetailTvActivity.SEND_TV;


import java.util.ArrayList;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder> {
    ArrayList<Movies> listMovie = new ArrayList<>();
    ArrayList<TvShow> listTvShow = new ArrayList<>();

    public void setListMovie(ArrayList<Movies> items) {
        listMovie.clear();
        listMovie.addAll(items);
        notifyDataSetChanged();
    }

    public void setListTvShow(ArrayList<TvShow> items){
        listTvShow.clear();
        listTvShow.addAll(items);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_hasil_search, viewGroup, false);
        return new SearchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final SearchViewHolder holder, final int i) {
        if (listMovie.size() > 0){
            holder.txtTitle.setText(listMovie.get(i).getTitle());
            holder.txtRilis.setText(listMovie.get(i).getRelease_date());
            holder.txtOverview.setText(listMovie.get(i).getOverview());
            Glide.with(holder.itemView.getContext())
                    .load(listMovie.get(i).getPoster_path())
                    .into(holder.imgPoster);
            holder.btnDetail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(holder.itemView.getContext(), DetailMovieActivity.class);
                    intent.putExtra(SEND_MOVIE, listMovie);
                    holder.itemView.getContext().startActivity(intent);
                }
            });
        } else if (listTvShow.size() > 0){
            holder.txtTitle.setText(listTvShow.get(i).getName());
            holder.txtRilis.setText(listTvShow.get(i).getFirstAirDate());
            holder.txtOverview.setText(listTvShow.get(i).getOverview());
            holder.btnDetail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(holder.itemView.getContext(), DetailTvActivity.class);
                    intent.putExtra(SEND_TV, listTvShow);
                    holder.itemView.getContext().startActivity(intent);
                }
            });

        }
    }

    @Override
    public int getItemCount() {
        int size = 0;
        if (listMovie.size() > 0) {
            size = listMovie.size();
        } else if (listTvShow.size() > 0){
            size = listTvShow.size();
        }
        return size;
    }

    public class SearchViewHolder extends RecyclerView.ViewHolder {
        TextView txtTitle,txtOverview,txtRilis;
        ImageView imgPoster;
        Button btnDetail;


        public SearchViewHolder(@NonNull View itemView) {
            super(itemView);

            txtTitle = itemView.findViewById(R.id.tv_title);
            txtRilis = itemView.findViewById(R.id.tv_release);
            txtOverview = itemView.findViewById(R.id.tv_overview);

        }
    }
}
