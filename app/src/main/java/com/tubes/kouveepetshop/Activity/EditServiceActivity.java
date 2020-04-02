package com.tubes.kouveepetshop.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.tubes.kouveepetshop.API.ApiClient;
import com.tubes.kouveepetshop.API.ApiInterface;
import com.tubes.kouveepetshop.Model.ServiceDAO;
import com.tubes.kouveepetshop.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditServiceActivity extends AppCompatActivity {
    private Button btnSave;
    private ImageView btnBack;
    private EditText etNama;
    private String sId, sName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_service);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

        etNama = findViewById(R.id.etNama);
        btnBack = findViewById(R.id.btnBack);
        btnSave = findViewById(R.id.btnSave);

        Intent i = getIntent();
        sId = i.getStringExtra("id");
        sName = i.getStringExtra("name");

        etNama.setText(sName);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(etNama.getText().toString().equalsIgnoreCase(""))
                {
                    Toast.makeText(EditServiceActivity.this, "Kosong", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Edit();
                }
            }
        });
    }
    private void Edit() {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<ServiceDAO> add = apiService.updateService(sId, etNama.getText().toString());

        add.enqueue(new Callback<ServiceDAO>(){
            @Override
            public void onResponse(Call<ServiceDAO> call, Response<ServiceDAO> response) {
                Toast.makeText(EditServiceActivity.this, "Success", Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void onFailure(Call<ServiceDAO> call, Throwable t) {
                Toast.makeText(EditServiceActivity.this, "Fail", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}
