package com.tubes.kouveepetshop.Fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tubes.kouveepetshop.API.ApiClient;
import com.tubes.kouveepetshop.API.ApiInterface;
import com.tubes.kouveepetshop.Activity.DetailTransactionServiceActivity;
import com.tubes.kouveepetshop.Activity.EditPetActivity;
import com.tubes.kouveepetshop.Activity.TransactionProductActivity;
import com.tubes.kouveepetshop.Model.DetailTransactionServiceDAO;
import com.tubes.kouveepetshop.Model.PetSizeDAO;
import com.tubes.kouveepetshop.Model.ServiceDAO;
import com.tubes.kouveepetshop.R;
import com.tubes.kouveepetshop.RecyclerAdapter.AddDetailTSRecyclerAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddServiceDetailTransactionServiceFragment extends DialogFragment {
  private ImageButton btnClose;
  private String sIdTS, sPet, sIdTSPetSize, sPetSize, sAmount, sPrice, sTotal, sStock;
  private int idPetSize;
  private SearchView searchView;
  private AutoCompleteTextView spPetSize;
  private List<ServiceDAO> serviceList;
  private RecyclerView recyclerView;
  private AddDetailTSRecyclerAdapter recyclerAdapter;
  private ProgressDialog progressDialog;
  List<String> idListPetSize = new ArrayList<String>();
  List<String> nameListPetSize = new ArrayList<String>();

  public AddServiceDetailTransactionServiceFragment() {
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
    View v = inflater.inflate(R.layout.fragment_add_service_detail_transaction_service, container, false);

    progressDialog = new ProgressDialog(getContext());
    searchView = v.findViewById(R.id.searchView);
    spPetSize = v.findViewById(R.id.spPetSize);
    btnClose = v.findViewById(R.id.btnClose);

    btnClose.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        dismiss();
      }
    });

    serviceList = new ArrayList<>();
    recyclerView = v.findViewById(R.id.serviceRecyclerView);
    recyclerView.setItemAnimator(new DefaultItemAnimator());

    sIdTS = getArguments().getString("id", "");
    sPet = getArguments().getString("pet", "");
    sIdTSPetSize = getArguments().getString("idpetsize", "");
    sPetSize = getArguments().getString("petsize", "");
    spPetSize.setText(sPetSize);

    Toast.makeText(getContext(), "Id Transaksi Layanan: "+sIdTS, Toast.LENGTH_SHORT).show();

    serviceList.removeAll(serviceList);

    if(sPet.equalsIgnoreCase("Guest"))
    {
        spPetSize.setEnabled(true);
        loadAll(sIdTS);
    }else
    {
        spPetSize.setEnabled(false);
        load(sIdTS, sIdTSPetSize);
    }

    if(spPetSize.getText().toString().equalsIgnoreCase(null))
    {
      loadAll(sIdTS);
    }

    loadSpinnerPetSize();

    return v;
  }

  public void loadAll(String idTS){
      ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
      Call<List<ServiceDAO>> call = apiService.getByIdTS(idTS);

      call.enqueue(new Callback<List<ServiceDAO>>() {
          @Override
          public void onResponse(Call<List<ServiceDAO>> call, Response<List<ServiceDAO>> response) {
              generateDataList(response.body());
              recyclerAdapter.notifyDataSetChanged();
          }

          @Override
          public void onFailure(Call<List<ServiceDAO>> call, Throwable t) {
              Toast.makeText(getContext(), "Kesalahan Jaringan", Toast.LENGTH_SHORT).show();
          }
      });
  }

  public void load(String idTS, String idPetSize){
    ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
    Call<List<ServiceDAO>> call = apiService.getByIdPetSize(idTS, idPetSize);

    call.enqueue(new Callback<List<ServiceDAO>>() {
      @Override
      public void onResponse(Call<List<ServiceDAO>> call, Response<List<ServiceDAO>> response) {
        for(int i=0;i<response.body().size();i++) {
          String id = response.body().get(i).getId_layanan();

          if(id.equalsIgnoreCase("false"))
          {
            Toast.makeText(getContext(), "Layanan Kosong", Toast.LENGTH_SHORT).show();
          }
          else
          {
            generateDataList(response.body());
            recyclerAdapter.notifyDataSetChanged();
          }
        }
      }

      @Override
      public void onFailure(Call<List<ServiceDAO>> call, Throwable t) {
        Toast.makeText(getContext(), "Kesalahan Jaringan", Toast.LENGTH_SHORT).show();
      }
    });
  }

  private void generateDataList(List<ServiceDAO> serviceList) {
    recyclerAdapter = new AddDetailTSRecyclerAdapter(getContext(),serviceList, AddServiceDetailTransactionServiceFragment.this);
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

  public void loadSpinnerPetSize(){
    ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
    Call<List<PetSizeDAO>> call = apiService.getAllPetSize();

    call.enqueue(new Callback<List<PetSizeDAO>>() {
      @Override
      public void onResponse(Call<List<PetSizeDAO>> call, Response<List<PetSizeDAO>> response) {
        for (int i = 0; i < response.body().size(); i++){
          idListPetSize.add(response.body().get(i).getId_ukuran_hewan());
          nameListPetSize.add(response.body().get(i).getNama());
        }

        ArrayAdapter<String> adapterPetSize = new ArrayAdapter<String>
                (getContext(), android.R.layout.simple_list_item_1, nameListPetSize);
        spPetSize.setAdapter(adapterPetSize);

        spPetSize.setOnTouchListener(new View.OnTouchListener() {
          @Override
          public boolean onTouch(View view, MotionEvent motionEvent) {
            spPetSize.showDropDown();
            return false;
          }
        });

        idPetSize = nameListPetSize.indexOf(sPetSize);
        spPetSize.setOnItemClickListener(new AdapterView.OnItemClickListener() {
          @Override
          public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            serviceList.clear();
            idPetSize = nameListPetSize.indexOf(spPetSize.getText().toString());
            load(sIdTS, idListPetSize.get(idPetSize));
          }
        });
      }

      @Override
      public void onFailure(Call<List<PetSizeDAO>> call, Throwable t) {
        Toast.makeText(getContext(), "Kesalahan Jaringan", Toast.LENGTH_SHORT).show();

      }
    });
  }

  public void Add(String id_service, String total)
  {
    ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
    Call<DetailTransactionServiceDAO> add = apiService.addDetailTS(sIdTS, id_service,"1",total);

    add.enqueue(new Callback<DetailTransactionServiceDAO>() {
      @Override
      public void onResponse(Call<DetailTransactionServiceDAO> call, Response<DetailTransactionServiceDAO> response) {
        dismiss();
        DetailTransactionServiceActivity detailTS = (DetailTransactionServiceActivity) getActivity();
        detailTS.onBack();
      }

      @Override
      public void onFailure(Call<DetailTransactionServiceDAO> call, Throwable t) {
        Toast.makeText(getContext(), "Gagal menambahkan produk", Toast.LENGTH_SHORT).show();
        dismiss();
        DetailTransactionServiceActivity detailTS = (DetailTransactionServiceActivity) getActivity();
        detailTS.onBack();
      }
    });
  }

  @Override
  public void dismiss() {
    super.dismiss();
  }

  public String getId_tl() {
    return sIdTS;
  }

}
