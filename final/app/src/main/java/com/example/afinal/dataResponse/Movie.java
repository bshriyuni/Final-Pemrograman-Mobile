package com.example.afinal.dataResponse;

import com.example.afinal.models.MovieModel;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Movie {
    @SerializedName("results")
    private List<MovieModel> movie;
    public List<MovieModel> getMovie() {
        return movie;
    }
}
