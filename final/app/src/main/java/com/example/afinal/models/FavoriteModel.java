package com.example.afinal.models;

import android.os.Parcel;
import android.os.Parcelable;

public class FavoriteModel implements Parcelable {
    private int id;
    private String judul;
    private String tahun;
    private String poster;
    private int jenis;
    private String sinopsis;
    private String vote;
    private String backdrop;

    public FavoriteModel(int id, String judul, String tahun, String poster, int jenis, String sinopsis, String vote, String backdrop) {
        this.id = id;
        this.judul = judul;
        this.tahun = tahun;
        this.poster = poster;
        this.jenis = jenis;
        this.sinopsis = sinopsis;
        this.vote = vote;
        this.backdrop = backdrop;
    }

    public FavoriteModel() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getTahun() {
        return tahun;
    }

    public void setTahun(String tahun) {
        this.tahun = tahun;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public int getJenis() {
        return jenis;
    }

    public void setJenis(int jenis) {
        this.jenis = jenis;
    }

    public String getSinopsis() {
        return sinopsis;
    }

    public void setSinopsis(String sinopsis) {
        this.sinopsis = sinopsis;
    }

    public String getVote() {
        return vote;
    }

    public void setVote(String vote) {
        this.vote = vote;
    }

    public String getBackdrop() {
        return backdrop;
    }

    public void setBackdrop(String backdrop) {
        this.backdrop = backdrop;
    }

    protected FavoriteModel(Parcel in) {
        id = in.readInt();
        judul = in.readString();
        tahun = in.readString();
        poster = in.readString();
        jenis = in.readInt();
        sinopsis = in.readString();
        vote = in.readString();
        backdrop = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(judul);
        dest.writeString(tahun);
        dest.writeString(poster);
        dest.writeInt(jenis);
        dest.writeString(sinopsis);
        dest.writeString(vote);
        dest.writeString(backdrop);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<FavoriteModel> CREATOR = new Creator<FavoriteModel>() {
        @Override
        public FavoriteModel createFromParcel(Parcel in) {
            return new FavoriteModel(in);
        }

        @Override
        public FavoriteModel[] newArray(int size) {
            return new FavoriteModel[size];
        }
    };
}
