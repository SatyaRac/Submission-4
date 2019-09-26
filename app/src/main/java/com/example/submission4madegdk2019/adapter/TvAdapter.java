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
import com.example.submission4madegdk2019.activity.DetailTvActivity;
import com.example.submission4madegdk2019.model.TvShow;

import java.util.ArrayList;
import java.util.List;

public class TvAdapter extends RecyclerView.Adapter<TvAdapter.TvViewAdapter> {

    private ArrayList<TvShow> tData = new ArrayList<>();
    private Activity activity;
    private Context context;

    public TvAdapter(Activity activity){
        this.activity = activity;
    }

    public void setDataTv(List<TvShow> items){
        this.tData.clear();
        this.tData.addAll(items);
        notifyDataSetChanged();
    }



    @NonNull
    @Override
    public TvViewAdapter onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View Tview = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_tv,viewGroup, false);
        return new TvViewAdapter(Tview);
    }

    @Override
    public void onBindViewHolder(@NonNull TvViewAdapter holder, int i) {
        final TvShow tvShow = tData.get(i);

        holder.tvName.setText(tData.get(i).getName());
        holder.tvFirstAirDate.setText(tData.get(i).getFirstAirDate());
        holder.tvOverview.setText(tData.get(i).getOverview());
        String urlPosterTv = "https://image.tmdb.org/t/p/w500/" + tData.get(i).getPosterPath();
        Glide.with(holder.itemView.getContext())
                .load(urlPosterTv)
                .apply(new RequestOptions().override(50,75))
                .into(holder.imgPos);

        holder.btnDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, DetailTvActivity.class);
                intent.putExtra(DetailTvActivity.SEND_TV, tvShow);
                activity.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return tData.size();
    }

     class TvViewAdapter extends RecyclerView.ViewHolder {
        TextView tvName, tvFirstAirDate, tvOverview;
        Button btnDetail;
        ImageView imgPos;

        public TvViewAdapter(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_name_tv);
            tvFirstAirDate = itemView.findViewById(R.id.tv_first_air_date_tv);
            tvOverview = itemView.findViewById(R.id.tv_overview_tv);
            btnDetail = itemView.findViewById(R.id.btn_detailtv);
            imgPos = itemView.findViewById(R.id.iv_poster_tv);
        }
    }
}
