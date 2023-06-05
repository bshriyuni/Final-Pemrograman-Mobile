package com.example.afinal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.afinal.fragment.FavoriteFragment;
import com.example.afinal.fragment.MovieFragment;
import com.example.afinal.fragment.TvshowFragment;
import com.example.afinal.internet.NetworkUtil;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity implements NavigationBarView.OnItemSelectedListener{
    private TextView tv_toolbar;
    private BottomNavigationView bottomNavigationView;
    private MovieFragment movieFragment = new MovieFragment();
    private TvshowFragment tvshowFragment = new TvshowFragment();
    private FavoriteFragment favoriteFragment = new FavoriteFragment();



    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv_toolbar = findViewById(R.id.tv_toolbar);
        bottomNavigationView = findViewById(R.id.bottomView);
        bottomNavigationView.setOnItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.movie);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.movie:
                getSupportFragmentManager().beginTransaction().replace(R.id.framelayout, movieFragment).commit();
                tv_toolbar.setText("Movie");
                return true;
            case R.id.tvshow:
                getSupportFragmentManager().beginTransaction().replace(R.id.framelayout, tvshowFragment).commit();
                tv_toolbar.setText("TvShow");
                return true;
            case R.id.favorite:
                getSupportFragmentManager().beginTransaction().replace(R.id.framelayout, favoriteFragment).commit();
                tv_toolbar.setText("Favorite");
                return true;
        }
        return false;
    }
}