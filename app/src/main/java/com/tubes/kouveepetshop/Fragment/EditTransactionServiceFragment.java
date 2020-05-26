package com.tubes.kouveepetshop.Fragment;

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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditTransactionServiceFragment extends DialogFragment {
    private Button btnDone;
    private AutoCompleteTextView spPet;
    private ImageView imgClose;
    private String sId, sCustomerService, sCustomer, sCode, sDate, sYear, sMonth, sDay, sCodeTP, sIdPet, sPet;
    private int idPet, tpLength = 0;
    private SessionManager sessionManager;
    List<String> idListPet = new ArrayList<String>();
    List<String> nameListPet = new ArrayList<String>();

    @Override
    public void onStart() {
        super.onStart();
        getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_edit_transaction_service, container, false);

        spPet = v.findViewById(R.id.spPet);
        btnDone = v.findViewById(R.id.btnDone);
        imgClose = v.findViewById(R.id.imgClose);

        sId = getArguments().getString("id", "");
        sPet = getArguments().getString("pet", "");
        sCustomer = getArguments().getString("customer", "");

        sessionManager = new SessionManager(getContext());
        HashMap<String, String> user = sessionManager.getUserDetail();
        sCustomerService = user.get(sessionManager.NAME);

        imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        loadSpinnerPet();
        spPet.setText(sPet+" / "+sCustomer);

        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(spPet.getText().toString().equals(nameListPet.get(idPet)))
                {
                    Update();
                }
                else {
                    spPet.setError("Hewan tidak ada!");
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

                idPet = nameListPet.indexOf(sPet+" / "+sCustomer);
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


    private void Update() {
        sIdPet = idListPet.get(idPet);
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<TransactionServiceDAO> add = apiService.updateTransactionService(sId, sIdPet, sCustomerService);

        add.enqueue(new Callback<TransactionServiceDAO>() {
            @Override
            public void onResponse(Call<TransactionServiceDAO> call, Response<TransactionServiceDAO> response) {
                Toast.makeText(getContext(), "Sukses mengubah transaksi layanan", Toast.LENGTH_SHORT).show();
                dismiss();
                DetailTransactionServiceActivity detailTS = (DetailTransactionServiceActivity) getActivity();
                detailTS.onBack();
            }

            @Override
            public void onFailure(Call<TransactionServiceDAO> call, Throwable t) {
                Toast.makeText(getContext(), "Gagal mengubah transaksi layanan", Toast.LENGTH_SHORT).show();
                dismiss();
                DetailTransactionServiceActivity detailTS = (DetailTransactionServiceActivity) getActivity();
                detailTS.onBack();
            }
        });
    }
}