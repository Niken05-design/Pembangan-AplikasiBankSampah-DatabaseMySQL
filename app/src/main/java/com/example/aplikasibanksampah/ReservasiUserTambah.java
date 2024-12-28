package com.example.aplikasibanksampah;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.aplikasibanksampah.api.ApiClient;
import com.example.aplikasibanksampah.api.ApiInterface;
import com.example.aplikasibanksampah.api.login.LoginResponse;
import com.example.aplikasibanksampah.api.tambahReservasi.ReservasiResponse;
import com.example.aplikasibanksampah.databinding.ActivityReservasiUserTambahBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReservasiUserTambah extends AppCompatActivity {

    String namaReservasi, alamatReservasi, teleponReservasi, beratSampahReservasi;

    SharedPreferences sharedPreferences;
    ActivityReservasiUserTambahBinding binding;

    ProgressDialog dialogLoading;

    ApiInterface apiInterface;

    int idUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityReservasiUserTambahBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        sharedPreferences = getSharedPreferences("userData", MODE_PRIVATE);

        binding.namaTambahReservasi.setText(sharedPreferences.getString("name", ""));

        idUser = sharedPreferences.getInt("id_user", 0);

        dialogLoading = new ProgressDialog(this);
        dialogLoading.setMessage("Loading...");
        dialogLoading.setCancelable(false);


        binding.btnTambahSampah.setOnClickListener(v -> {

            dialogLoading.show();

            namaReservasi = binding.namaTambahReservasi.getText().toString();
            alamatReservasi = binding.alamatTambahSampah.getText().toString();
            teleponReservasi = binding.teleponTambahSampah.getText().toString();
            beratSampahReservasi = binding.beratTambahSampah.getText().toString();

            if (namaReservasi.isEmpty() || alamatReservasi.isEmpty() || teleponReservasi.isEmpty() || beratSampahReservasi.isEmpty()) {
                Toast.makeText(this, "Data tidak boleh kosong", Toast.LENGTH_SHORT).show();
                dialogLoading.dismiss();
            } else {
                
                tambahReservasi(namaReservasi, alamatReservasi, teleponReservasi, Integer.parseInt(beratSampahReservasi));

            }


        });
    }

    private void tambahReservasi(String namaReservasi, String alamatReservasi, String teleponReservasi, int beratSampahReservasi) {
        dialogLoading.show();

        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<ReservasiResponse> loginCall = apiInterface.reservasiResponse(namaReservasi, alamatReservasi, teleponReservasi, "Peduli Sungai Surabaya (PSS)", beratSampahReservasi, idUser );
        loginCall.enqueue(new Callback<ReservasiResponse>() {
            @Override
            public void onResponse(Call<ReservasiResponse> call, Response<ReservasiResponse> response) {
                if (response.isSuccessful()) {
                    ReservasiResponse reservasiResponse = response.body();
                    if (reservasiResponse != null && reservasiResponse.isSuccess() ) {
                        Toast.makeText(ReservasiUserTambah.this, reservasiResponse.getMessage(), Toast.LENGTH_SHORT).show();

                        dialogLoading.dismiss();

                        finish();
                    } else {
                        Toast.makeText(ReservasiUserTambah.this, "Tambah Reservasi gagal: " + (reservasiResponse != null ? reservasiResponse.getMessage().toString() : "Response body is null"), Toast.LENGTH_SHORT).show();
                        dialogLoading.dismiss();
                    }
                } else {
                    Toast.makeText(ReservasiUserTambah.this, response.body().getMessage().toString(), Toast.LENGTH_SHORT).show();
                    dialogLoading.dismiss();
                }
            }

            @Override
            public void onFailure(Call<ReservasiResponse> call, Throwable t) {
                Toast.makeText(ReservasiUserTambah.this, "Koneksi Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                dialogLoading.dismiss();
            }
        });
    }


}