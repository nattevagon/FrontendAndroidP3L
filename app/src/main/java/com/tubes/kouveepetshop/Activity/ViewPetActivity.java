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
import com.tubes.kouveepetshop.Model.PetDAO;
import com.tubes.kouveepetshop.R;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewPetActivity extends AppCompatActivity {
    private TextView twName, twBirthdate, twPetType, twPetSize, twCustomer;
    private ImageView btnBack, btnDelete, btnEdit;
    private String sId, sName, sBirthdate, sPetType, sPetSize, sCustomer, sEmployee;
    private ProgressDialog progressDialog;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pet);

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
        twPetType = findViewById(R.id.twPetType);
        twPetSize = findViewById(R.id.twPetSize);
        twCustomer = findViewById(R.id.twCustomer);
        btnDelete = findViewById(R.id.btnDelete);
        btnEdit = findViewById(R.id.btnEdit);

        Intent i = getIntent();
        sId = i.getStringExtra("id");

        getData();

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ViewPetActivity.this, EditPetActivity.class);
                i.putExtra("id",sId);
                i.putExtra("name",sName);
                i.putExtra("birthdate",sBirthdate);
                i.putExtra("pettype",sPetType);
                i.putExtra("petsize",sPetSize);
                i.putExtra("customer",sCustomer);
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
        Call<List<PetDAO>> get = apiService.getByPet(sId);

        get.enqueue(new Callback<List<PetDAO>>() {
            @Override
            public void onResponse(Call<List<PetDAO>> call, Response<List<PetDAO>> response) {

                for(int i=0;i<response.body().size();i++)
                {
                    sId = response.body().get(i).getId_hewan();
                    sName = response.body().get(i).getNama();
                    sBirthdate = response.body().get(i).getTgl_lahir();
                    sPetType = response.body().get(i).getJenis_hewan();
                    sPetSize = response.body().get(i).getUkuran_hewan();
                    sCustomer = response.body().get(i).getCustomer();
                }

                twName.setText(sName);
                twBirthdate.setText(sBirthdate);
                twPetType.setText(sPetType);
                twPetSize.setText(sPetSize);
                twCustomer.setText(sCustomer);
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<List<PetDAO>> call, Throwable t) {
                Toast.makeText(ViewPetActivity.this, "Koneksi hilang", Toast.LENGTH_SHORT).show();
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
        Call<PetDAO> delete = apiService.deletePet(sId, sEmployee);

        delete.enqueue(new Callback<PetDAO>() {
            @Override
            public void onResponse(Call<PetDAO> call, Response<PetDAO> response) {
                Toast.makeText(ViewPetActivity.this, "Berhasil dihapus", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
                onBackPressed();
            }

            @Override
            public void onFailure(Call<PetDAO> call, Throwable t) {
                Toast.makeText(ViewPetActivity.this, "Koneksi hilang", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
