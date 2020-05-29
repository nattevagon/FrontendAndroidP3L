package com.tubes.kouveepetshop.Fragment;

import android.app.ProgressDialog;
import android.app.Service;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.tubes.kouveepetshop.API.ApiClient;
import com.tubes.kouveepetshop.API.ApiInterface;
import com.tubes.kouveepetshop.Activity.ProductCustomerActivity;
import com.tubes.kouveepetshop.Activity.ServiceCustomerActivity;

import com.tubes.kouveepetshop.Model.ProductDAO;
import com.tubes.kouveepetshop.Model.ServiceDAO;
import com.tubes.kouveepetshop.R;
import com.tubes.kouveepetshop.RecyclerAdapter.ProductHomeRecyclerAdapter;
import com.tubes.kouveepetshop.RecyclerAdapter.ServiceHomeRecyclerAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {
  private TextView twShowProduct, twShowService;
  private List<ProductDAO> productList;
  private List<ServiceDAO> serviceList;
  private RecyclerView productRecyclerView, serviceRecyclerView;
  private ProductHomeRecyclerAdapter productRecyclerAdapter;
  private ServiceHomeRecyclerAdapter serviceRecyclerAdapter;
  private ProgressDialog progressDialog;
  private ShimmerFrameLayout productShimmerViewContainer, serviceShimmerViewContainer;


  public HomeFragment() {
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    progressDialog = new ProgressDialog(getContext());

    productShimmerViewContainer = view.findViewById(R.id.product_shimmer_view_container);
    serviceShimmerViewContainer = view.findViewById(R.id.service_shimmer_view_container);
    productShimmerViewContainer.startShimmerAnimation();
    serviceShimmerViewContainer.startShimmerAnimation();

    twShowProduct = view.findViewById(R.id.twShowProduct);
    twShowService = view.findViewById(R.id.twShowService);

    twShowProduct.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Intent i = new Intent(getContext(), ProductCustomerActivity.class);
        startActivity(i);
      }
    });

    twShowService.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Intent i = new Intent(getContext(), ServiceCustomerActivity.class);
        startActivity(i);
      }
    });

    productList = new ArrayList<>();
    productRecyclerView = view.findViewById(R.id.productRecyclerView);
    productRecyclerView.setItemAnimator(new DefaultItemAnimator());
    productRecyclerAdapter = new ProductHomeRecyclerAdapter(getContext(),productList);
    productRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 1, GridLayoutManager.HORIZONTAL, false));
    productRecyclerView.setAdapter(productRecyclerAdapter);
    loadProduct();

    serviceList = new ArrayList<>();
    serviceRecyclerView = view.findViewById(R.id.serviceRecyclerView);
    loadService();
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    return inflater.inflate(R.layout.fragment_home, container, false);
  }

  public void loadProduct(){
    ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
    Call<List<ProductDAO>> call = apiService.getAllProduct();

    call.enqueue(new Callback<List<ProductDAO>>() {
      @Override
      public void onResponse(Call<List<ProductDAO>> call, Response<List<ProductDAO>> response) {
        productList.addAll(response.body());
        productRecyclerAdapter.notifyDataSetChanged();
        productShimmerViewContainer.stopShimmerAnimation();
        productShimmerViewContainer.setVisibility(View.GONE);
      }

      @Override
      public void onFailure(Call<List<ProductDAO>> call, Throwable t) {
//        Toast.makeText(getContext(), "Kesalahan Jaringan", Toast.LENGTH_SHORT).show();
        progressDialog.dismiss();
      }
    });
  }

  public void loadService(){
    ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
    Call<List<ServiceDAO>> call = apiService.getAllService();

    call.enqueue(new Callback<List<ServiceDAO>>() {
      @Override
      public void onResponse(Call<List<ServiceDAO>> call, Response<List<ServiceDAO>> response) {
        generateServiceList(response.body());
        serviceShimmerViewContainer.stopShimmerAnimation();
        serviceShimmerViewContainer.setVisibility(View.GONE);
      }

      @Override
      public void onFailure(Call<List<ServiceDAO>> call, Throwable t) {
//        Toast.makeText(getContext(), "Kesalahan Jaringan", Toast.LENGTH_SHORT).show();
        progressDialog.dismiss();
      }
    });
  }

  private void generateServiceList(List<ServiceDAO> serviceList) {
    serviceRecyclerAdapter = new ServiceHomeRecyclerAdapter(getContext(),serviceList);
    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
    serviceRecyclerView.setItemAnimator(new DefaultItemAnimator());
    serviceRecyclerView.setLayoutManager(layoutManager);
    serviceRecyclerView.setAdapter(serviceRecyclerAdapter);
  }
}
