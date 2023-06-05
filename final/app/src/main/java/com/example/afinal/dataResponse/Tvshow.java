package com.example.afinal.dataResponse;

import com.example.afinal.models.MovieModel;
import com.example.afinal.models.TvShowModel;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Tvshow {
    @SerializedName("results")
    private List<TvShowModel> tvshow;

    public List<TvShowModel> getTvshow() {
        return tvshow;
    }
}
