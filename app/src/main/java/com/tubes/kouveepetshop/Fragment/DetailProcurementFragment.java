package com.tubes.kouveepetshop.Fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tubes.kouveepetshop.API.ApiClient;
import com.tubes.kouveepetshop.API.ApiInterface;
import com.tubes.kouveepetshop.Model.DetailProcurementDAO;
import com.tubes.kouveepetshop.R;
import com.tubes.kouveepetshop.RecyclerAdapter.HistoryDetailProcurementRecyclerAdapter;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailProcurementFragment extends DialogFragment {
  private ImageButton btnClose;
  private TextView twCode, twSupplier, twTotal;
  private String sId, sCode, sSupplier, sTotal;
  private List<DetailProcurementDAO> productList;
  private RecyclerView recyclerView;
  private HistoryDetailProcurementRecyclerAdapter recyclerAdapter;
  private ProgressDialog progressDialog;

  private Locale localeID = new Locale("in", "ID");
  private NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);

  public DetailProcurementFragment(String id_tp) {
    this.sId = id_tp;
  }


  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Override
  public void onStart() {
    super.onStart();
    getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    View v = inflater.inflate(R.layout.fragment_detail_procurement, container, false);

    progressDialog = new ProgressDialog(getContext());
    progressDialog.show();

    twCode = v.findViewById(R.id.twCode);
    twSupplier = v.findViewById(R.id.twSupplier);
    twTotal = v.findViewById(R.id.twTotal);
    btnClose = v.findViewById(R.id.btnClose);

    btnClose.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        dismiss();
      }
    });

    productList = new ArrayList<>();
    recyclerView = v.findViewById(R.id.restoreTSRecyclerView);
    recyclerView.setItemAnimator(new DefaultItemAnimator());

    productList.removeAll(productList);
    load();

    sCode = getArguments().getString("code", "");
    sSupplier = getArguments().getString("supplier", "");
    sTotal = getArguments().getString("total", "");
    twCode.setText(sCode);
    twSupplier.setText(sSupplier);
    twTotal.setText(formatRupiah.format((double)Double.parseDouble(sTotal)));

    return v;
  }

  public void load(){
    ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
    Call<List<DetailProcurementDAO>> call = apiService.getByIdPDetailP(sId);

    call.enqueue(new Callback<List<DetailProcurementDAO>>() {
      @Override
      public void onResponse(Call<List<DetailProcurementDAO>> call, Response<List<DetailProcurementDAO>> response) {
        generateDataList(response.body());
      }

      @Override
      public void onFailure(Call<List<DetailProcurementDAO>> call, Throwable t) {
        progressDialog.dismiss();
      }
    });
  }

  private void generateDataList(List<DetailProcurementDAO> productList) {
    recyclerAdapter = new HistoryDetailProcurementRecyclerAdapter(getContext(),productList);
    recyclerView.setLayoutManager(new GridLayoutManager(getContext(),1));
    recyclerView.setAdapter(recyclerAdapter);
    progressDialog.dismiss();
  }
}
