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
import com.example.afinal.MainActivity;
import com.example.afinal.MainActivity2;
import com.example.afinal.R;
import com.example.afinal.models.MovieModel;
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
        TvShowModel tvShow = datatv.get(position);
        holder.tv_judul.setText(tvShow.getName());

        String releaseDate = tvShow.getFirst_air_date();
        if (releaseDate != null && !releaseDate.isEmpty()) {
            String year = releaseDate.substring(0, 4);
            holder.tv_tahun.setText(year);
        } else {
            holder.tv_tahun.setText("");
        }

        String posterUrl = "https://image.tmdb.org/t/p/w500" + tvShow.getPoster_path();
        Glide.with(holder.iv_poster.getContext())
                .load(posterUrl)
                .placeholder(R.drawable.baseline_image_search_24) // placeholder image saat sedang memuat
                .error(R.drawable.baseline_image_24) // gambar yang ditampilkan jika terjadi kesalahan
                .into(holder.iv_poster);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = datatv.get(holder.getAdapterPosition()).getName();
                String tanggal = datatv.get(holder.getAdapterPosition()).getFirst_air_date();
                String overview = datatv.get(holder.getAdapterPosition()).getOverview();
                String vote = datatv.get(holder.getAdapterPosition()).getVote_average();
                String poster = datatv.get(holder.getAdapterPosition()).getPoster_path();
                String backdrop = datatv.get(holder.getAdapterPosition()).getBackdrop_path();
                int jenis = R.drawable.baseline_live_tv_24p;

                Intent intent = new Intent(holder.itemView.getContext(), MainActivity2.class);
                intent.putExtra("title", title);
                intent.putExtra("tanggal", tanggal);
                intent.putExtra("overview", overview);
                intent.putExtra("vote", vote);
                intent.putExtra("poster", poster);
                intent.putExtra("backdrop", backdrop);
                intent.putExtra("jenis", jenis);
                holder.itemView.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return datatv.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView tv_judul, tv_tahun;
        private ImageView iv_poster;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_judul = itemView.findViewById(R.id.tv_judul);
            tv_tahun = itemView.findViewById(R.id.tv_tahun);
            iv_poster = itemView.findViewById(R.id.iv_poster);

        }
    }
}
