package com.example.afinal.internet;

import com.example.afinal.dataResponse.Movie;
import com.example.afinal.dataResponse.Tvshow;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {
    @GET("tv/airing_today?")
    Call<Tvshow> getTvshow(@Query("api_key") String apiKey);

    @GET("movie/now_playing?")
    Call<Movie> getMovie(@Query("api_key") String apiKey);
}
