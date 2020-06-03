package com.tubes.kouveepetshop.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SearchView;
import android.widget.Toast;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.tubes.kouveepetshop.API.ApiClient;
import com.tubes.kouveepetshop.API.ApiInterface;
import com.tubes.kouveepetshop.Model.CustomerDAO;
import com.tubes.kouveepetshop.R;
import com.tubes.kouveepetshop.RecyclerAdapter.CustomerRecyclerAdapter;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CustomerActivity extends AppCompatActivity {
    private ImageButton btnBack;
    private SearchView searchView;
    private FloatingActionButton btnAdd;
    private ShimmerFrameLayout mShimmerViewContainer;

    private List<CustomerDAO> customerList;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefresh;
    private CustomerRecyclerAdapter recyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer);

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

        btnAdd = findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(CustomerActivity.this, AddCustomerActivity.class);
                startActivity(i);

            }
        });

        searchView = findViewById(R.id.searchView);
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
        Call<List<CustomerDAO>> call = apiService.getAllCustomer();

        call.enqueue(new Callback<List<CustomerDAO>>() {
            @Override
            public void onResponse(Call<List<CustomerDAO>> call, Response<List<CustomerDAO>> response) {
                generateDataList(response.body());
                mShimmerViewContainer.stopShimmerAnimation();
                mShimmerViewContainer.setVisibility(View.GONE);
                swipeRefresh.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<List<CustomerDAO>> call, Throwable t) {
                Toast.makeText(CustomerActivity.this, "Kesalahan Jaringan", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void generateDataList(List<CustomerDAO> customerList) {
        recyclerView = findViewById(R.id.recyclerView);
        recyclerAdapter = new CustomerRecyclerAdapter(this,customerList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(CustomerActivity.this);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(recyclerAdapter);

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
