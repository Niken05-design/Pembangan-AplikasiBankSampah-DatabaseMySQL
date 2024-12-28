package com.example.aplikasibanksampah;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.aplikasibanksampah.api.ApiClient;
import com.example.aplikasibanksampah.api.ApiInterface;
import com.example.aplikasibanksampah.api.login.LoginResponse;
import com.example.aplikasibanksampah.api.register.RegisterResponse;
import com.example.aplikasibanksampah.databinding.ActivityRegisterBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Register extends AppCompatActivity {

    String registerNama, registerEmail, registerPassword;

    ActivityRegisterBinding binding;

    ProgressDialog dialogLoading;
    ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        dialogLoading = new ProgressDialog(this);
        dialogLoading.setMessage("Loading...");
        dialogLoading.setCancelable(false);


        binding.registerBtn.setOnClickListener(v -> {
            registerNama = binding.registerNama.getText().toString();
            registerEmail = binding.registerEmail.getText().toString();
            registerPassword = binding.registerPassword.getText().toString();

            if (registerNama.isEmpty() || registerEmail.isEmpty() || registerPassword.isEmpty()) {
                Toast.makeText(Register.this, "Please fill all the fields", Toast.LENGTH_SHORT).show();
            } else {
                registerUser(registerNama, registerEmail, registerPassword);
            }
        });

    }

    private void registerUser(String registerNama, String registerEmail, String registerPassword) {
        dialogLoading.show();

        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<RegisterResponse> loginCall = apiInterface.registerResponse(registerNama,registerEmail, registerPassword);
        loginCall.enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                if (response.isSuccessful()) {
                    RegisterResponse registerResponse = response.body();
                    if (registerResponse != null && registerResponse.isSuccess()) {
                        Toast.makeText(Register.this, registerResponse.getMessage(), Toast.LENGTH_SHORT).show();


                        dialogLoading.dismiss();

                        Intent intentKeMain = new Intent(Register.this, Login.class);
                        intentKeMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intentKeMain);
                        finish();
                    } else {
                        Toast.makeText(Register.this, "register gagal: " + (registerResponse != null ? registerResponse.getMessage().toString() : "Response body is null"), Toast.LENGTH_SHORT).show();
                        dialogLoading.dismiss();
                    }
                } else {
                    Toast.makeText(Register.this, response.body().getMessage().toString(), Toast.LENGTH_SHORT).show();
                    dialogLoading.dismiss();
                }
            }

            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {
                Toast.makeText(Register.this, "Koneksi Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                dialogLoading.dismiss();
            }
        });

    }


}