package com.tubes.kouveepetshop.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.tubes.kouveepetshop.API.ApiClient;
import com.tubes.kouveepetshop.API.ApiInterface;
import com.tubes.kouveepetshop.Activity.DetailProcurementActivity;
import com.tubes.kouveepetshop.Java.SessionManager;
import com.tubes.kouveepetshop.Model.SupplierDAO;
import com.tubes.kouveepetshop.Model.ProcurementDAO;
import com.tubes.kouveepetshop.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditProcurementFragment extends DialogFragment {
    private Button btnDone;
    private AutoCompleteTextView spSupplier;
    private ImageView imgClose;
    private String sId, sIdSupplier, sSupplier, sCode, sDate;
    private int idSupplier, tpLength = 0;
    List<String> idListSupplier = new ArrayList<String>();
    List<String> nameListSupplier = new ArrayList<String>();

    @Override
    public void onStart() {
        super.onStart();
        getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_edit_procurement, container, false);

        spSupplier = v.findViewById(R.id.spSupplier);
        btnDone = v.findViewById(R.id.btnDone);
        imgClose = v.findViewById(R.id.imgClose);

        sId = getArguments().getString("id", "");
        sSupplier = getArguments().getString("supplier", "");
        sCode = getArguments().getString("code", "");
        sDate = getArguments().getString("date", "");

        imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        loadSpinnerSupplier();
        spSupplier.setText(sSupplier);

        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(spSupplier.getText().toString().equals(nameListSupplier.get(idSupplier)))
                {
                    Update();
                }
                else {
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

                idSupplier = nameListSupplier.indexOf(sSupplier);
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


    private void Update() {
        sIdSupplier = idListSupplier.get(idSupplier);
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<ProcurementDAO> add = apiService.updateProcurement(sId, sIdSupplier, sCode, sDate);

        add.enqueue(new Callback<ProcurementDAO>() {
            @Override
            public void onResponse(Call<ProcurementDAO> call, Response<ProcurementDAO> response) {
                Toast.makeText(getContext(), "Sukses mengubah pengadaan", Toast.LENGTH_SHORT).show();
                dismiss();
                DetailProcurementActivity detailTS = (DetailProcurementActivity) getActivity();
                detailTS.onBack();
            }

            @Override
            public void onFailure(Call<ProcurementDAO> call, Throwable t) {
                Toast.makeText(getContext(), "Gagal mengubah pengadaan", Toast.LENGTH_SHORT).show();
                dismiss();
                DetailProcurementActivity detailTS = (DetailProcurementActivity) getActivity();
                detailTS.onBack();
            }
        });
    }
}