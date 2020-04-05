package com.tubes.kouveepetshop.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.tubes.kouveepetshop.API.ApiClient;
import com.tubes.kouveepetshop.API.ApiInterface;
import com.tubes.kouveepetshop.Fragment.AddProductTransactionFragment;
import com.tubes.kouveepetshop.Model.TransactionProductDAO;
import com.tubes.kouveepetshop.R;
import com.tubes.kouveepetshop.RecyclerAdapter.TransactionProductRecyclerAdapter;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TransactionProductActivity extends AppCompatActivity {
    private ImageButton btnBack;
    private SearchView searchView;
    private FloatingActionButton btnAdd;
    private ProgressDialog progressDialog;

    private List<TransactionProductDAO> transactionProductList;
    private RecyclerView recyclerView;
    private TransactionProductRecyclerAdapter recyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_product);

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

        btnAdd = findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager manager = TransactionProductActivity.this.getSupportFragmentManager();
                AddProductTransactionFragment bottomSheet = new AddProductTransactionFragment();
                bottomSheet.show(manager, "bottomSheet");
            }
        });

        searchView = findViewById(R.id.searchView);

        load();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        progressDialog.show();
        load();
    }

    public void load(){
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<List<TransactionProductDAO>> call = apiService.getAllTransactionProduct();

        call.enqueue(new Callback<List<TransactionProductDAO>>() {
            @Override
            public void onResponse(Call<List<TransactionProductDAO>> call, Response<List<TransactionProductDAO>> response) {
                generateDataList(response.body());
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<List<TransactionProductDAO>> call, Throwable t) {
                Toast.makeText(TransactionProductActivity.this, "Kesalahan Jaringan", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();

            }
        });
    }

    private void generateDataList(List<TransactionProductDAO> transactionProductList) {
        recyclerView = findViewById(R.id.tpRecyclerView);
        recyclerAdapter = new TransactionProductRecyclerAdapter(this, transactionProductList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(recyclerAdapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String queryString) {
                recyclerAdapter.getFilter().filter(queryString);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String queryString) {
                recyclerAdapter.getFilter().filter(queryString);
                return false;
            }
        });
    }
}
