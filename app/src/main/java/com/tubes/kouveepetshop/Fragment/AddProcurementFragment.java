package com.tubes.kouveepetshop.Fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.tubes.kouveepetshop.API.ApiClient;
import com.tubes.kouveepetshop.API.ApiInterface;
import com.tubes.kouveepetshop.Activity.DetailProcurementActivity;
import com.tubes.kouveepetshop.Activity.DetailTransactionProductActivity;
import com.tubes.kouveepetshop.Model.DetailProcurementDAO;
import com.tubes.kouveepetshop.Model.SupplierDAO;
import com.tubes.kouveepetshop.Model.ProcurementDAO;
import com.tubes.kouveepetshop.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddProcurementFragment extends DialogFragment {
    private TextView twCode, twDate;
    private Button btnAdd;
    private AutoCompleteTextView spSupplier;
    private ImageButton btnClose;
    private String sCode, sDate, sYear, sMonth, sDay, sCodeP, sIdSupplier;
    private int idSupplier, tpLength = 0;
    List<String> idListSupplier = new ArrayList<String>();
    List<String> nameListSupplier = new ArrayList<String>();

    private ProgressDialog progressDialog;

    @Override
    public void onStart() {
        super.onStart();
        getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_add_procurement, container, false);

        progressDialog = new ProgressDialog(getContext());
        progressDialog.show();

        twCode = v.findViewById(R.id.twCode);
        twDate = v.findViewById(R.id.twDate);
        spSupplier = v.findViewById(R.id.spSupplier);
        btnAdd = v.findViewById(R.id.btnAdd);
        btnClose = v.findViewById(R.id.btnClose);

        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        sDate = dateFormat.format(date);
        System.out.println(sDate);

        twDate.setText(dateFormat.format(date));

        sYear = sDate.substring(0, 4);
        sMonth = sDate.substring(5, 7);
        sDay = sDate.substring(8, 10);

        sCode = "PO-"+sYear+"-"+sMonth+"-"+sDay;
        getCode(sCode);

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        loadSpinnerSupplier();

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(spSupplier.getText().toString().equals(nameListSupplier.get(idSupplier)))
                {
                    Add();
                }
                else
                {
                    spSupplier.setError("Supplier tidak ada!");
                }
            }
        });

        return v;
    }

    public void loadSpinnerSupplier() {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<List<SupplierDAO>> call = apiService.getAllSupplier();

        call.enqueue(new Callback<List<SupplierDAO>>() {
            @Override
            public void onResponse(Call<List<SupplierDAO>> call, Response<List<SupplierDAO>> response) {
                for (int i = 0; i < response.body().size(); i++) {
                    idListSupplier.add(response.body().get(i).getId_supplier());
                    nameListSupplier.add(response.body().get(i).getNama());
                }

                ArrayAdapter<String> adapterSupplier = new ArrayAdapter<String>
                        (getContext(), android.R.layout.simple_list_item_1, nameListSupplier);
                spSupplier.setAdapter(adapterSupplier);


                spSupplier.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View view, MotionEvent motionEvent) {
                        spSupplier.showDropDown();
                        return false;
                    }
                });

                spSupplier.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        idSupplier = nameListSupplier.indexOf(spSupplier.getText().toString());
                    }
                });
            }

            @Override
            public void onFailure(Call<List<SupplierDAO>> call, Throwable t) {
                Toast.makeText(getContext(), "Kesalahan Jaringan", Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void getCode(String code){
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<List<ProcurementDAO>> get = apiService.getCodeLengthProcurement(code);

        get.enqueue(new Callback<List<ProcurementDAO>>() {
            @Override
            public void onResponse(Call<List<ProcurementDAO>> call, Response<List<ProcurementDAO>> response) {
                tpLength = response.body().size();

                sCodeP = sCode+"-"+String.format("%02d", tpLength+1);
                twCode.setText(sCodeP);
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<List<ProcurementDAO>> call, Throwable t) {
                sCodeP = sCode+"-"+String.format("%02d", 1);
                twCode.setText(sCodeP);
                progressDialog.dismiss();
            }
        });
    }

    private void Add() {
        sIdSupplier = idListSupplier.get(idSupplier);
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<ProcurementDAO> add = apiService.addProcurement(sIdSupplier, sCodeP, sDate, "Proses", "0");

        add.enqueue(new Callback<ProcurementDAO>() {
            @Override
            public void onResponse(Call<ProcurementDAO> call, Response<ProcurementDAO> response) {
                Toast.makeText(getContext(), "Sukses menambah pengadaan", Toast.LENGTH_SHORT).show();
                dismiss();

                Intent i = new Intent(getContext(), DetailProcurementActivity.class);
                i.putExtra("kode",sCodeP);
                getContext().startActivity(i);
            }

            @Override
            public void onFailure(Call<ProcurementDAO> call, Throwable t) {
                Toast.makeText(getContext(), "Gagal menambah pengadaan", Toast.LENGTH_SHORT).show();
                dismiss();
            }
        });
    }
}