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
import com.example.afinal.models.MovieModel;

import java.util.ArrayList;
import java.util.List;

import javax.xml.transform.sax.SAXResult;

public class AdapterMovie extends RecyclerView.Adapter<AdapterMovie.ViewHolder> {
    private final List<MovieModel> datamovie = new ArrayList<>();
    public void addUser (List<MovieModel> datamovie) {
        this.datamovie.addAll(datamovie);
    }
    @NonNull
    @Override
    public AdapterMovie.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterMovie.ViewHolder holder, int position) {
        MovieModel movie = datamovie.get(position);
        holder.tv_judul.setText(movie.getOriginal_title());

        String releaseDate = movie.getRelease_date();
        if (releaseDate != null && !releaseDate.isEmpty()) {
            String year = releaseDate.substring(0, 4);
            holder.tv_tahun.setText(year);
        } else {
            holder.tv_tahun.setText("");
        }

        String posterUrl = "https://image.tmdb.org/t/p/w500" + movie.getPoster_path();
        Glide.with(holder.iv_poster.getContext())
                .load(posterUrl)
                .placeholder(R.drawable.baseline_image_search_24) // placeholder image saat sedang memuat
                .error(R.drawable.baseline_image_24) // gambar yang ditampilkan jika terjadi kesalahan
                .into(holder.iv_poster);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = datamovie.get(holder.getAdapterPosition()).getTitle();
                String tanggal = datamovie.get(holder.getAdapterPosition()).getRelease_date();
                String overview = datamovie.get(holder.getAdapterPosition()).getOverview();
                String vote = datamovie.get(holder.getAdapterPosition()).getVote_average();
                String poster = datamovie.get(holder.getAdapterPosition()).getPoster_path();
                String backdrop = datamovie.get(holder.getAdapterPosition()).getBackdrop_path();
                int jenis = R.drawable.baseline_movie_24p;

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
        return datamovie.size();
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
