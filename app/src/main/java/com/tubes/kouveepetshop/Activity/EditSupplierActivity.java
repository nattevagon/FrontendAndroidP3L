package com.tubes.kouveepetshop.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.tubes.kouveepetshop.API.ApiClient;
import com.tubes.kouveepetshop.API.ApiInterface;
import com.tubes.kouveepetshop.Model.SupplierDAO;
import com.tubes.kouveepetshop.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditSupplierActivity extends AppCompatActivity {
    private ImageButton btnBack;
    private EditText etName, etAddress, etPhoneNumber;
    private Button btnSave, btnDate;
    private String sId, sName, sAddress, sPhoneNumber;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_supplier);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        progressDialog = new ProgressDialog(this);

        etName = findViewById(R.id.etName);
        etAddress = findViewById(R.id.etAddress);
        etPhoneNumber = findViewById(R.id.etPhoneNumber);
        btnSave = findViewById(R.id.btnSave);

        Intent i = getIntent();
        sId = i.getStringExtra("id");
        sName = i.getStringExtra("name");
        sAddress = i.getStringExtra("address");
        sPhoneNumber = i.getStringExtra("phonenumber");

        etName.setText(sName);
        etAddress.setText(sAddress);
        etPhoneNumber.setText(sPhoneNumber);

        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.show();
                Update();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    private void Update() {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<SupplierDAO> update = apiService.updateSupplier(sId, etName.getText().toString(), etPhoneNumber.getText().toString()
                ,etAddress.getText().toString());

        update.enqueue(new Callback<SupplierDAO>(){
            @Override
            public void onResponse(Call<SupplierDAO> call, Response<SupplierDAO> response) {
                Toast.makeText(EditSupplierActivity.this, "Success"+sId, Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
                onBackPressed();
            }

            @Override
            public void onFailure(Call<SupplierDAO> call, Throwable t) {
                Toast.makeText(EditSupplierActivity.this, "Fail"+sId, Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
                onBackPressed();
            }
        });
    }
}
