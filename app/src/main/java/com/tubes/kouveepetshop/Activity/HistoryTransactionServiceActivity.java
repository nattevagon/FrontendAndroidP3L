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
import com.tubes.kouveepetshop.Model.TransactionServiceDAO;
import com.tubes.kouveepetshop.R;
import com.tubes.kouveepetshop.RecyclerAdapter.HistoryTransactionServiceRecyclerAdapter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HistoryTransactionServiceActivity extends AppCompatActivity {
    private ImageButton btnBack;
    private SearchView searchView;
    private ShimmerFrameLayout mShimmerViewContainer;

    private List<TransactionServiceDAO> transactionServiceList;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefresh;
    private HistoryTransactionServiceRecyclerAdapter recyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_transaction_service);

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
        recyclerView = findViewById(R.id.restoreTSRecyclerView);
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
        Call<List<TransactionServiceDAO>> call = apiService.getAllCanceledTransactionService();

        call.enqueue(new Callback<List<TransactionServiceDAO>>() {
            @Override
            public void onResponse(Call<List<TransactionServiceDAO>> call, Response<List<TransactionServiceDAO>> response) {
                for(int i=0;i<response.body().size();i++) {
                    String id = response.body().get(i).getId_tl();

                    if(id.equalsIgnoreCase("false"))
                    {
                        Toast.makeText(HistoryTransactionServiceActivity.this, "Transaksi Kosong", Toast.LENGTH_SHORT).show();
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
            public void onFailure(Call<List<TransactionServiceDAO>> call, Throwable t) {
                Toast.makeText(HistoryTransactionServiceActivity.this, "Kesalahan Jaringan", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void generateDataList(List<TransactionServiceDAO> transactionServiceList) {
        recyclerAdapter = new HistoryTransactionServiceRecyclerAdapter(this, transactionServiceList, this);
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
                .setMessage("Anda yakin untuk memulihkan transaksi "+code+" ?, jika dipulihkan maka akan tertampil pada daftar transaksi layanan.")
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
        Call<TransactionServiceDAO> call = apiService.restoreTransactionService(id);

        call.enqueue(new Callback<TransactionServiceDAO>() {
            @Override
            public void onResponse(Call<TransactionServiceDAO> call, Response<TransactionServiceDAO> response) {
                onPostResume();
            }

            @Override
            public void onFailure(Call<TransactionServiceDAO> call, Throwable t) {
                mShimmerViewContainer.stopShimmerAnimation();
                mShimmerViewContainer.setVisibility(View.GONE);
            }
        });
    }
}
