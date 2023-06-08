package com.example.afinal.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

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
    private ArrayList<FavoriteModel> favoriteModels;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorite, container, false);
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rv = view.findViewById(R.id.rv_favorite);
        LinearLayout ll_reload = view.findViewById(R.id.reload);
        ProgressBar progressBar = view.findViewById(R.id.progressBar);

        adapterFavorite = new AdapterFavorite();
        rv.setAdapter(adapterFavorite);
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));

        new LoadStudentsAsync(getActivity(), fav -> {
            if (fav.size() > 0) {
                favoriteModels = fav;
            } else {
                favoriteModels = null;
            }
            if (favoriteModels != null){
                showCurrentUser(favoriteModels);
            }
            else {
                showCurrentUser(new ArrayList<>());
                Toast.makeText(getActivity(), "kosong?", Toast.LENGTH_SHORT).show();
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
                ArrayList<FavoriteModel> favList = favoriteHelper.getAllNotes();
                favoriteHelper.close();
                handler.post(() -> weakCallback.get().postExecute(favList));
            });
        }
    }
    interface LoadStudentsCallback {
        void postExecute(ArrayList<FavoriteModel> favorite);
    }
}