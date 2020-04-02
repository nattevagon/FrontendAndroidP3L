package com.tubes.kouveepetshop.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.tubes.kouveepetshop.API.ApiClient;
import com.tubes.kouveepetshop.API.ApiInterface;
import com.tubes.kouveepetshop.Model.PetTypeDAO;
import com.tubes.kouveepetshop.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddPetTypeActivity extends AppCompatActivity {
    private Button btnAdd;
    private ImageView btnBack;
    private EditText etNama;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_pet_type);

        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        progressDialog =new ProgressDialog(this);

        etNama = findViewById(R.id.etNama);
        btnBack = findViewById(R.id.btnBack);
        btnAdd = findViewById(R.id.btnAdd);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Add();
            }
        });

    }

    private void Add() {
        progressDialog.show();
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<PetTypeDAO> add = apiService.addPetType(etNama.getText().toString());

        add.enqueue(new Callback<PetTypeDAO>() {
            @Override
            public void onResponse(Call<PetTypeDAO> call, Response<PetTypeDAO> response) {
                Toast.makeText(AddPetTypeActivity.this, "Success"+etNama.getText().toString(), Toast.LENGTH_SHORT).show();
                onBackPressed();
            }

            @Override
            public void onFailure(Call<PetTypeDAO> call, Throwable t) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        progressDialog.dismiss();
    }
}
