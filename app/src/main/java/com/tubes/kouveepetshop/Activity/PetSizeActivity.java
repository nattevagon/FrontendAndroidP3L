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
import com.tubes.kouveepetshop.Model.PetSizeDAO;
import com.tubes.kouveepetshop.R;
import com.tubes.kouveepetshop.RecyclerAdapter.PetSizeRecyclerAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PetSizeActivity extends AppCompatActivity {
    private ImageButton btnBack;
    private SearchView searchView;
    private FloatingActionButton btnAdd;
    private ShimmerFrameLayout mShimmerViewContainer;

    private List<PetSizeDAO> petsizeList;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefresh;
    private PetSizeRecyclerAdapter recyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_size);

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
                Intent i = new Intent(PetSizeActivity.this, AddPetSizeActivity.class);
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
    public void onPostResume() {
        super.onPostResume();
        mShimmerViewContainer.startShimmerAnimation();
        load();
    }

    public void load(){
        ApiInterface apiPetSize = ApiClient.getClient().create(ApiInterface.class);
        Call<List<PetSizeDAO>> call = apiPetSize.getAllPetSize();

        call.enqueue(new Callback<List<PetSizeDAO>>() {
            @Override
            public void onResponse(Call<List<PetSizeDAO>> call, Response<List<PetSizeDAO>> response) {
                generateDataList(response.body());
                mShimmerViewContainer.stopShimmerAnimation();
                mShimmerViewContainer.setVisibility(View.GONE);
                swipeRefresh.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<List<PetSizeDAO>> call, Throwable t) {
                Toast.makeText(PetSizeActivity.this, "Kesalahan Jaringan", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void generateDataList(List<PetSizeDAO> petsizeList) {
        recyclerView = findViewById(R.id.recyclerView);
        recyclerAdapter = new PetSizeRecyclerAdapter(this,petsizeList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(PetSizeActivity.this);
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
