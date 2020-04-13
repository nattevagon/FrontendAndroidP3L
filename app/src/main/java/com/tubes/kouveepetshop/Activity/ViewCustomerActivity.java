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
import com.tubes.kouveepetshop.Java.SessionManager;
import com.tubes.kouveepetshop.Model.CustomerDAO;
import com.tubes.kouveepetshop.R;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewCustomerActivity extends AppCompatActivity {
    private TextView twName, twBirthdate, twAddress, twPhoneNumber, twInitial;
    private ImageView btnBack, btnDelete, btnEdit;
    private String sId, sName, sBirthdate, sAddress, sPhoneNumber, sEmployee;
    private ProgressDialog progressDialog;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_customer);

        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        progressDialog = new ProgressDialog(this);
        progressDialog.show();

        sessionManager = new SessionManager(this);
        HashMap<String, String> user = sessionManager.getUserDetail();
        sEmployee = user.get(sessionManager.NAME);


        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        twName = findViewById(R.id.twName);
        twBirthdate = findViewById(R.id.twBirthdate);
        twAddress = findViewById(R.id.twAddress);
        twPhoneNumber = findViewById(R.id.twPhoneNumber);
        twInitial = findViewById(R.id.twInitial);
        btnDelete = findViewById(R.id.btnDelete);
        btnEdit = findViewById(R.id.btnEdit);

        Intent i = getIntent();
        sId = i.getStringExtra("id");

        getData();

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ViewCustomerActivity.this, EditCustomerActivity.class);
                i.putExtra("id",sId);
                i.putExtra("name",sName);
                i.putExtra("birthdate",sBirthdate);
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
        Call<List<CustomerDAO>> get = apiService.getByCustomer(sId);

        get.enqueue(new Callback<List<CustomerDAO>>() {
            @Override
            public void onResponse(Call<List<CustomerDAO>> call, Response<List<CustomerDAO>> response) {

                for(int i=0;i<response.body().size();i++)
                {
                    sId = response.body().get(i).getId_customer();
                    sName = response.body().get(i).getNama();
                    sBirthdate = response.body().get(i).getTgl_lahir();
                    sAddress = response.body().get(i).getAlamat();
                    sPhoneNumber = response.body().get(i).getNo_telp();
                }

                twInitial.setText(sName.substring(0, 1));
                twName.setText(sName);
                twBirthdate.setText(sBirthdate);
                twAddress.setText(sAddress);
                twPhoneNumber.setText(sPhoneNumber);
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<List<CustomerDAO>> call, Throwable t) {
                Toast.makeText(ViewCustomerActivity.this, "Koneksi hilang", Toast.LENGTH_SHORT).show();
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
        Call<CustomerDAO> delete = apiService.deleteCustomer(sId, sEmployee);

        delete.enqueue(new Callback<CustomerDAO>() {
            @Override
            public void onResponse(Call<CustomerDAO> call, Response<CustomerDAO> response) {
                Toast.makeText(ViewCustomerActivity.this, "Berhasil dihapus", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
                onBackPressed();
            }

            @Override
            public void onFailure(Call<CustomerDAO> call, Throwable t) {
                Toast.makeText(ViewCustomerActivity.this, "Koneksi hilang", Toast.LENGTH_SHORT).show();
            }
        });
    }
    
}
