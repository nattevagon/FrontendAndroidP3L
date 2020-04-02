package com.tubes.kouveepetshop.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.tubes.kouveepetshop.API.ApiClient;
import com.tubes.kouveepetshop.API.ApiInterface;
import com.tubes.kouveepetshop.Model.CustomerDAO;
import com.tubes.kouveepetshop.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailCustomerActivity extends AppCompatActivity {
    private TextView twName, twBirthdate, twAddress, twPhoneNumber, twInitial;
    private Button btnEdit;
    private ImageView btnBack, btnDelete;
    private String sId, sName, sBirthdate, sAddress, sPhoneNumber;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_customer);


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
                Intent i = new Intent(DetailCustomerActivity.this, EditCustomerActivity.class);
                i.putExtra("id",sId);
                i.putExtra("name",sName);
                i.putExtra("birthdate",sBirthdate);
                i.putExtra("address",sAddress);
                i.putExtra("phonenumber",sPhoneNumber);
                startActivity(i);
            }
        });

        Toast.makeText(DetailCustomerActivity.this, "Coba hapus"+sId, Toast.LENGTH_SHORT).show();

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
        Toast.makeText(DetailCustomerActivity.this, "Coba hapus"+sId, Toast.LENGTH_SHORT).show();
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
                    sId = response.body().get(i).getID_CUSTOMER();
                    sName = response.body().get(i).getNAMA();
                    sBirthdate = response.body().get(i).getTGL_LAHIR();
                    sAddress = response.body().get(i).getALAMAT();
                    sPhoneNumber = response.body().get(i).getNO_TELP();
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
                Toast.makeText(DetailCustomerActivity.this, "Koneksi hilang", Toast.LENGTH_SHORT).show();
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
        Call<CustomerDAO> delete = apiService.deleteCustomer(sId);

        delete.enqueue(new Callback<CustomerDAO>() {
            @Override
            public void onResponse(Call<CustomerDAO> call, Response<CustomerDAO> response) {
                Toast.makeText(DetailCustomerActivity.this, "Berhasil dihapus", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
                onBackPressed();
            }

            @Override
            public void onFailure(Call<CustomerDAO> call, Throwable t) {
                Toast.makeText(DetailCustomerActivity.this, "Koneksi hilang", Toast.LENGTH_SHORT).show();
            }
        });
    }
    
}
