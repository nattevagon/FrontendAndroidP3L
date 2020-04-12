package com.tubes.kouveepetshop.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.SearchView;
import android.widget.Toast;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.tubes.kouveepetshop.API.ApiClient;
import com.tubes.kouveepetshop.API.ApiInterface;
import com.tubes.kouveepetshop.Model.ProductDAO;
import com.tubes.kouveepetshop.R;
import com.tubes.kouveepetshop.RecyclerAdapter.ProductCustomerRecyclerAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductCustomerActivity extends AppCompatActivity {
    private ImageButton btnBack, btnSort;
    private SearchView searchView;
    private ShimmerFrameLayout mShimmerViewContainer;

    private List<ProductDAO> productList;
    private RecyclerView recyclerView;
    private ProductCustomerRecyclerAdapter recyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_customer);

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
        btnSort = findViewById(R.id.btnSort);
        
        recyclerView = findViewById(R.id.productRecyclerView);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        productList = new ArrayList<ProductDAO>();
        productList.removeAll(productList);
        load();

        btnSort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Sort(view);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    public void load(){
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<List<ProductDAO>> call = apiService.getAllProduct();

        call.enqueue(new Callback<List<ProductDAO>>() {
            @Override
            public void onResponse(Call<List<ProductDAO>> call, Response<List<ProductDAO>> response) {
                generateDataList(response.body());
                mShimmerViewContainer.stopShimmerAnimation();
                mShimmerViewContainer.setVisibility(View.GONE);            }

            @Override
            public void onFailure(Call<List<ProductDAO>> call, Throwable t) {
                Toast.makeText(ProductCustomerActivity.this, "Kesalahan Jaringan", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void loadByPrice(){
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<List<ProductDAO>> call = apiService.getSortPrice();

        call.enqueue(new Callback<List<ProductDAO>>() {
            @Override
            public void onResponse(Call<List<ProductDAO>> call, Response<List<ProductDAO>> response) {
                generateDataList(response.body());
            }

            @Override
            public void onFailure(Call<List<ProductDAO>> call, Throwable t) {
                Toast.makeText(ProductCustomerActivity.this, "Kesalahan Jaringan", Toast.LENGTH_SHORT).show();

            }
        });
    }

    public void loadByStock(){
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<List<ProductDAO>> call = apiService.getSortStock();

        call.enqueue(new Callback<List<ProductDAO>>() {
            @Override
            public void onResponse(Call<List<ProductDAO>> call, Response<List<ProductDAO>> response) {
                generateDataList(response.body());
            }

            @Override
            public void onFailure(Call<List<ProductDAO>> call, Throwable t) {
                Toast.makeText(ProductCustomerActivity.this, "Kesalahan Jaringan", Toast.LENGTH_SHORT).show();

            }
        });
    }


    private void generateDataList(List<ProductDAO> productList) {
        recyclerAdapter = new ProductCustomerRecyclerAdapter(ProductCustomerActivity.this,productList);
        recyclerView.setLayoutManager(new GridLayoutManager(ProductCustomerActivity.this,2));
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

    public void Sort(View v){
        PopupMenu popup = new PopupMenu(ProductCustomerActivity.this, v);
        popup.getMenuInflater().inflate(R.menu.product_sort_menu, popup.getMenu());
        popup.show();
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item2) {

                switch (item2.getItemId()) {
                    case R.id.sort_by_stock:
                        productList.removeAll(productList);
                        loadByStock();
                        break;
                    case R.id.sort_by_price:
                        productList.removeAll(productList);
                        loadByPrice();
                        break;

                    default:
                        break;
                }
                return true;
            }
        });

    }

}
