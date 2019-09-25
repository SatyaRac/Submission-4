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
import com.example.submission4madegdk2019.activity.DetailTvFavoriteActivity;
import com.example.submission4madegdk2019.model.TvFav;

import java.util.ArrayList;
import java.util.List;

public class TvFavAdapter extends RecyclerView.Adapter<TvFavAdapter.TvViewHolder> {

    private ArrayList<TvFav> tvFavList = new ArrayList<>();
    private  Context context;
    private final Activity activity;

    public TvFavAdapter(Activity activity){
        this.activity = activity;
    }

    public ArrayList<TvFav> getAllTvFav(){
        return tvFavList;
    }

    public void setDataTv(List<TvFav> items){
        tvFavList.clear();
        tvFavList.addAll(items);
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public TvViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View Tview = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_favorite_tv, viewGroup, false);
        return new TvFavAdapter.TvViewHolder(Tview);
    }

    @Override
    public void onBindViewHolder(@NonNull TvViewHolder holder, int i) {
        holder.bind(tvFavList.get(i));
    }

    @Override
    public int getItemCount() {
        return tvFavList.size();
    }

    class TvViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvNameFavTv,tvFirstAirDateFavTv;
        Button btnDetailTvFav;
        ImageView imgPhoto;
        TvViewHolder(@NonNull View itemView) {
            super(itemView);

            tvNameFavTv = itemView.findViewById(R.id.tv_name_tv);
            tvFirstAirDateFavTv = itemView.findViewById(R.id.tv_first_air_date_tv);
            btnDetailTvFav = itemView.findViewById(R.id.btn_detail_tv_fav);
            imgPhoto = itemView.findViewById(R.id.iv_poster_tv);
            btnDetailTvFav.setOnClickListener(this);
        }
        void bind(TvFav tvFav){
            String url_image = tvFav.getTv_poster_path();
            tvNameFavTv.setText(tvFav.getTv_name());
            tvFirstAirDateFavTv.setText(tvFav.getTv_first_air_date());

            Glide.with(itemView.getContext())
                    .load(url_image)
                    .placeholder(R.color.primaryColor)
                    .override(50,75)
                    .into(imgPhoto);
        }

        @Override
        public void onClick(View v) {

            int position = getAdapterPosition();
            TvFav tvFav = tvFavList.get(position);
            Intent intent = new Intent(btnDetailTvFav.getContext(), DetailTvFavoriteActivity.class);
            intent.putExtra(DetailTvFavoriteActivity.SEND_POSITION, position);
            intent.putExtra(DetailTvFavoriteActivity.SEND_TV_FAVORITE, tvFavList.get(position));
            intent.putExtra(DetailTvFavoriteActivity.SEND_TV_FAVORITE, tvFav);
            ((Activity) btnDetailTvFav.getContext()).startActivityForResult(intent, DetailTvFavoriteActivity.REQUEST_UPDATE);

        }
    }
    public void setTvFavList(ArrayList<TvFav> tvFavList){
        if (tvFavList.size() > 0){
            this.tvFavList.clear();
        }
        this.tvFavList.addAll(tvFavList);

        notifyDataSetChanged();
    }
    public void addItem(TvFav tvFav){
        this.tvFavList.add(tvFav);
        notifyItemInserted(tvFavList.size() - 1);
    }

    public void removeItem(int i){
        this.tvFavList.remove(i);
        notifyItemRemoved(i);
        notifyItemRangeChanged(i, tvFavList.size());
    }
}
