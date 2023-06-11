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

import java.util.ArrayList;

public class FavoriteHelper {
    private static final String DATABASE_TABLE = DatabaseContract.TABLE_NAME;
    private static DatabaseHelper databaseHelper;
    private static SQLiteDatabase database;
    private static volatile FavoriteHelper INSTANCE;

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

    public ArrayList<FavoriteModel> getAllQuery() {
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
            FavoriteModel favoriteMovieModel = getFromCursor(cursor);
            notesList.add(favoriteMovieModel);
            cursor.moveToNext();
        }
        cursor.close();
        return notesList;
    }

    private FavoriteModel getFromCursor(Cursor cursor) {
        FavoriteModel favoriteModel = new FavoriteModel();
        favoriteModel.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.FavoriteColumns._ID))));
        favoriteModel.setJudul(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.FavoriteColumns.TITLE)));
        favoriteModel.setTahun(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.FavoriteColumns.TAHUN)));
        favoriteModel.setPoster(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.FavoriteColumns.POSTER)));
        favoriteModel.setJenis(Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.FavoriteColumns.JENIS))));
        favoriteModel.setBackdrop(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.FavoriteColumns.BACKDROP)));
        favoriteModel.setVote(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.FavoriteColumns.VOTE)));
        favoriteModel.setSinopsis(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.FavoriteColumns.SINOPSIS)));
       return favoriteModel;
    }

    public static long insert(ContentValues values) {
        return database.insert(DATABASE_TABLE, null, values);
    }

    @SuppressLint("Range")
    public ArrayList<FavoriteModel> searchData(String searchText) {
        ArrayList<FavoriteModel> searchResults = new ArrayList<>();

        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        String query = "SELECT * FROM " + DatabaseContract.TABLE_NAME + " WHERE " + DatabaseContract.FavoriteColumns.TITLE + " LIKE '" + searchText + "%'";
        Cursor cursor = db.rawQuery(query, null);

        if (cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                FavoriteModel favoriteMovieModel = getFromCursor(cursor);
                searchResults.add(favoriteMovieModel);
            }
        }

        if (cursor != null) {
            cursor.close();
        }

        return searchResults;
    }

    public boolean checkDataExists(String title) {
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        String[] projection = {DatabaseContract.FavoriteColumns._ID};
        String selection = DatabaseContract.FavoriteColumns.TITLE + " = ?";
        String[] selectionArgs = {title};
        Cursor cursor = db.query(DatabaseContract.TABLE_NAME, projection, selection, selectionArgs, null, null, null);
        boolean exists = cursor != null && cursor.moveToFirst();
        if (cursor != null) {
            cursor.close();
        }
        return exists;
    }

    public int deleteBytitle(String title) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        String whereClause = DatabaseContract.FavoriteColumns.TITLE + " = ?";
        String[] whereArgs = {title};
        return db.delete(DatabaseContract.TABLE_NAME, whereClause, whereArgs);
    }
}
