package com.example.afinal.adapter;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.afinal.MainActivity2;
import com.example.afinal.R;
import com.example.afinal.models.FavoriteModel;

import java.util.ArrayList;
import java.util.List;

public class AdapterFavorite extends RecyclerView.Adapter<AdapterFavorite.ViewHolder> {
    private final List<FavoriteModel> datafavorite = new ArrayList<>();
    @SuppressLint("NotifyDataSetChanged")
    public void setData(ArrayList<FavoriteModel> favorite) {
        datafavorite.clear();
        if (favorite != null) {
            datafavorite.addAll(favorite);
        }
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public AdapterFavorite.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.favorite_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterFavorite.ViewHolder holder, int position) {
        FavoriteModel favoriteModel = datafavorite.get(position);
        holder.tv_judul.setText(favoriteModel.getJudul());
        holder.iv_label.setImageResource(favoriteModel.getJenis());
        String releaseDate = favoriteModel.getTahun();
        if (releaseDate != null && !releaseDate.isEmpty()) {
            String year = releaseDate.substring(0, 4);
            holder.tv_tahun.setText(year);
        } else {
            holder.tv_tahun.setText("");
        }
        String posterUrl = "https://image.tmdb.org/t/p/w500" + favoriteModel.getPoster();
        Glide.with(holder.iv_poster.getContext())
                .load(posterUrl)
                .placeholder(R.drawable.baseline_image_search_24) // placeholder image saat sedang memuat
                .error(R.drawable.baseline_image_24) // gambar yang ditampilkan jika terjadi kesalahan
                .into(holder.iv_poster);

        holder.itemView.setOnClickListener(view -> {
            Intent intent = new Intent(holder.itemView.getContext(), MainActivity2.class);
            intent.putExtra("favorite", favoriteModel);
            holder.itemView.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return datafavorite.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private final ImageView iv_poster;
        private final ImageView iv_label;
        private final TextView tv_judul;
        private final TextView tv_tahun;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_poster = itemView.findViewById(R.id.iv_poster);
            iv_label = itemView.findViewById(R.id.iv_label);
            tv_judul = itemView.findViewById(R.id.tv_judul);
            tv_tahun = itemView.findViewById(R.id.tv_tahun);
        }
    }
}
