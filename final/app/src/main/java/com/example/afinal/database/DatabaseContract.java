package com.example.afinal.database;

import android.provider.BaseColumns;

public class DatabaseContract {
    public static String TABLE_NAME = "favorite";
    public static final class FavoriteColumns implements BaseColumns{
        public static String TITLE = "title";
        public static String TAHUN = "tahun";
        public static String POSTER = "poster";
        public static String JENIS = "jenis";
        public  static String BACKDROP = "backdrop";
        public static  String VOTE = "vote";
        public static String SINOPSIS = "sinopsis";
    }
}
