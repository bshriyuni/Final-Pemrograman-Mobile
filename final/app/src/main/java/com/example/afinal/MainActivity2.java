package com.example.afinal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class MainActivity2 extends AppCompatActivity {
    private ImageView iv_backdrop, iv_poster, iv_jenis;
    private TextView tv_judul, tv_tayang, tv_overview, tv_vote;
    private CardView cv_back, cv_fav;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        iv_backdrop = findViewById(R.id.iv_backdropdetail);
        iv_poster = findViewById(R.id.iv_posterdetail);
        tv_judul = findViewById(R.id.tv_juduldetail);
        tv_tayang = findViewById(R.id.tv_tayangdetail);
        tv_overview = findViewById(R.id.tv_overviewdetail);
        tv_vote = findViewById(R.id.tv_votedetail);
        cv_back = findViewById(R.id.cv_backdetail);
        cv_fav = findViewById(R.id.cv_favdetail);
        iv_jenis = findViewById(R.id.iv_jenis);

        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        String tanggal = intent.getStringExtra("tanggal");
        String overview = intent.getStringExtra("overview");
        String vote = intent.getStringExtra("vote");
        String poster = intent.getStringExtra("poster");
        String backdrop = intent.getStringExtra("backdrop");
        int jenis = intent.getIntExtra("jenis", 0);

        tv_judul.setText(title);
        tv_tayang.setText(tanggal);
        tv_overview.setText(overview);
        tv_vote.setText(vote);
        iv_jenis.setImageResource(jenis);
        String posterUrl = "https://image.tmdb.org/t/p/w500" + poster;
        Glide.with(iv_poster.getContext())
                .load(posterUrl)
                .placeholder(R.drawable.baseline_image_search_24) // placeholder image saat sedang memuat
                .error(R.drawable.baseline_image_24) // gambar yang ditampilkan jika terjadi kesalahan
                .into(iv_poster);
        String backdropUrl = "https://image.tmdb.org/t/p/w500" + backdrop;
        Glide.with(iv_backdrop.getContext())
                .load(backdropUrl)
                .placeholder(R.drawable.baseline_image_search_24) // placeholder image saat sedang memuat
                .error(R.drawable.baseline_image_24) // gambar yang ditampilkan jika terjadi kesalahan
                .into(iv_backdrop);

        cv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent back = new Intent(MainActivity2.this, MainActivity.class);
                startActivity(back);
                finish();
            }
        });

        cv_fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}