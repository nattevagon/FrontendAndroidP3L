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
import com.tubes.kouveepetshop.Activity.DetailTransactionProductActivity;
import com.tubes.kouveepetshop.Model.DetailTransactionProductDAO;
import com.tubes.kouveepetshop.Model.ProductDAO;
import com.tubes.kouveepetshop.R;
import com.tubes.kouveepetshop.RecyclerAdapter.AddDetailTPRecyclerAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddDetailProductTransactionFragment extends DialogFragment {
  private ImageButton btnBack;
  private String sId, sIdDTP, sAmount, sPrice, sTotal;
  private SearchView searchView;
  private List<ProductDAO> productList;
  private RecyclerView recyclerView;
  private AddDetailTPRecyclerAdapter recyclerAdapter;
  private ProgressDialog progressDialog;

  public AddDetailProductTransactionFragment() {
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
    View v = inflater.inflate(R.layout.fragment_add_detail_product_transaction, container, false);

    progressDialog = new ProgressDialog(getContext());
    searchView = v.findViewById(R.id.searchView);

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
    recyclerAdapter = new AddDetailTPRecyclerAdapter(getContext(),productList, AddDetailProductTransactionFragment.this);
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

  public void Add(final String id_product, final String total)
  {
    ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
    Call<List<DetailTransactionProductDAO>> call = apiService.getByProdukDTP(sId, id_product);

    call.enqueue(new Callback<List<DetailTransactionProductDAO>>() {
      @Override
      public void onResponse(Call<List<DetailTransactionProductDAO>> call, Response<List<DetailTransactionProductDAO>> response) {
        Toast.makeText(getContext(), "Udah ada : "+response.body().size(), Toast.LENGTH_SHORT).show();
        for(int i=0;i<response.body().size();i++)
        {
          sIdDTP = response.body().get(i).getId_detail_tp();
          sAmount = response.body().get(i).getJumlah();
          sPrice = response.body().get(i).getHarga();
          sTotal = response.body().get(i).getTotal();
        }

        int amount = Integer.parseInt(sAmount)+1;
        int total = Integer.parseInt(sTotal)+Integer.parseInt(sPrice);

        UpdateProduct(sIdDTP, sId, id_product, Integer.toString(amount), Integer.toString(total));

      }

      @Override
      public void onFailure(Call<List<DetailTransactionProductDAO>> call, Throwable t) {
        AddProduct(id_product, total);
      }
    });
  }

  private void AddProduct(String id_product, String total)
  {
    ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
    Call<DetailTransactionProductDAO> add = apiService.addDetailTP(sId, id_product,"1",total);

    add.enqueue(new Callback<DetailTransactionProductDAO>() {
      @Override
      public void onResponse(Call<DetailTransactionProductDAO> call, Response<DetailTransactionProductDAO> response) {
        dismiss();
        DetailTransactionProductActivity detailTP = (DetailTransactionProductActivity) getActivity();
        detailTP.onBack();
      }

      @Override
      public void onFailure(Call<DetailTransactionProductDAO> call, Throwable t) {
        Toast.makeText(getContext(), "Fail", Toast.LENGTH_SHORT).show();
        dismiss();
        DetailTransactionProductActivity detailTP = (DetailTransactionProductActivity) getActivity();
        detailTP.onBack();
      }
    });
  }

  private void UpdateProduct(String id_detail_tp, String id_tp, String id_product, String amount, String total)
  {
    ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
    Call<DetailTransactionProductDAO> update = apiService.updateDetailTP(id_detail_tp, id_tp, id_product,amount,total);

    update.enqueue(new Callback<DetailTransactionProductDAO>() {
      @Override
      public void onResponse(Call<DetailTransactionProductDAO> call, Response<DetailTransactionProductDAO> response) {
        dismiss();
        DetailTransactionProductActivity detailTP = (DetailTransactionProductActivity) getActivity();
        detailTP.onBack();
      }

      @Override
      public void onFailure(Call<DetailTransactionProductDAO> call, Throwable t) {
        Toast.makeText(getContext(), "Fail", Toast.LENGTH_SHORT).show();
        dismiss();
        DetailTransactionProductActivity detailTP = (DetailTransactionProductActivity) getActivity();
        detailTP.onBack();
      }
    });
  }

}
