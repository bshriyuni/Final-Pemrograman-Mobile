package com.example.afinal.adapter;

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
import com.example.afinal.models.TvShowModel;

import java.util.ArrayList;
import java.util.List;

public class AdapterTvShow extends RecyclerView.Adapter<AdapterTvShow.ViewHolder> {
    private final ArrayList<TvShowModel> datatv = new ArrayList<>();
    public void addUser (List<TvShowModel> datatv) {
        this.datatv.addAll(datatv);
    }
    @NonNull
    @Override
    public AdapterTvShow.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tvshow_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterTvShow.ViewHolder holder, int position) {
        TvShowModel tvShowModel = datatv.get(position);
        holder.tv_judul.setText(tvShowModel.getName());

        String releaseDate = tvShowModel.getFirst_air_date();
        if (releaseDate != null && !releaseDate.isEmpty()) {
            String year = releaseDate.substring(0, 4);
            holder.tv_tahun.setText(year);
        } else {
            holder.tv_tahun.setText("");
        }

        String posterUrl = "https://image.tmdb.org/t/p/w500" + tvShowModel.getPoster_path();
        Glide.with(holder.iv_poster.getContext())
                .load(posterUrl)
                .placeholder(R.drawable.baseline_image_search_24) // placeholder image saat sedang memuat
                .error(R.drawable.baseline_image_24) // gambar yang ditampilkan jika terjadi kesalahan
                .into(holder.iv_poster);

        holder.itemView.setOnClickListener(view -> {
            Intent intent = new Intent(holder.itemView.getContext(), MainActivity2.class);
            intent.putExtra("tvshow", tvShowModel);
            holder.itemView.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return datatv.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private final TextView tv_judul;
        private final TextView tv_tahun;
        private final ImageView iv_poster;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_judul = itemView.findViewById(R.id.tv_judul);
            tv_tahun = itemView.findViewById(R.id.tv_tahun);
            iv_poster = itemView.findViewById(R.id.iv_poster);

        }
    }
}
