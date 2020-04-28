package com.tubes.kouveepetshop.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.squareup.picasso.Picasso;
import com.tubes.kouveepetshop.API.ApiClient;
import com.tubes.kouveepetshop.API.ApiInterface;
import com.tubes.kouveepetshop.Activity.DetailTransactionProductActivity;
import com.tubes.kouveepetshop.Activity.DetailTransactionServiceActivity;
import com.tubes.kouveepetshop.Model.DetailTransactionProductDAO;
import com.tubes.kouveepetshop.Model.DetailTransactionServiceDAO;
import com.tubes.kouveepetshop.Model.ProductDAO;
import com.tubes.kouveepetshop.Model.ServiceDAO;
import com.tubes.kouveepetshop.R;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddPetCareFragment extends BottomSheetDialogFragment{
    private TextView twName, twPetSize, twPrice, twSubTotal;
    private EditText etAmountDay;
    private ImageView imgClose;
    private Button btnAdd;
    private String sIdService, sIdDetailTS, sIdTS, sName, sPetSize, sAmountDate, sPrice, sSubTotal;
    private int total;
    private AddServiceDetailTransactionServiceFragment fragment;

    Locale localeID = new Locale("in", "ID");
    NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);

    public AddPetCareFragment(AddServiceDetailTransactionServiceFragment fragment) {
        this.fragment = fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_add_pet_care, container, false);

        sIdService = getArguments().getString("id_service", "");
        sIdTS = getArguments().getString("id_ts", "");
        sName= getArguments().getString("name", "");
        sPetSize= getArguments().getString("petsize", "");
        sPrice= getArguments().getString("price", "");

        twName = v.findViewById(R.id.twName);
        twPetSize = v.findViewById(R.id.twPetSize);
        twPrice = v.findViewById(R.id.twPrice);
        etAmountDay = v.findViewById(R.id.etAmountDay);
        twSubTotal = v.findViewById(R.id.twSubTotal);
        imgClose = v.findViewById(R.id.imgClose);
        btnAdd = v.findViewById(R.id.btnAdd);

        twName.setText(sName);
        twPetSize.setText(sPetSize);
        twPrice.setText(sPrice);

        imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(etAmountDay.getText().toString().equalsIgnoreCase(""))
                {
                    etAmountDay.setError("Kosong!");
                    etAmountDay.requestFocus();
                }
                else if(etAmountDay.getText().toString().equalsIgnoreCase("0"))
                {
                    etAmountDay.setError("Kurang dari 0!");
                    etAmountDay.requestFocus();
                }
                else
                {
                    Add(sIdService, etAmountDay.getText().toString());
                }
            }
        });

        return v;
    }

    public void Add(String id_service, String amountDay)
    {
        total = Integer.parseInt(sPrice)*Integer.parseInt(amountDay);
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<DetailTransactionServiceDAO> add = apiService.addDetailTS(sIdTS, id_service, amountDay, Integer.toString(total));

        add.enqueue(new Callback<DetailTransactionServiceDAO>() {
            @Override
            public void onResponse(Call<DetailTransactionServiceDAO> call, Response<DetailTransactionServiceDAO> response) {
                dismiss();
                fragment.dismiss();
                DetailTransactionServiceActivity detailTS = (DetailTransactionServiceActivity) getActivity();
                detailTS.onBack();
            }

            @Override
            public void onFailure(Call<DetailTransactionServiceDAO> call, Throwable t) {
                Toast.makeText(getContext(), "Gagal menambahkan layanan", Toast.LENGTH_SHORT).show();
                dismiss();
                fragment.dismiss();
                DetailTransactionServiceActivity detailTS = (DetailTransactionServiceActivity) getActivity();
                detailTS.onBack();
            }
        });
    }
}