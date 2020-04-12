package com.tubes.kouveepetshop.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.tubes.kouveepetshop.API.ApiClient;
import com.tubes.kouveepetshop.API.ApiInterface;
import com.tubes.kouveepetshop.Model.PetSizeDAO;
import com.tubes.kouveepetshop.Model.ServiceDAO;
import com.tubes.kouveepetshop.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddServiceActivity extends AppCompatActivity {
    private Button btnAdd;
    private ImageView btnBack;
    private EditText etNama, etPrice;
    private int idPetSize;
    private AutoCompleteTextView spPetSize;
    List<String> idListPetSize = new ArrayList<String>();
    List<String> nameListPetSize = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        setContentView(R.layout.activity_add_service);

        etNama = findViewById(R.id.etNama);
        spPetSize = findViewById(R.id.spPetSize);
        etPrice = findViewById(R.id.etPrice);
        btnBack = findViewById(R.id.btnBack);
        btnAdd = findViewById(R.id.btnAdd);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        loadSpinnerPetSize();

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(etNama.getText().toString().equalsIgnoreCase(""))
                {
                    Toast.makeText(AddServiceActivity.this, "Kosong", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Add();
                }
            }
        });
    }

    public void loadSpinnerPetSize(){
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<List<PetSizeDAO>> call = apiService.getAllPetSize();

        call.enqueue(new Callback<List<PetSizeDAO>>() {
            @Override
            public void onResponse(Call<List<PetSizeDAO>> call, Response<List<PetSizeDAO>> response) {
                for (int i = 0; i < response.body().size(); i++){
                    idListPetSize.add(response.body().get(i).getId_ukuran_hewan());
                    nameListPetSize.add(response.body().get(i).getNama());
                }

                ArrayAdapter<String> adapterPetSize = new ArrayAdapter<String>
                        (AddServiceActivity.this, android.R.layout.simple_list_item_1, nameListPetSize);
                spPetSize.setAdapter(adapterPetSize);

                spPetSize.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View view, MotionEvent motionEvent) {
                        spPetSize.showDropDown();
                        return false;
                    }
                });

                spPetSize.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        idPetSize = nameListPetSize.indexOf(spPetSize.getText().toString());
                        Toast.makeText(AddServiceActivity.this, "POS"+idPetSize, Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onFailure(Call<List<PetSizeDAO>> call, Throwable t) {
                Toast.makeText(AddServiceActivity.this, "Kesalahan Jaringan", Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void Add() {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<ServiceDAO> add = apiService.addService(etNama.getText().toString(), idListPetSize.get(idPetSize), etPrice.getText().toString());

        add.enqueue(new Callback<ServiceDAO>(){
            @Override
            public void onResponse(Call<ServiceDAO> call, Response<ServiceDAO> response) {
                Toast.makeText(AddServiceActivity.this, "Success"+etNama.getText().toString(), Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void onFailure(Call<ServiceDAO> call, Throwable t) {
                Toast.makeText(AddServiceActivity.this, "Fail", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}
