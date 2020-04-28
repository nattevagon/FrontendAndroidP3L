package com.tubes.kouveepetshop.Fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.tubes.kouveepetshop.API.ApiClient;
import com.tubes.kouveepetshop.API.ApiInterface;
import com.tubes.kouveepetshop.Activity.DetailTransactionServiceActivity;
import com.tubes.kouveepetshop.Java.SessionManager;
import com.tubes.kouveepetshop.Model.PetDAO;
import com.tubes.kouveepetshop.Model.TransactionServiceDAO;
import com.tubes.kouveepetshop.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddTransactionServiceFragment extends DialogFragment {
    private TextView twCustomerService, twCode, twDate;
    private Button btnAdd;
    private CheckBox cbGuest;
    private AutoCompleteTextView spPet;
    private ImageView imgClose;
    private boolean statusGuest;
    private String sIdCS, sCustomerService, sCode, sDate, sYear, sMonth, sDay, sCodeTS, sIdPet;
    private int idPet, tpLength = 0;
    private SessionManager sessionManager;
    List<String> idListPet = new ArrayList<String>();
    List<String> nameListPet = new ArrayList<String>();

    private ProgressDialog progressDialog;

    @Override
    public void onStart() {
        super.onStart();
        getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_add_transaction_service, container, false);

        progressDialog = new ProgressDialog(getContext());
        progressDialog.show();

        twCode = v.findViewById(R.id.twCode);
        twCustomerService = v.findViewById(R.id.twCS);
        twDate = v.findViewById(R.id.twDate);
        spPet = v.findViewById(R.id.spPet);
        btnAdd = v.findViewById(R.id.btnAdd);
        cbGuest = v.findViewById(R.id.cbGuest);
        imgClose = v.findViewById(R.id.imgClose);

        sessionManager = new SessionManager(getContext());
        HashMap<String, String> user = sessionManager.getUserDetail();
        sIdCS = user.get(sessionManager.ID);
        sCustomerService = user.get(sessionManager.NAME);

        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        sDate = dateFormat.format(date);
        System.out.println(sDate);

        twDate.setText(dateFormat.format(date));
        twCustomerService.setText(sCustomerService);

        sYear = sDate.substring(2, 4);
        sMonth = sDate.substring(5, 7);
        sDay = sDate.substring(8, 10);

        sCode = "LY-"+sYear+sMonth+sDay;
        getCode(sCode);

        imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        loadSpinnerPet();

        statusGuest = false;
        cbGuest.setChecked(false);
        cbGuest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean checked = ((CheckBox) view).isChecked();
                // Check which checkbox was clicked
                if (checked){
                    spPet.setEnabled(false);
                    statusGuest = true;

                }
                else{
                    spPet.setEnabled(true);
                    statusGuest = false;
                }
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(statusGuest == false)
                {
                    if(spPet.getText().toString().equals(nameListPet.get(idPet)))
                    {
                        Add();
                    }
                    else
                    {
                        spPet.setError("Hewan tidak ada!");
                    }
                }
                else
                {
                    Add();
                }
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
                    idListPet.add(response.body().get(i).getId_hewan());
                    nameListPet.add(response.body().get(i).getNama()+" / "+response.body().get(i).getCustomer());
                }

                ArrayAdapter<String> adapterPet = new ArrayAdapter<String>
                        (getContext(), android.R.layout.simple_list_item_1, nameListPet);
                spPet.setAdapter(adapterPet);

                spPet.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        idPet = nameListPet.indexOf(spPet.getText().toString());
                    }
                });
            }

            @Override
            public void onFailure(Call<List<PetDAO>> call, Throwable t) {
                Toast.makeText(getContext(), "Kesalahan Jaringan", Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void getCode(String code){
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<List<TransactionServiceDAO>> get = apiService.getCodeLengthTransactionService(code);

        get.enqueue(new Callback<List<TransactionServiceDAO>>() {
            @Override
            public void onResponse(Call<List<TransactionServiceDAO>> call, Response<List<TransactionServiceDAO>> response) {
                tpLength = response.body().size();

                sCodeTS = sCode+"-"+String.format("%02d", tpLength+1);
                twCode.setText(sCodeTS);
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<List<TransactionServiceDAO>> call, Throwable t) {
                sCodeTS = sCode+"-"+String.format("%02d", 1);
                twCode.setText(sCodeTS);
                progressDialog.dismiss();
            }
        });
    }

    private void Add() {
        if(statusGuest == false)
        {
            sIdPet = idListPet.get(idPet);
        }
        else {
            sIdPet = "0";
        }

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<TransactionServiceDAO> add = apiService.addTransactionService(sIdPet, sIdCS, sCodeTS, sDate, "0", "0", "Penjualan", sCustomerService);

        add.enqueue(new Callback<TransactionServiceDAO>() {
            @Override
            public void onResponse(Call<TransactionServiceDAO> call, Response<TransactionServiceDAO> response) {
                Toast.makeText(getContext(), "Sukses menambah transaksi", Toast.LENGTH_SHORT).show();
                dismiss();

                Intent i = new Intent(getContext(), DetailTransactionServiceActivity.class);
                i.putExtra("kode",sCodeTS);
                getContext().startActivity(i);
            }

            @Override
            public void onFailure(Call<TransactionServiceDAO> call, Throwable t) {
                Toast.makeText(getContext(), "Gagal menambah transaksi", Toast.LENGTH_SHORT).show();
                dismiss();
            }
        });
    }
}