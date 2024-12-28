package com.example.aplikasibanksampah;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aplikasibanksampah.databinding.ActivityMainBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {

    //Button
    ImageView logout; //Button Logout
    View btnHargaJenis, btnResevasiPickUp, btnLokasiBankSampah;
    TextView UserName;

    SharedPreferences sharedPreferences;

    ActivityMainBinding binding;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        logout = findViewById(R.id.btnLogout);
        btnHargaJenis = findViewById(R.id.btnHargaJenis);
        btnResevasiPickUp = findViewById(R.id.btnReservasiPickUp);
        btnLokasiBankSampah = findViewById(R.id.btnTampilkanLokasiBankSampah);

        sharedPreferences = getSharedPreferences("userData", MODE_PRIVATE);

        binding.UserName.setText(sharedPreferences.getString("name", ""));

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),Login.class));
                sharedPreferences.edit().clear().apply();
                finish();
            }
        });

        btnHargaJenis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),HargaDanJenisUser.class));
            }
        });

        btnResevasiPickUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),ReservasiUserTambah.class));
            }
        });

        btnLokasiBankSampah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),LokasiBankSampah.class));
            }
        });
    }


}