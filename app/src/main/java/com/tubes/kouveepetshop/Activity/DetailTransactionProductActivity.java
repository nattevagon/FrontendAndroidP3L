package com.tubes.kouveepetshop.Activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tubes.kouveepetshop.API.ApiClient;
import com.tubes.kouveepetshop.API.ApiInterface;
import com.tubes.kouveepetshop.Fragment.AddDetailProductTransactionFragment;
import com.tubes.kouveepetshop.Model.DetailTransactionProductDAO;
import com.tubes.kouveepetshop.Model.PetDAO;
import com.tubes.kouveepetshop.Model.TransactionProductDAO;
import com.tubes.kouveepetshop.R;
import com.tubes.kouveepetshop.RecyclerAdapter.DetailTransactionProductRecyclerAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailTransactionProductActivity extends AppCompatActivity {
    private ImageButton btnBack;
    private Button btnAddProduct;
    private String sId, sCode, sDate, sPet, sSubtotal, sTotalPrice, sCustomerService, sStatus;
    private TextView twCode, twDate, twCS;
    private AutoCompleteTextView spPet;
    private int idPet;
    List<String> idListPet = new ArrayList<String>();
    List<String> nameListPet = new ArrayList<String>();

    private List<DetailTransactionProductDAO> detailTPProductList;
    private RecyclerView recyclerView;
    private DetailTransactionProductRecyclerAdapter recyclerAdapter;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_product_transaction);

        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        progressDialog = new ProgressDialog(this);

        twCode = findViewById(R.id.twCode);
        twDate = findViewById(R.id.twDate);
        twCS = findViewById(R.id.twCS);
        spPet = findViewById(R.id.spPet);
        btnAddProduct = findViewById(R.id.btnAddProduct);

        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        Intent i = getIntent();
        sCode = i.getStringExtra("kode");

        btnAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager manager = DetailTransactionProductActivity.this.getSupportFragmentManager();
                AddDetailProductTransactionFragment dialog = new AddDetailProductTransactionFragment();
                dialog.show(manager, "dialog");

                Bundle args = new Bundle();
                args.putString("id", sId);
                dialog.setArguments(args);
            }
        });

        detailTPProductList = new ArrayList<>();
        recyclerView = findViewById(R.id.detailTPRecyclerView);
        recyclerAdapter = new DetailTransactionProductRecyclerAdapter(DetailTransactionProductActivity.this,detailTPProductList);
        RecyclerView.LayoutManager LayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(LayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(recyclerAdapter);

        getData();
        loadSpinnerPet();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    public void onBack() {
        super.onPostResume();
        progressDialog.show();
        detailTPProductList.removeAll(detailTPProductList);
        getData();
    }

    private void getData(){
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<List<TransactionProductDAO>> get = apiService.getByCodeTransactionProduct(sCode);

        get.enqueue(new Callback<List<TransactionProductDAO>>() {
            @Override
            public void onResponse(Call<List<TransactionProductDAO>> call, Response<List<TransactionProductDAO>> response) {
                for(int i=0;i<response.body().size();i++)
                {
                    sId = response.body().get(i).getId_tp();
                    sCode = response.body().get(i).getKode();
                    sDate = response.body().get(i).getTanggal();
                    sPet = response.body().get(i).getHewan();
                    sCustomerService = response.body().get(i).getCustomer_service();
                    sStatus = response.body().get(i).getStatus();
                }

                twCode.setText(sCode);
                twDate.setText(sDate);
                twCS.setText(sCustomerService);
                spPet.setText(sPet);

                loadProduct();
            }

            @Override
            public void onFailure(Call<List<TransactionProductDAO>> call, Throwable t) {
                Toast.makeText(DetailTransactionProductActivity.this, "Tidak ditemukan atau jaringan tidak ada", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
    }

    public void loadSpinnerPet() {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<List<PetDAO>> call = apiService.getAllPet();

        call.enqueue(new Callback<List<PetDAO>>() {
            @Override
            public void onResponse(Call<List<PetDAO>> call, Response<List<PetDAO>> response) {
                for (int i = 0; i < response.body().size(); i++) {
                    idListPet.add(response.body().get(i).getId_hewan());
                    nameListPet.add(response.body().get(i).getNama());
                }

                ArrayAdapter<String> adapterPet = new ArrayAdapter<String>
                        (DetailTransactionProductActivity.this, android.R.layout.simple_list_item_1, nameListPet);
                spPet.setAdapter(adapterPet);

                spPet.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        idPet = nameListPet.indexOf(spPet.getText().toString());
                    }
                });

                spPet.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View view, MotionEvent motionEvent) {
                        spPet.showDropDown();
                        return false;
                    }
                });
            }

            @Override
            public void onFailure(Call<List<PetDAO>> call, Throwable t) {
                Toast.makeText(DetailTransactionProductActivity.this, "Kesalahan Jaringan", Toast.LENGTH_SHORT).show();

            }
        });
    }

    public void loadProduct(){
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<List<DetailTransactionProductDAO>> call = apiService.getByIDTPDetailTP(sId);

        call.enqueue(new Callback<List<DetailTransactionProductDAO>>() {
            @Override
            public void onResponse(Call<List<DetailTransactionProductDAO>> call, Response<List<DetailTransactionProductDAO>> response) {
                detailTPProductList.addAll(response.body());
                recyclerAdapter.notifyDataSetChanged();
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<List<DetailTransactionProductDAO>> call, Throwable t) {
                progressDialog.dismiss();
            }
        });
    }

}
