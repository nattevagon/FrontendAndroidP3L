package com.tubes.kouveepetshop.Fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.tubes.kouveepetshop.API.ApiClient;
import com.tubes.kouveepetshop.API.ApiInterface;
import com.tubes.kouveepetshop.Activity.EditServiceActivity;
import com.tubes.kouveepetshop.Activity.ServiceActivity;
import com.tubes.kouveepetshop.Model.ServiceDAO;
import com.tubes.kouveepetshop.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ServiceCustomerBottomFragment extends BottomSheetDialogFragment{
    private ImageButton btnClose;
    private TextView twName, twPetSize, twPrice;
    private String sId, sName, sPetSize, sPrice;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_service_customer_bottom_sheet, container, false);

        sId = getArguments().getString("id", "");
        sName = getArguments().getString("name", "");
        sPetSize = getArguments().getString("petsize", "");
        sPrice = getArguments().getString("price", "");

        twName = v.findViewById(R.id.twName);
        twPetSize = v.findViewById(R.id.twPetSize);
        twPrice = v.findViewById(R.id.twPrice);
        btnClose = v.findViewById(R.id.btnClose);

        twName.setText(sName);
        twPetSize.setText(sPetSize);
        twPrice.setText(sPrice);

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        return v;
    }
}