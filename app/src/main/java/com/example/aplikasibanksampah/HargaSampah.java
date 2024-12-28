package com.example.aplikasibanksampah;


import java.io.Serializable;

public class HargaSampah implements Serializable {
    private String artisName;
    private String artisRating;
    private String artisKategori;
    private Integer kategoriPhoto;
    private Integer photo;
    private String followerTik;
    private String followerIns;

    // Constructor
    public HargaSampah(String artisName, String artisRating, String artisKategori, Integer kategoriPhoto, Integer photo, String followerTik, String followerIns) {
        this.artisName = artisName;
        this.artisRating = artisRating;
        this.artisKategori = artisKategori;
        this.kategoriPhoto = kategoriPhoto;
        this.photo = photo;
        this.followerTik = followerTik;
        this.followerIns = followerIns;
    }

    // Getter dan Setter untuk setiap field
    public String getArtisName() {
        return artisName;
    }

    public void setArtisName(String artisName) {
        this.artisName = artisName;
    }

    public String getArtisRating() {
        return artisRating;
    }

    public void setArtisRating(String artisRating) {
        this.artisRating = artisRating;
    }

    public String getArtisKategori() {
        return artisKategori;
    }

    public void setArtisKategori(String artisKategori) {
        this.artisKategori = artisKategori;
    }

    public Integer getKategoriPhoto() {
        return kategoriPhoto;
    }

    public void setKategoriPhoto(Integer kategoriPhoto) {
        this.kategoriPhoto = kategoriPhoto;
    }

    public Integer getPhoto() {
        return photo;
    }

    public void setPhoto(Integer photo) {
        this.photo = photo;
    }

    public String getFollowerTik() {
        return followerTik;
    }

    public void setFollowerTik(String followerTik) {
        this.followerTik = followerTik;
    }

    public String getFollowerIns() {
        return followerIns;
    }

    public void setFollowerIns(String followerIns) {
        this.followerIns = followerIns;
    }
}
