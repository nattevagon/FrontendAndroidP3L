package com.tubes.kouveepetshop.Activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tubes.kouveepetshop.API.ApiClient;
import com.tubes.kouveepetshop.API.ApiInterface;
import com.tubes.kouveepetshop.Model.TransactionServiceDAO;
import com.tubes.kouveepetshop.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailTransactionServiceActivity extends AppCompatActivity {
    private ImageButton btnBack, btnCancel;
    private Button btnAddService, btnConfirmDone;
    private String sId, sCode, sDate, sPet, sSubtotal, sTotalPrice, sCustomerService, sStatus;
    private TextView twCode, twDate, twCS, twSubTotal, twPet;
    private int idPet, sumSubTotal, subTotal;
    List<String> idListPet = new ArrayList<String>();
    List<String> nameListPet = new ArrayList<String>();
    private List<TransactionServiceDAO> TransactionService;
    private RecyclerView recyclerView;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_transaction_service);

        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        progressDialog = new ProgressDialog(this);

        twCode = findViewById(R.id.twCode);
        twDate = findViewById(R.id.twDate);
        twCS = findViewById(R.id.twCS);
        twSubTotal = findViewById(R.id.twSubTotal);
        twPet = findViewById(R.id.twPet);
        btnAddService = findViewById(R.id.btnAddService);
        btnConfirmDone = findViewById(R.id.btnConfirmDone);
        btnCancel = findViewById(R.id.btnCancel);

        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        Intent i = getIntent();
        sCode = i.getStringExtra("kode");

        btnAddService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
        
        getData();

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });

        btnConfirmDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    public void onBack() {
        super.onPostResume();
        progressDialog.show();
        getData();
    }

    private void getData(){
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<List<TransactionServiceDAO>> get = apiService.getByCodeTransactionService(sCode);

        get.enqueue(new Callback<List<TransactionServiceDAO>>() {
            @Override
            public void onResponse(Call<List<TransactionServiceDAO>> call, Response<List<TransactionServiceDAO>> response) {
                for(int i=0;i<response.body().size();i++)
                {
                    sId = response.body().get(i).getId_tl();
                    sCode = response.body().get(i).getKode();
                    sDate = response.body().get(i).getTanggal();
                    sPet = response.body().get(i).getHewan();
                    sCustomerService = response.body().get(i).getCustomer_service();
                    sStatus = response.body().get(i).getStatus();
                }

                twCode.setText(sCode);
                twDate.setText(sDate);
                twCS.setText(sCustomerService);
                twPet.setText(sPet);
            }

            @Override
            public void onFailure(Call<List<TransactionServiceDAO>> call, Throwable t) {
                Toast.makeText(DetailTransactionServiceActivity.this, "Tidak ditemukan atau jaringan tidak ada", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
    }

}
