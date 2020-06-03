package com.tubes.kouveepetshop.Activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.tubes.kouveepetshop.API.ApiClient;
import com.tubes.kouveepetshop.API.ApiInterface;
import com.tubes.kouveepetshop.Model.TransactionProductDAO;
import com.tubes.kouveepetshop.R;
import com.tubes.kouveepetshop.RecyclerAdapter.HistoryTransactionProductRecyclerAdapter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HistoryTransactionProductActivity extends AppCompatActivity {
    private ImageButton btnBack;
    private SearchView searchView;
    private ShimmerFrameLayout mShimmerViewContainer;

    private List<TransactionProductDAO> transactionProductList;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefresh;
    private HistoryTransactionProductRecyclerAdapter recyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_transaction_product);

        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

        mShimmerViewContainer = findViewById(R.id.shimmer_view_container);
        mShimmerViewContainer.startShimmerAnimation();

        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


        searchView = findViewById(R.id.searchView);
        recyclerView = findViewById(R.id.restoreTPRecyclerView);
        load();

        swipeRefresh = findViewById(R.id.swipeRefresh);
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mShimmerViewContainer.startShimmerAnimation();
                load();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        mShimmerViewContainer.startShimmerAnimation();
        load();
    }

    public void load(){
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<List<TransactionProductDAO>> call = apiService.getAllCanceledTransactionProduct();

        call.enqueue(new Callback<List<TransactionProductDAO>>() {
            @Override
            public void onResponse(Call<List<TransactionProductDAO>> call, Response<List<TransactionProductDAO>> response) {
                for(int i=0;i<response.body().size();i++) {
                    String id = response.body().get(i).getId_tp();

                    if(id.equalsIgnoreCase("false"))
                    {
                        Toast.makeText(HistoryTransactionProductActivity.this, "Transaksi Kosong", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        generateDataList(response.body());
                    }
                }
                mShimmerViewContainer.stopShimmerAnimation();
                mShimmerViewContainer.setVisibility(View.GONE);
                swipeRefresh.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<List<TransactionProductDAO>> call, Throwable t) {
                Toast.makeText(HistoryTransactionProductActivity.this, "Kesalahan Jaringan", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void generateDataList(List<TransactionProductDAO> transactionProductList) {
        recyclerAdapter = new HistoryTransactionProductRecyclerAdapter(this, transactionProductList, this);
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

    public void RestoreItemList(View v, final String idDetail, String code) {

        AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());

        builder.setTitle("Pulihkan transaksi ?")
                .setMessage("Anda yakin untuk memulihkan transaksi "+code+" ?, jika dipulihkan maka akan tertampil pada daftar transaksi produk.")
                .setIcon(R.drawable.ic_restore)
                .setCancelable(false)
                .setPositiveButton("PULIHKAN",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                mShimmerViewContainer.startShimmerAnimation();
                                Restore(idDetail);
                            }
                        })
                .setNegativeButton("BATAL", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });

        builder.show();

    }

    public void Restore(final String id) {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<TransactionProductDAO> call = apiService.restoreTransactionProduct(id);

        call.enqueue(new Callback<TransactionProductDAO>() {
            @Override
            public void onResponse(Call<TransactionProductDAO> call, Response<TransactionProductDAO> response) {
                onPostResume();
            }

            @Override
            public void onFailure(Call<TransactionProductDAO> call, Throwable t) {
                mShimmerViewContainer.stopShimmerAnimation();
                mShimmerViewContainer.setVisibility(View.GONE);
            }
        });
    }
}
