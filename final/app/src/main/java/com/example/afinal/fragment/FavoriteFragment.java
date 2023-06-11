package com.example.afinal.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.afinal.R;
import com.example.afinal.adapter.AdapterFavorite;
import com.example.afinal.helper.FavoriteHelper;
import com.example.afinal.models.FavoriteModel;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FavoriteFragment extends Fragment {
    AdapterFavorite adapterFavorite;
    RecyclerView rv;
    private ArrayList<FavoriteModel> favoriteMovieModel;
    private FavoriteHelper favoriteHelper;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorite, container, false);
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rv = view.findViewById(R.id.rv_favorite);
        TextView tv_add = view.findViewById(R.id.tv_addfirst);

        adapterFavorite = new AdapterFavorite();
        rv.setAdapter(adapterFavorite);
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));

        new LoadStudentsAsync(getActivity(), fav -> {
            if (fav.size() > 0) {
                favoriteMovieModel = fav;
            } else {
                favoriteMovieModel = null;
            }
            if (favoriteMovieModel != null){
                showCurrentUser(favoriteMovieModel);
                tv_add.setVisibility(View.GONE);
            }
            else {
                showCurrentUser(new ArrayList<>());
            }

        }).execute();
    }

    private void showCurrentUser(ArrayList<FavoriteModel> favorite) {
        adapterFavorite.setData(favorite);
    }

    private static class LoadStudentsAsync {
        private final WeakReference<Context> weakContext;
        private final WeakReference<LoadStudentsCallback> weakCallback;
        private LoadStudentsAsync(Context context, LoadStudentsCallback callback) {
            weakContext = new WeakReference<>(context);
            weakCallback = new WeakReference<>(callback);
        }
        void execute() {
            ExecutorService executor = Executors.newSingleThreadExecutor();
            Handler handler = new Handler(Looper.getMainLooper());
            executor.execute(() -> {
                Context context = weakContext.get();
                FavoriteHelper favoriteHelper = FavoriteHelper.getInstance(context);
                favoriteHelper.open();
                ArrayList<FavoriteModel> favList = favoriteHelper.getAllQuery();
                favoriteHelper.close();
                handler.post(() -> weakCallback.get().postExecute(favList));
            });
        }
    }
    interface LoadStudentsCallback {
        void postExecute(ArrayList<FavoriteModel> favorite);
    }
}