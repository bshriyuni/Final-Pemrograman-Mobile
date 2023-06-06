package com.example.afinal;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.bumptech.glide.Glide;
import com.example.afinal.models.MovieModel;
import com.example.afinal.models.TvShowModel;

public class MainActivity2 extends AppCompatActivity {

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

        Intent intent = getIntent();
        if (intent.getParcelableExtra("movie") != null){
            System.out.println("tidak kosong");
            MovieModel movieModel = intent.getParcelableExtra("movie");
            tv_judul.setText(movieModel.getTitle());
            tv_tayang.setText(movieModel.getRelease_date());
            tv_vote.setText(movieModel.getVote_average());
            tv_overview.setText(movieModel.getOverview());
            iv_jenis.setImageResource(R.drawable.baseline_movie_24p);
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
        }
        else {
            System.out.println("kodsong");
            TvShowModel tvShowModel = intent.getParcelableExtra("tvshow");
            tv_judul.setText(tvShowModel.getName());
            tv_tayang.setText(tvShowModel.getFirst_air_date());
            tv_vote.setText(tvShowModel.getVote_average());
            tv_overview.setText(tvShowModel.getOverview());
            iv_jenis.setImageResource(R.drawable.baseline_live_tv_24p);
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
        }

        cv_back.setOnClickListener(view -> {
            Intent back = new Intent(MainActivity2.this, MainActivity.class);
            startActivity(back);
            finish();
        });

        cv_fav.setOnClickListener(view -> {

        });

    }
}