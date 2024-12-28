package com.example.aplikasibanksampah;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aplikasibanksampah.api.ApiClient;
import com.example.aplikasibanksampah.api.ApiInterface;
import com.example.aplikasibanksampah.api.login.LoginResponse;
import com.example.aplikasibanksampah.databinding.ActivityLoginBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.api.LogDescriptor;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity {
    String email;
    String password;

    ApiInterface apiInterface;

    SharedPreferences sharedPreferences;

    ActivityLoginBinding binding;

    ProgressDialog dialogLoading;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        sharedPreferences = getSharedPreferences("userData", MODE_PRIVATE);
        dialogLoading = new ProgressDialog(this);
        dialogLoading.setMessage("Loading...");
        dialogLoading.setCancelable(false);

        if (sharedPreferences.getBoolean("isLoggedIn", false)) {
            startActivity(new Intent(Login.this, MainActivity.class));
            finish();
        }


        binding.signupBtn.setOnClickListener(v -> {
            startActivity(new Intent(Login.this, Register.class));
            finish();
        });


        binding.loginBtn.setOnClickListener(v -> {
            email = binding.loginEmail.getText().toString();
            password = binding.loginPassword.getText().toString();

            if (email.isEmpty()) {
                binding.loginEmail.setError("Email tidak boleh kosong");
                binding.loginEmail.requestFocus();
            } else if (password.isEmpty()) {
                binding.loginPassword.setError("Password tidak boleh kosong");
                binding.loginPassword.requestFocus();
            } else {
                login(email, password);
            }
        });

        binding.signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),Register.class));
            }
        });

    }



    private void login(String email, String password) {
        dialogLoading.show();

        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<LoginResponse> loginCall = apiInterface.loginResponse(email, password);
        loginCall.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful()) {
                    LoginResponse loginResponse = response.body();
                    if (loginResponse != null && loginResponse.isSuccess()) {
                        Toast.makeText(Login.this, loginResponse.getMessage(), Toast.LENGTH_SHORT).show();

                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putInt("id_user", loginResponse.getData().getId());
                        editor.putString("name", loginResponse.getData().getName());
                        editor.putString("email", loginResponse.getData().getEmail());
                        editor.putString("password", password);
                        editor.putBoolean("isLoggedIn", true);
                        editor.apply();

                        dialogLoading.dismiss();

                        Intent intentKeMain = new Intent(Login.this, MainActivity.class);
                        intentKeMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intentKeMain);
                        finish();
                    } else {
                        Toast.makeText(Login.this, "Login gagal: " + (loginResponse != null ? loginResponse.getMessage().toString() : "Response body is null"), Toast.LENGTH_SHORT).show();
                        dialogLoading.dismiss();
                    }
                } else {
                    Toast.makeText(Login.this, response.body().getMessage().toString(), Toast.LENGTH_SHORT).show();
                    dialogLoading.dismiss();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Toast.makeText(Login.this, "Koneksi Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                dialogLoading.dismiss();
            }
        });
    }
}