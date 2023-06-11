package com.example.afinal;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.bumptech.glide.Glide;
import com.example.afinal.database.DatabaseContract;
import com.example.afinal.helper.FavoriteHelper;
import com.example.afinal.models.FavoriteModel;
import com.example.afinal.models.MovieModel;
import com.example.afinal.models.TvShowModel;

public class MainActivity2 extends AppCompatActivity {
    FavoriteHelper favoriteHelper;
    public static final String EXTRA_FAVORITE = "extra_favorite";
    public static final int RESULT_DELETE = 301;
    private boolean isFavorite = false;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        ImageView iv_backdrop = findViewById(R.id.iv_backdropdetail);
        ImageView iv_poster = findViewById(R.id.iv_posterdetail);
        TextView tv_judul = findViewById(R.id.tv_juduldetail);
        TextView tv_tayang = findViewById(R.id.tv_tayangdetail);
        TextView tv_overview = findViewById(R.id.tv_overviewdetail);
        TextView tv_vote = findViewById(R.id.tv_votedetail);
        CardView cv_back = findViewById(R.id.cv_backdetail);
        CardView cv_fav = findViewById(R.id.cv_favdetail);
        ImageView iv_jenis = findViewById(R.id.iv_jenis);
        ImageView iv_fav = findViewById(R.id.iv_fav);

        // Menginisialisasi FavoriteHelper dan membuka koneksi database
        favoriteHelper = FavoriteHelper.getInstance(getApplicationContext());
        favoriteHelper.open();

        // Mendapatkan status favorit dari SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("FavoriteStatus", Context.MODE_PRIVATE);
        isFavorite = sharedPreferences.getBoolean("isFavorite", false);

        // Mengatur gambar iv_fav sesuai dengan status favorit
        if (isFavorite) {
            iv_fav.setImageResource(R.drawable.baseline_favorite_24h);
        } else {
            iv_fav.setImageResource(R.drawable.baseline_favorite_border_24);
        }

