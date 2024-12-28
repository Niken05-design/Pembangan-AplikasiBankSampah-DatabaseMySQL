package com.example.aplikasibanksampah.api;

import com.example.aplikasibanksampah.api.login.LoginResponse;
import com.example.aplikasibanksampah.api.register.RegisterResponse;
import com.example.aplikasibanksampah.api.tambahReservasi.ReservasiResponse;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ApiInterface {

    //API INTERFACE PELANGGAN
    @FormUrlEncoded
    @POST("loginMobile.php")
        // Sesuaikan dengan path ke login pada API Anda
    Call<LoginResponse> loginResponse(
            @Field("email") String email,
            @Field("password") String password);

    @FormUrlEncoded
    @POST("createReservasi.php")
        // Sesuaikan dengan path ke login pada API Anda
    Call<ReservasiResponse> reservasiResponse(
            @Field("nama") String nama,
            @Field("alamat") String alamat,
            @Field("telepon") String telepon,
            @Field("cabang") String cabang,
            @Field("berat_sampah") int beratSampah,
            @Field("id_user") int idUser);

    @FormUrlEncoded
    @POST("registerMobile.php")
        // Sesuaikan dengan path ke login pada API Anda
    Call<RegisterResponse> registerResponse(
            @Field("name") String name,
            @Field("email") String email,
            @Field("password") String password);





//
//    @FormUrlEncoded
//    @POST("TampilSemuaDataPesananBelumBayar")
//    Call<List<KeranjangPelangganResponseItem>> KeranjangPelangganResponseItem(
//            @Field("id_pelanggan") int idPelanggan);
//
//
//    @Multipart
//    @POST("BayarPesananPelangganMobile")
//    Call<PembayaranPelangganResponse> PembayaranPelangganResponse(
//            @Part("pesanan_id") RequestBody jenisKelamin,
//            @Part MultipartBody.Part buktiBayar
//            );
//

}
