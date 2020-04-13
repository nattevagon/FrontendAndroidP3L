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
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.tubes.kouveepetshop.API.ApiClient;
import com.tubes.kouveepetshop.API.ApiInterface;
import com.tubes.kouveepetshop.Activity.DetailTransactionProductActivity;
import com.tubes.kouveepetshop.Activity.MenuCustomerServiceActivity;
import com.tubes.kouveepetshop.Java.SessionManager;
import com.tubes.kouveepetshop.Model.PetDAO;
import com.tubes.kouveepetshop.Model.TransactionProductDAO;
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

public class EditTransactionProductFragment extends DialogFragment {
    private TextView twCustomerService, twCode, twDate;
    private Button btnAdd;
    private CheckBox cbGuest;
    private AutoCompleteTextView spPet;
    private ImageView imgClose;
    private boolean statusGuest;
    private String sId, sCustomerService, sCode, sDate, sYear, sMonth, sDay, sCodeTP, sIdPet, sPet;
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
        View v = inflater.inflate(R.layout.fragment_edit_transaction_product, container, false);

        twCustomerService = v.findViewById(R.id.twCS);
        twDate = v.findViewById(R.id.twDate);
        spPet = v.findViewById(R.id.spPet);
        btnAdd = v.findViewById(R.id.btnAdd);
        cbGuest = v.findViewById(R.id.cbGuest);
        imgClose = v.findViewById(R.id.imgClose);

        sId = getArguments().getString("id", "");
        sPet = getArguments().getString("pet", "");
        HashMap<String, String> user = sessionManager.getUserDetail();
        sCustomerService = user.get(sessionManager.NAME);

        imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        loadSpinnerPet();

        if(sPet.equalsIgnoreCase("Guest"))
        {
            statusGuest = true;
            cbGuest.setChecked(true);
            spPet.setEnabled(false);
        }
        else
        {
            statusGuest = false;
            cbGuest.setChecked(false);
            spPet.setText(sPet);
        }
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
                Update();
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


    private void Update() {
        if(statusGuest == false)
        {
            sIdPet = idListPet.get(idPet);
        }
        else {
            sIdPet = "0";
        }

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<TransactionProductDAO> add = apiService.updateTransactionProduct(sId, sIdPet, sCustomerService);

        add.enqueue(new Callback<TransactionProductDAO>() {
            @Override
            public void onResponse(Call<TransactionProductDAO> call, Response<TransactionProductDAO> response) {
                Toast.makeText(getContext(), "Sukses", Toast.LENGTH_SHORT).show();
                dismiss();
                DetailTransactionProductActivity detailTP = (DetailTransactionProductActivity) getActivity();
                detailTP.onBack();
            }

            @Override
            public void onFailure(Call<TransactionProductDAO> call, Throwable t) {
                Toast.makeText(getContext(), "Gagal", Toast.LENGTH_SHORT).show();
                dismiss();
                DetailTransactionProductActivity detailTP = (DetailTransactionProductActivity) getActivity();
                detailTP.onBack();
            }
        });
    }
}