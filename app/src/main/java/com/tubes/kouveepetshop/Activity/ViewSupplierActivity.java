package com.tubes.kouveepetshop.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.tubes.kouveepetshop.API.ApiClient;
import com.tubes.kouveepetshop.API.ApiInterface;
import com.tubes.kouveepetshop.Model.SupplierDAO;
import com.tubes.kouveepetshop.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewSupplierActivity extends AppCompatActivity {
    private TextView twName, twAddress, twPhoneNumber;
    private ImageView btnBack, btnDelete, btnEdit;
    private String sId, sName, sAddress, sPhoneNumber;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_supplier);


        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        progressDialog = new ProgressDialog(this);
        progressDialog.show();

        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        twName = findViewById(R.id.twName);
        twAddress = findViewById(R.id.twAddress);
        twPhoneNumber = findViewById(R.id.twPhoneNumber);
        btnDelete = findViewById(R.id.btnDelete);
        btnEdit = findViewById(R.id.btnEdit);

        Intent i = getIntent();
        sId = i.getStringExtra("id");

        getData();

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ViewSupplierActivity.this, EditSupplierActivity.class);
                i.putExtra("id",sId);
                i.putExtra("name",sName);
                i.putExtra("address",sAddress);
                i.putExtra("phonenumber",sPhoneNumber);
                startActivity(i);
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                deleteItemFromList(view);
            }
        });
    }

    @Override
    public void onRestart() {
        super.onRestart();
        getData();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    private void getData()
    {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<List<SupplierDAO>> get = apiService.getBySupplier(sId);

        get.enqueue(new Callback<List<SupplierDAO>>() {
            @Override
            public void onResponse(Call<List<SupplierDAO>> call, Response<List<SupplierDAO>> response) {

                for(int i=0;i<response.body().size();i++)
                {
                    sId = response.body().get(i).getId_supplier();
                    sName = response.body().get(i).getNama();
                    sAddress = response.body().get(i).getAlamat();
                    sPhoneNumber = response.body().get(i).getNo_telp();
                }

                twName.setText(sName);
                twAddress.setText(sAddress);
                twPhoneNumber.setText(sPhoneNumber);

                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<List<SupplierDAO>> call, Throwable t) {
                Toast.makeText(ViewSupplierActivity.this, "Koneksi hilang", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
    }

    private void deleteItemFromList(View v) {

        AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());

        builder.setMessage("Hapus data ?")
                .setCancelable(false)
                .setPositiveButton("HAPUS",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                deleteData();
                            }
                        })
                .setNegativeButton("BATAL", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });

        builder.show();

    }

    private void deleteData()
    {
        progressDialog.show();
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<SupplierDAO> delete = apiService.deleteSupplier(sId);

        delete.enqueue(new Callback<SupplierDAO>() {
            @Override
            public void onResponse(Call<SupplierDAO> call, Response<SupplierDAO> response) {
                Toast.makeText(ViewSupplierActivity.this, "Berhasil dihapus", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
                onBackPressed();
            }

            @Override
            public void onFailure(Call<SupplierDAO> call, Throwable t) {
                Toast.makeText(ViewSupplierActivity.this, "Koneksi hilang", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
