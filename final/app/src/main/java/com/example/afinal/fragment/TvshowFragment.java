package com.example.afinal.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.example.afinal.R;
import com.example.afinal.adapter.AdapterTvShow;
import com.example.afinal.dataResponse.Movie;
import com.example.afinal.dataResponse.Tvshow;
import com.example.afinal.internet.ApiConfig;
import com.example.afinal.internet.NetworkUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TvshowFragment extends Fragment {
    AdapterTvShow adapterTvShow;
    RecyclerView rv;
    private LinearLayout ll_reload;
    private ImageView iv_loading;
    private ProgressBar progressBar;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tvshow, container, false);
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        rv = view.findViewById(R.id.rv_tv);
        ll_reload = view.findViewById(R.id.reload);
        iv_loading = view.findViewById(R.id.loading);
        progressBar = view.findViewById(R.id.progressBar);
        adapterTvShow = new AdapterTvShow();

        ll_reload.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);

        fetchAPI();
        iv_loading.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fetchAPI();
            }
        });
    }

    private void fetchAPI() {
        if (NetworkUtil.isNetworkAvailable(getActivity())){
            ApiConfig.getApiService().getTvshow(ApiConfig.getApiKey()).enqueue(new Callback<Tvshow>(){
                @Override
                public void onResponse(Call<Tvshow> call, Response<Tvshow> response) {
                    if (response.isSuccessful() && response.body() != null){
                        adapterTvShow.addUser(response.body().getTvshow());
                        rv.setAdapter(adapterTvShow);
                        int numberOfColumns = 2; // Jumlah kolom yang diinginkan
                        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), numberOfColumns);
                        rv.setLayoutManager(layoutManager);

                        ll_reload.setVisibility(View.GONE);
                        progressBar.setVisibility(View.GONE);
                    }
                }
                @Override
                public void onFailure(Call<Tvshow> call, Throwable t) {
                    Log.e("MainActivity", "onFailure: " + t.getMessage());
                }
            });
        }
        else {
            ll_reload.setVisibility(View.VISIBLE);
            rv.setVisibility(View.GONE);
            progressBar.setVisibility(View.GONE);
        }
    }
}