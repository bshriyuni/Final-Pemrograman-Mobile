package com.example.afinal.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.afinal.R;
import com.example.afinal.adapter.AdapterTvShow;
import com.example.afinal.dataResponse.Tvshow;
import com.example.afinal.internet.ApiConfig;
import com.example.afinal.internet.NetworkUtil;
import com.example.afinal.models.TvShowModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TvshowFragment extends Fragment {
    AdapterTvShow adapterTvShow;
    RecyclerView rv;
    private LinearLayout ll_reload;
    private ProgressBar progressBar;
    private List<TvShowModel> datamodel;
    private List<TvShowModel> filteredData;
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
        ImageView iv_loading = view.findViewById(R.id.loading);
        SearchView searchView = view.findViewById(R.id.searcview);
        progressBar = view.findViewById(R.id.progressBar);
        adapterTvShow = new AdapterTvShow();

        ll_reload.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);

        fetchAPI();
        iv_loading.setOnClickListener(view1 -> fetchAPI());

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                searchData(newText);
                return true;
            }
        });
    }

    @SuppressLint("NotifyDataSetChanged")
    private void searchData(String query) {
        filteredData.clear();
        if (TextUtils.isEmpty(query)) {
            filteredData.addAll(datamodel);
        } else {
            String lowerCaseQuery = query.toLowerCase().trim();
            for (TvShowModel tvshow : datamodel) {
                String title = tvshow.getName().toLowerCase();
                if (title.startsWith(lowerCaseQuery)) {
                    filteredData.add(tvshow);
                }
            }
        }
        adapterTvShow.setFilteredList(filteredData);
        adapterTvShow.notifyDataSetChanged();
    }

    private void fetchAPI() {
        if (NetworkUtil.isNetworkAvailable(requireActivity())){
            ApiConfig.getApiService().getTvshow(ApiConfig.getApiKey()).enqueue(new Callback<Tvshow>(){
                @Override
                public void onResponse(@NonNull Call<Tvshow> call, @NonNull Response<Tvshow> response) {
                    if (response.isSuccessful() && response.body() != null){
                        datamodel = response.body().getTvshow();
                        filteredData = new ArrayList<>(datamodel);
                        adapterTvShow.setFilteredList(datamodel);
                        rv.setAdapter(adapterTvShow);
                        int numberOfColumns = 2; // Jumlah kolom yang diinginkan
                        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), numberOfColumns);
                        rv.setLayoutManager(layoutManager);

                        ll_reload.setVisibility(View.GONE);
                        progressBar.setVisibility(View.GONE);
                    }
                }
                @Override
                public void onFailure(@NonNull Call<Tvshow> call, @NonNull Throwable t) {
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