        Intent intent = getIntent();
        if (intent.getParcelableExtra("movie") != null) {
            MovieModel movieModel = intent.getParcelableExtra("movie");
            tv_judul.setText(movieModel.getTitle());
            tv_tayang.setText(movieModel.getRelease_date());
            tv_vote.setText(movieModel.getVote_average());
            tv_overview.setText( movieModel.getOverview());
            iv_jenis.setImageResource(R.drawable.baseline_movie_24h);
            String posterUrl = "https://image.tmdb.org/t/p/w500" + movieModel.getPoster_path();
            Glide.with(iv_poster.getContext())
                    .load(posterUrl)
                    .placeholder(R.drawable.baseline_image_search_24) // placeholder image saat sedang memuat
                    .error(R.drawable.baseline_image_24) // gambar yang ditampilkan jika terjadi kesalahan
                    .into(iv_poster);
            String backdropUrl = "https://image.tmdb.org/t/p/w500" + movieModel.getBackdrop_path();
            Glide.with(iv_backdrop.getContext())
                    .load(backdropUrl)
                    .placeholder(R.drawable.baseline_image_search_24) // placeholder image saat sedang memuat
                    .error(R.drawable.baseline_image_24) // gambar yang ditampilkan jika terjadi kesalahan
                    .into(iv_backdrop);

            cv_fav.setOnClickListener(view -> {
                boolean dataExists = favoriteHelper.checkDataExists(movieModel.getTitle());
                if (dataExists) {
                    iv_fav.setImageResource(R.drawable.baseline_favorite_border_24);
                    long deleteResult = favoriteHelper.deleteBytitle(movieModel.getTitle());
                    if (deleteResult > 0) {
                        Intent delete = new Intent();
                        Toast.makeText(this, "Delete from Favorite", Toast.LENGTH_SHORT).show();
                        setResult(RESULT_DELETE, delete);
                    } else {
                        Toast.makeText(this, "Failed delete data", Toast.LENGTH_SHORT).show();
                    }
                    isFavorite = false;
                } else {
                    iv_fav.setImageResource(R.drawable.baseline_favorite_24h);
                    int jenismovie = R.drawable.baseline_movie_24h;
                    Intent kefavorite = new Intent();
                    kefavorite.putExtra(EXTRA_FAVORITE, movieModel);
                    ContentValues values = new ContentValues();
                    values.put(DatabaseContract.FavoriteColumns.TITLE, movieModel.getTitle());
                    values.put(DatabaseContract.FavoriteColumns.POSTER, movieModel.getPoster_path());
                    values.put(DatabaseContract.FavoriteColumns.TAHUN, movieModel.getRelease_date());
                    values.put(DatabaseContract.FavoriteColumns.JENIS, jenismovie);
                    values.put(DatabaseContract.FavoriteColumns.BACKDROP, movieModel.getBackdrop_path());
                    values.put(DatabaseContract.FavoriteColumns.VOTE, movieModel.getVote_average());
                    values.put(DatabaseContract.FavoriteColumns.SINOPSIS, movieModel.getOverview());
                    long result = FavoriteHelper.insert(values);
                    if (result > 0) {
                        movieModel.setId(String.valueOf(result));
                        Toast.makeText(this, "Add to Favorite", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "Failed to add data", Toast.LENGTH_SHORT).show();
                    }
                    isFavorite = true;
                }

            });

        } else if (intent.getParcelableExtra("tvshow") != null) {
            TvShowModel tvShowModel = intent.getParcelableExtra("tvshow");
            tv_judul.setText(tvShowModel.getName());
            tv_tayang.setText(tvShowModel.getFirst_air_date());
            tv_vote.setText(tvShowModel.getVote_average());
            tv_overview.setText(tvShowModel.getOverview());
            iv_jenis.setImageResource(R.drawable.baseline_live_tv_24);
            String posterUrl = "https://image.tmdb.org/t/p/w500" + tvShowModel.getPoster_path();
            Glide.with(iv_poster.getContext())
                    .load(posterUrl)
                    .placeholder(R.drawable.baseline_image_search_24) // placeholder image saat sedang memuat
                    .error(R.drawable.baseline_image_24) // gambar yang ditampilkan jika terjadi kesalahan
                    .into(iv_poster);
            String backdropUrl = "https://image.tmdb.org/t/p/w500" + tvShowModel.getBackdrop_path();
            Glide.with(iv_backdrop.getContext())
                    .load(backdropUrl)
                    .placeholder(R.drawable.baseline_image_search_24) // placeholder image saat sedang memuat
                    .error(R.drawable.baseline_image_24) // gambar yang ditampilkan jika terjadi kesalahan
                    .into(iv_backdrop);

            cv_fav.setOnClickListener(view -> {
                boolean dataExists = favoriteHelper.checkDataExists(tvShowModel.getName());
                if (dataExists) {
                    iv_fav.setImageResource(R.drawable.baseline_favorite_border_24);
                    long deleteResult = favoriteHelper.deleteBytitle(tvShowModel.getName());
                    if (deleteResult > 0) {
                        Intent delete = new Intent();
                        Toast.makeText(this, "Delete from Favorite", Toast.LENGTH_SHORT).show();
                        setResult(RESULT_DELETE, delete);
                    } else {
                        Toast.makeText(this, "Failed delete data", Toast.LENGTH_SHORT).show();
                    }
                    isFavorite = false;
                } else {
                    iv_fav.setImageResource(R.drawable.baseline_favorite_24h);
                    int jenistvshow = R.drawable.baseline_live_tv_24;
                    Intent kefavorite = new Intent();
                    kefavorite.putExtra(EXTRA_FAVORITE, tvShowModel);
                    ContentValues values = new ContentValues();
                    values.put(DatabaseContract.FavoriteColumns.TITLE, tvShowModel.getName());
                    values.put(DatabaseContract.FavoriteColumns.POSTER, tvShowModel.getPoster_path());
                    values.put(DatabaseContract.FavoriteColumns.TAHUN, tvShowModel.getFirst_air_date());
                    values.put(DatabaseContract.FavoriteColumns.JENIS, jenistvshow);
                    values.put(DatabaseContract.FavoriteColumns.BACKDROP, tvShowModel.getBackdrop_path());
                    values.put(DatabaseContract.FavoriteColumns.VOTE, tvShowModel.getVote_average());
                    values.put(DatabaseContract.FavoriteColumns.SINOPSIS, tvShowModel.getOverview());
                    long result = FavoriteHelper.insert(values);
                    if (result > 0) {
                        tvShowModel.setId(String.valueOf(result));
                        Toast.makeText(this, "Add to Favorite", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "Failed to add data", Toast.LENGTH_SHORT).show();
                    }
                    isFavorite = true;
                }
            });
        } else {
            FavoriteModel favoriteModel = intent.getParcelableExtra("favorite");
            tv_judul.setText(favoriteModel.getJudul());
            tv_tayang.setText(favoriteModel.getTahun());
            tv_vote.setText(favoriteModel.getVote());
            tv_overview.setText(favoriteModel.getSinopsis());
            Glide.with(this).load(favoriteModel.getJenis()).into(iv_jenis);
            String posterUrl = "https://image.tmdb.org/t/p/w500" + favoriteModel.getPoster();
            Glide.with(iv_poster.getContext())
                    .load(posterUrl)
                    .placeholder(R.drawable.baseline_image_search_24) // placeholder image saat sedang memuat
                    .error(R.drawable.baseline_image_24) // gambar yang ditampilkan jika terjadi kesalahan
                    .into(iv_poster);
            String backdropUrl = "https://image.tmdb.org/t/p/w500" + favoriteModel.getBackdrop();
            Glide.with(iv_backdrop.getContext())
                    .load(backdropUrl)
                    .placeholder(R.drawable.baseline_image_search_24) // placeholder image saat sedang memuat
                    .error(R.drawable.baseline_image_24) // gambar yang ditampilkan jika terjadi kesalahan
                    .into(iv_backdrop);


            cv_fav.setOnClickListener(view ->{
                boolean dataExists = favoriteHelper.checkDataExists(favoriteModel.getJudul());
                if (dataExists) {
                    iv_fav.setImageResource(R.drawable.baseline_favorite_border_24);
                    long deleteResult = favoriteHelper.deleteBytitle(favoriteModel.getJudul());
                    if (deleteResult > 0) {
                        Intent delete = new Intent();
                        Toast.makeText(this, "Delete from Favorite", Toast.LENGTH_SHORT).show();
                        setResult(RESULT_DELETE, delete);
                    } else {
                        Toast.makeText(this, "Failed delete data", Toast.LENGTH_SHORT).show();
                    }
                    isFavorite = false;
                } else {
                    iv_fav.setImageResource(R.drawable.baseline_favorite_24h);
                    Intent kefavorite = new Intent();
                    kefavorite.putExtra(EXTRA_FAVORITE, favoriteModel);
                    ContentValues values = new ContentValues();
                    values.put(DatabaseContract.FavoriteColumns.TITLE, favoriteModel.getJudul());
                    values.put(DatabaseContract.FavoriteColumns.POSTER, favoriteModel.getPoster());
                    values.put(DatabaseContract.FavoriteColumns.TAHUN, favoriteModel.getTahun());
                    values.put(DatabaseContract.FavoriteColumns.JENIS, favoriteModel.getJenis());
                    values.put(DatabaseContract.FavoriteColumns.VOTE, favoriteModel.getVote());
                    values.put(DatabaseContract.FavoriteColumns.BACKDROP, favoriteModel.getBackdrop());
                    values.put(DatabaseContract.FavoriteColumns.SINOPSIS, favoriteModel.getSinopsis());
                    long result = FavoriteHelper.insert(values);
                    if (result > 0) {
                        favoriteModel.setId(Integer.parseInt(String.valueOf(result)));
                        Toast.makeText(this, "Add to Favorite", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "Failed to add data", Toast.LENGTH_SHORT).show();
                    }
                    isFavorite = true;
                }
            });
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("isFavorite", isFavorite);
            editor.apply();
        }
        cv_back.setOnClickListener(view -> onBackPressed());
    }
}