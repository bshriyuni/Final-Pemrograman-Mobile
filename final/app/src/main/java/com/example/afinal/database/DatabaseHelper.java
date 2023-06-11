package com.example.afinal.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static String DATABASE_NAME = "favorite.db";
    private static final int DATABASE_VERSION = 1;

    private static final String SQL_CREATE_TABLE_NOTES =
            String.format(
                    "CREATE TABLE %s"
                            + " (%s INTEGER PRIMARY KEY AUTOINCREMENT,"
                            + " %s TEXT NOT NULL,"
                            + " %s TEXT NOT NULL,"
                            + " %s INTEGER NOT NULL,"
                            + " %s TEXT NOT NULL,"
                            + " %s TEXT NOT NULL,"
                            + " %s TEXT NOT NULL,"
                            + " %s TEXT NOT NULL)",
                    DatabaseContract.TABLE_NAME,
                    DatabaseContract.FavoriteColumns._ID,
                    DatabaseContract.FavoriteColumns.TITLE,
                    DatabaseContract.FavoriteColumns.TAHUN,
                    DatabaseContract.FavoriteColumns.JENIS,
                    DatabaseContract.FavoriteColumns.POSTER,
                    DatabaseContract.FavoriteColumns.BACKDROP,
                    DatabaseContract.FavoriteColumns.VOTE,
                    DatabaseContract.FavoriteColumns.SINOPSIS
            );

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_TABLE_NOTES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DatabaseContract.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

}
