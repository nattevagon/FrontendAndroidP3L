package com.tubes.kouveepetshop.Fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tubes.kouveepetshop.API.ApiClient;
import com.tubes.kouveepetshop.API.ApiInterface;
import com.tubes.kouveepetshop.Activity.DetailProcurementActivity;
import com.tubes.kouveepetshop.Activity.DetailTransactionProductActivity;
import com.tubes.kouveepetshop.Model.DetailProcurementDAO;
import com.tubes.kouveepetshop.Model.ProductDAO;
import com.tubes.kouveepetshop.R;
import com.tubes.kouveepetshop.RecyclerAdapter.AddDetailPRecyclerAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddProductDetailProcurementFragment extends DialogFragment {
  private ImageButton btnClose;
  private String sId;
  private SearchView searchView;
  private List<ProductDAO> productList;
  private RecyclerView recyclerView;
  private AddDetailPRecyclerAdapter recyclerAdapter;
  private ProgressDialog progressDialog;

  public AddProductDetailProcurementFragment() {
  }



  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Override
  public void onStart() {
    super.onStart();
    getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    View v = inflater.inflate(R.layout.fragment_add_product_detail_procurement, container, false);

    progressDialog = new ProgressDialog(getContext());
    searchView = v.findViewById(R.id.searchView);
    btnClose = v.findViewById(R.id.btnClose);

    btnClose.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        dismiss();
      }
    });

    productList = new ArrayList<>();
    recyclerView = v.findViewById(R.id.productRecyclerView);
    recyclerView.setItemAnimator(new DefaultItemAnimator());

    productList.removeAll(productList);
    load();

    sId = getArguments().getString("id", "");

    return v;
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

  private void generateDataList(List<ProductDAO> productList) {
    recyclerAdapter = new AddDetailPRecyclerAdapter(getContext(),productList, AddProductDetailProcurementFragment.this);
    recyclerView.setLayoutManager(new GridLayoutManager(getContext(),1));
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

  public String getsId() {
    return sId;
  }
}
