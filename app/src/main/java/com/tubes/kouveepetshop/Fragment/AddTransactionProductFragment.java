package com.tubes.kouveepetshop.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.squareup.picasso.Picasso;
import com.tubes.kouveepetshop.API.ApiClient;
import com.tubes.kouveepetshop.API.ApiInterface;
import com.tubes.kouveepetshop.Activity.AddPetActivity;
import com.tubes.kouveepetshop.Activity.DetailPetActivity;
import com.tubes.kouveepetshop.Activity.DetailSupplierActivity;
import com.tubes.kouveepetshop.Activity.DetailTransactionProductActivity;
import com.tubes.kouveepetshop.Activity.TransactionProductActivity;
import com.tubes.kouveepetshop.Model.CustomerDAO;
import com.tubes.kouveepetshop.Model.PetDAO;
import com.tubes.kouveepetshop.Model.ProductDAO;
import com.tubes.kouveepetshop.Model.TransactionProductDAO;
import com.tubes.kouveepetshop.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddTransactionProductFragment extends BottomSheetDialogFragment {
    private TextView twCustomerService, twCode, twDate;
    private Button btnAdd;
    private AutoCompleteTextView spPet;
    private ImageView imgClose;
    private String sCustomerService, sCode, sDate, sYear, sMonth, sDay, sCodeTP;
    private int idPet, tpLength = 0;
    List<String> idListPet = new ArrayList<String>();
    List<String> nameListPet = new ArrayList<String>();


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_add_transaction_product, container, false);

        twCustomerService = v.findViewById(R.id.twCS);
        twDate = v.findViewById(R.id.twDate);
        spPet = v.findViewById(R.id.spPet);
        btnAdd = v.findViewById(R.id.btnAdd);
        imgClose = v.findViewById(R.id.imgClose);

        sCustomerService = "Ruben";

        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        sDate = dateFormat.format(date);
        System.out.println(sDate);

        twDate.setText(dateFormat.format(date));
        sYear = sDate.substring(2, 4);
        sMonth = sDate.substring(5, 7);
        sDay = sDate.substring(8, 10);

        sCode = "PR-"+sYear+sMonth+sDay;
        getCode(sCode);

        imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        loadSpinnerPet();

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Add();
            }
        });

        return v;
    }

    public void loadSpinnerPet() {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<List<PetDAO>> call = apiService.getAllPet();

        call.enqueue(new Callback<List<PetDAO>>() {
            @Override
            public void onResponse(Call<List<PetDAO>> call, Response<List<PetDAO>> response) {
                for (int i = 0; i < response.body().size(); i++) {
                    idListPet.add(response.body().get(i).getID_HEWAN());
                    nameListPet.add(response.body().get(i).getNAMA());
                }

                ArrayAdapter<String> adapterPet = new ArrayAdapter<String>
                        (getContext(), android.R.layout.simple_list_item_1, nameListPet);
                spPet.setAdapter(adapterPet);

                spPet.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        idPet = nameListPet.indexOf(spPet.getText().toString());
                        Toast.makeText(getContext(), "POS" + idPet, Toast.LENGTH_SHORT).show();
                    }
                });

                spPet.showDropDown();

                Toast.makeText(getContext(), "IDPet" + idListPet.get(idPet), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<List<PetDAO>> call, Throwable t) {
                Toast.makeText(getContext(), "Kesalahan Jaringan", Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void getCode(String code){
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<List<TransactionProductDAO>> get = apiService.getCodeLengthTransactionProduct(code);

        get.enqueue(new Callback<List<TransactionProductDAO>>() {
            @Override
            public void onResponse(Call<List<TransactionProductDAO>> call, Response<List<TransactionProductDAO>> response) {
                tpLength = response.body().size();
            }

            @Override
            public void onFailure(Call<List<TransactionProductDAO>> call, Throwable t) {
                Toast.makeText(getContext(), "Tidak ditemukan atau jaringan tidak ada", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void Add() {
        sCodeTP = sCode+"-"+String.format("%02d", tpLength+1);
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<TransactionProductDAO> add = apiService.addTransactionProduct(idListPet.get(idPet), "1", sCodeTP, sDate, "0", "0", "Penjualan", sCustomerService);

        add.enqueue(new Callback<TransactionProductDAO>() {
            @Override
            public void onResponse(Call<TransactionProductDAO> call, Response<TransactionProductDAO> response) {
                Toast.makeText(getContext(), "Success", Toast.LENGTH_SHORT).show();
                dismiss();

                Intent i = new Intent(getContext(), DetailTransactionProductActivity.class);
                i.putExtra("kode",sCodeTP);
                getContext().startActivity(i);
            }

            @Override
            public void onFailure(Call<TransactionProductDAO> call, Throwable t) {
                Toast.makeText(getContext(), "Fail", Toast.LENGTH_SHORT).show();
                dismiss();
            }
        });
    }
}