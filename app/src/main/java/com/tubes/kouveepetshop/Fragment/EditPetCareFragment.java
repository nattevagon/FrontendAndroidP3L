package com.tubes.kouveepetshop.Fragment;

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
import com.tubes.kouveepetshop.API.ApiClient;
import com.tubes.kouveepetshop.API.ApiInterface;
import com.tubes.kouveepetshop.Activity.DetailTransactionServiceActivity;
import com.tubes.kouveepetshop.Model.DetailTransactionServiceDAO;
import com.tubes.kouveepetshop.R;

import java.text.NumberFormat;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditPetCareFragment extends BottomSheetDialogFragment{
    private TextView twName, twPetSize, twPrice, twSubTotal;
    private EditText etAmountDay;
    private ImageView imgClose;
    private Button btnSave;
    private String sIdService, sIdDetailTS, sIdTS, sName, sPetSize, sAmountDay, sPrice, sSubTotal;
    private int total;

    Locale localeID = new Locale("in", "ID");
    NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_edit_pet_care, container, false);

        sIdDetailTS = getArguments().getString("id_detail_ts", "");
        sIdService = getArguments().getString("id_service", "");
        sIdTS = getArguments().getString("id_ts", "");
        sName = getArguments().getString("name", "");
        sPrice = getArguments().getString("price", "");
        sAmountDay = getArguments().getString("amount_day", "");

        twName = v.findViewById(R.id.twName);
        twPrice = v.findViewById(R.id.twPrice);
        etAmountDay = v.findViewById(R.id.etAmountDay);
        imgClose = v.findViewById(R.id.imgClose);
        btnSave = v.findViewById(R.id.btnSave);

        twName.setText(sName);
        twPrice.setText(sPrice);
        etAmountDay.setText(sAmountDay);

        imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
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
                    Update(sIdService, etAmountDay.getText().toString());
                }
            }
        });

        return v;
    }

    public void Update(String id_service, String amountDay)
    {
        total = Integer.parseInt(sPrice)*Integer.parseInt(amountDay);
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<DetailTransactionServiceDAO> add = apiService.updateDetailTS(sIdDetailTS, sIdTS, id_service, amountDay, Integer.toString(total));

        add.enqueue(new Callback<DetailTransactionServiceDAO>() {
            @Override
            public void onResponse(Call<DetailTransactionServiceDAO> call, Response<DetailTransactionServiceDAO> response) {
                dismiss();
                DetailTransactionServiceActivity detailTS = (DetailTransactionServiceActivity) getActivity();
                detailTS.onBack();
            }

            @Override
            public void onFailure(Call<DetailTransactionServiceDAO> call, Throwable t) {
                Toast.makeText(getContext(), "Gagal mengubah layanan", Toast.LENGTH_SHORT).show();
                dismiss();
                DetailTransactionServiceActivity detailTS = (DetailTransactionServiceActivity) getActivity();
                detailTS.onBack();
            }
        });
    }
}