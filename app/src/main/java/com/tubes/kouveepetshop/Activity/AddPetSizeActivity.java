package com.tubes.kouveepetshop.Activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.tubes.kouveepetshop.API.ApiClient;
import com.tubes.kouveepetshop.API.ApiInterface;
import com.tubes.kouveepetshop.Model.PetSizeDAO;
import com.tubes.kouveepetshop.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddPetSizeActivity extends AppCompatActivity {
    private Button btnAdd;
    private ImageView btnBack;
    private EditText etName;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_pet_size);

        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        progressDialog =new ProgressDialog(this);

        etName = findViewById(R.id.etName);
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
                if(etName.getText().toString().equalsIgnoreCase(""))
                {
                    etName.setError("Kosong!");
                    etName.requestFocus();
                }
                else
                {
                    progressDialog.show();
                    Add();
                }
            }
        });

    }

    private void Add() {
        progressDialog.show();
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<PetSizeDAO> add = apiService.addPetSize(etName.getText().toString());

        add.enqueue(new Callback<PetSizeDAO>() {
            @Override
            public void onResponse(Call<PetSizeDAO> call, Response<PetSizeDAO> response) {
                Toast.makeText(AddPetSizeActivity.this, "Sukses menambah data", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
                onBackPressed();
            }

            @Override
            public void onFailure(Call<PetSizeDAO> call, Throwable t) {
                Toast.makeText(AddPetSizeActivity.this, "Gagal menambah data", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}
