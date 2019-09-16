package com.example.submission4madegdk2019;

import android.view.View;

public class CustomOnItemClickListiner implements View.OnClickListener {
    private final int position;
    private final OnItemClickCallback onItemClickCallback;
    public CustomOnItemClickListiner(int position, OnItemClickCallback onItemClickCallback) {
        this.position = position;
        this.onItemClickCallback = onItemClickCallback;
    }


    @Override
    public void onClick(View v) {
        onItemClickCallback.onItemClicked(v, position);

    }

    public interface OnItemClickCallback{
        void onItemClicked(View view, int position);
    }
}
