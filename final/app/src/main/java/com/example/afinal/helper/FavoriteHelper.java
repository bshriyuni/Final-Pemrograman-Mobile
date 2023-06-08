package com.example.afinal.helper;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.afinal.database.DatabaseContract;
import com.example.afinal.database.DatabaseHelper;
import com.example.afinal.models.FavoriteModel;
import com.example.afinal.models.MovieModel;

import java.util.ArrayList;

public class FavoriteHelper {
    private static final String DATABASE_TABLE = DatabaseContract.TABLE_NAME;
    private static DatabaseHelper databaseHelper;
    private static SQLiteDatabase database;
    private static volatile FavoriteHelper INSTANCE;
    private FavoriteModel favoriteModel;

    private FavoriteHelper(Context context) {
        databaseHelper = new DatabaseHelper(context);
    }

    public static FavoriteHelper getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (SQLiteOpenHelper.class) {
                if (INSTANCE == null) {
                    INSTANCE = new FavoriteHelper(context);
                }
            }
        }
        return INSTANCE;
    }

    public void open() throws SQLException {
        database = databaseHelper.getWritableDatabase();
    }

    public void close() {
        databaseHelper.close();
        if (database.isOpen()) {
            database.close();
        }
    }

    public ArrayList<FavoriteModel> getAllNotes() {
        ArrayList<FavoriteModel> notesList = new ArrayList<>();
        Cursor cursor = database.query(
                DATABASE_TABLE,
                null,
                null,
                null,
                null,
                null,
                DatabaseContract.FavoriteColumns._ID + " DESC"
        );
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            FavoriteModel favoriteModel = getNotesFromCursor(cursor);
            notesList.add(favoriteModel);
            cursor.moveToNext();
        }
        cursor.close();
        return notesList;
    }

    private FavoriteModel getNotesFromCursor(Cursor cursor) {
        FavoriteModel favoriteModel = new FavoriteModel();
        favoriteModel.setId(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.FavoriteColumns._ID)));
        favoriteModel.setJudul(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.FavoriteColumns.TITLE)));
        favoriteModel.setTahun(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.FavoriteColumns.TAHUN)));
        favoriteModel.setPoster(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.FavoriteColumns.POSTER)));
        favoriteModel.setJenis(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.FavoriteColumns.JENIS)));
        favoriteModel.setBackdrop(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.FavoriteColumns.BACKDROP)));
        favoriteModel.setSinopsis(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.FavoriteColumns.SINOPSIS)));
        favoriteModel.setVote(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.FavoriteColumns.VOTE)));
        return favoriteModel;
    }

    public static long insert(ContentValues values) {
        return database.insert(DATABASE_TABLE, null, values);
    }

    public static int update(String id, ContentValues values) {
        return database.update(DATABASE_TABLE, values, DatabaseContract.FavoriteColumns._ID
                + " = ?", new String[]{id});
    }

    public int deleteById(String id) {
        return database.delete(DATABASE_TABLE, DatabaseContract.FavoriteColumns._ID + " = "
                + id, null);
    }

    @SuppressLint("Range")
    public ArrayList<FavoriteModel> searchNotes(String searchText) {
        ArrayList<FavoriteModel> searchResults = new ArrayList<>();

        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        String query = "SELECT * FROM " + DatabaseContract.TABLE_NAME + " WHERE " + DatabaseContract.FavoriteColumns.TITLE + " LIKE '" + searchText + "%'";
        Cursor cursor = db.rawQuery(query, null);

        if (cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                FavoriteModel favoriteModel = getNotesFromCursor(cursor);
                searchResults.add(favoriteModel);
            }
        }

        if (cursor != null) {
            cursor.close();
        }

        return searchResults;
    }

    public void insertToFavorite(MovieModel movieModel){
//        SQLiteDatabase db = databaseHelper.getReadableDatabase();
//        values.put(DatabaseContract.FavoriteColumns.TITLE, favoriteModel.getJudul());
//        values.put(DatabaseContract.FavoriteColumns.POSTER, favoriteModel.getPoster());
//        values.put(DatabaseContract.FavoriteColumns.TAHUN, favoriteModel.getTahun());
//        values.put(DatabaseContract.FavoriteColumns.JENIS, favoriteModel.getJenis());
    }
}
