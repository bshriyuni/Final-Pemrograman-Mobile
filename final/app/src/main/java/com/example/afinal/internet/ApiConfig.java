package com.example.afinal.internet;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiConfig {
    private static final String BASE_URL = "https://api.themoviedb.org/3/";
    private static final String API_KEY = "3b8c681cb1b65d46b2b5d5cfe97ec79c";

    public static ApiService getApiService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit.create(ApiService.class);
    }

    public static String getApiKey() {
        return API_KEY;
    }

}
