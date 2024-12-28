package com.example.aplikasibanksampah.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    public static final String BASE_URL = "https://470e-66-96-225-139.ngrok-free.app/"; // Ubah URL dasar menjadi sesuai dengan persyaratan
    private static final String API = BASE_URL+"bank_sampah/"; // Ubah URL dasar menjadi sesuai dengan persyaratan


    private static Retrofit retrofit;

    public static Retrofit getClient(){
        if(retrofit == null){
            retrofit = new Retrofit.Builder().baseUrl(API).addConverterFactory(GsonConverterFactory.create()).build();
        }
        return retrofit;
    }
}
