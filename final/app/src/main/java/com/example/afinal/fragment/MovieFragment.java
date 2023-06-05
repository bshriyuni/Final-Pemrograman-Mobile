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
import com.example.afinal.adapter.AdapterMovie;
import com.example.afinal.dataResponse.Movie;
import com.example.afinal.internet.ApiConfig;
import com.example.afinal.internet.NetworkUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieFragment extends Fragment {
    private AdapterMovie adapterMovie;
    RecyclerView rv;
    private LinearLayout ll_reload;
    private ImageView iv_loading;
    private ProgressBar progressBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movie, container, false);
    }
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        rv = view.findViewById(R.id.rv_movie);
        ll_reload = view.findViewById(R.id.reload);
        iv_loading = view.findViewById(R.id.loading);
        progressBar = view.findViewById(R.id.progressBar);
        adapterMovie = new AdapterMovie();

        ll_reload.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);

        fetchAPI();
        iv_loading.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                fetchAPI();
            }
        });
    }

    private void fetchAPI() {
        if (NetworkUtil.isNetworkAvailable(getActivity())){
            ApiConfig.getApiService().getMovie(ApiConfig.getApiKey()).enqueue(new Callback<Movie>(){
                @Override
                public void onResponse(Call<Movie> call, Response<Movie> response) {
                    if (response.isSuccessful() && response.body() != null){
                        adapterMovie.addUser(response.body().getMovie());
                        rv.setAdapter(adapterMovie);
                        int numberOfColumns = 2; // Jumlah kolom yang diinginkan
                        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), numberOfColumns);
                        rv.setLayoutManager(layoutManager);

                        ll_reload.setVisibility(View.GONE);
                        progressBar.setVisibility(View.GONE);
                    }
                }
                @Override
                public void onFailure(Call<Movie> call, Throwable t) {
                    Log.e("MainActivity", "onFailure: " + t.getMessage());
                }
            });
        }
        else {
            ll_reload.setVisibility(View.VISIBLE);
            rv.setVisibility(View.GONE);
        }
    }
}