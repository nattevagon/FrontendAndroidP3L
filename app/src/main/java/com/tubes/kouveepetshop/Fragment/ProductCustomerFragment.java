package com.tubes.kouveepetshop.Fragment;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SearchView;
import android.widget.Toast;

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

public class ProductCustomerFragment extends Fragment {
  private ImageButton btnBack;
  private Button btnSortPrice, btnSortStock;
  private SearchView searchView;
  private ProgressDialog progressDialog;

  private List<ProductDAO> productList;
  private RecyclerView recyclerView;
  private ProductCustomerRecyclerAdapter recyclerAdapter;

  public ProductCustomerFragment() {
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    progressDialog = new ProgressDialog(getContext());
    searchView = view.findViewById(R.id.searchView);
    btnSortPrice = view.findViewById(R.id.btnSortPrice);
    btnSortStock = view.findViewById(R.id.btnSortStock);

    productList = new ArrayList<>();
    recyclerView = view.findViewById(R.id.productRecyclerView);
    recyclerView.setItemAnimator(new DefaultItemAnimator());

    productList.removeAll(productList);
    load();

    btnSortPrice.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
          productList.removeAll(productList);
          loadByPrice();
      }
    });

    btnSortStock.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
         productList.removeAll(productList);
         loadByStock();
      }
    });
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    return inflater.inflate(R.layout.fragment_product_customer, container, false);
  }

  public void load(){
    ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
    Call<List<ProductDAO>> call = apiService.getAllProduct();

    call.enqueue(new Callback<List<ProductDAO>>() {
      @Override
      public void onResponse(Call<List<ProductDAO>> call, Response<List<ProductDAO>> response) {
        generateDataList(response.body());
      }

      @Override
      public void onFailure(Call<List<ProductDAO>> call, Throwable t) {
        Toast.makeText(getContext(), "Kesalahan Jaringan", Toast.LENGTH_SHORT).show();

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
        Toast.makeText(getContext(), "Kesalahan Jaringan", Toast.LENGTH_SHORT).show();

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
        Toast.makeText(getContext(), "Kesalahan Jaringan", Toast.LENGTH_SHORT).show();

      }
    });
  }


  private void generateDataList(List<ProductDAO> productList) {
    recyclerAdapter = new ProductCustomerRecyclerAdapter(getContext(),productList);
    recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
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
