package com.example.submission4madegdk2019.fragment;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.submission4madegdk2019.R;
import com.example.submission4madegdk2019.activity.ListFavTvActivity;
import com.example.submission4madegdk2019.adapter.TvAdapter;
import com.example.submission4madegdk2019.model.TvShow;
import com.example.submission4madegdk2019.viewModel.TvViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class TvFragment extends Fragment {

    private RecyclerView rvTv;
    private ProgressBar progressBar;
    private TvViewModel tvViewModel;
    private TvAdapter tvAdapter;
    private ArrayList<TvShow> tv;
    private FloatingActionButton fabTv;


    public TvFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tv, container, false);

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rvTv = view.findViewById(R.id.menu_tv);
        progressBar = view.findViewById(R.id.progress_circular);
        fabTv = view.findViewById(R.id.btn_favoriteTv);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);

        tvViewModel = ViewModelProviders.of(this).get(TvViewModel.class);
        tvViewModel.getTv().observe(this, getTv);

        tvAdapter = new TvAdapter(getActivity());
        tvAdapter.notifyDataSetChanged();

        rvTv.setHasFixedSize(true);
        rvTv.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvTv.setAdapter(tvAdapter);

        fabTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ListFavTvActivity.class);
                startActivity(intent);
            }
        });

        tvViewModel.setTv("SEND_TV");
        showLoading(false);

    }

    private Observer<ArrayList<TvShow>> getTv = new Observer<ArrayList<TvShow>>() {
        @Override
        public void onChanged(ArrayList<TvShow> tv) {
            if (tv != null){
                tvAdapter.setDataTv(tv);
                showLoading(true);
            }
        }
    };

    private void showLoading(Boolean b){
        if (b){
            progressBar.setVisibility(View.GONE);
        }else {
            progressBar.setVisibility(View.VISIBLE);
        }
    }

}
