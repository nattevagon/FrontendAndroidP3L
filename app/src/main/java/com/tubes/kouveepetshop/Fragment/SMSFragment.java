package com.tubes.kouveepetshop.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.tubes.kouveepetshop.Activity.DetailTransactionServiceActivity;
import com.tubes.kouveepetshop.Activity.ProductCustomerActivity;
import com.tubes.kouveepetshop.R;

public class SMSFragment extends DialogFragment {
    private ImageButton btnClose;
    private LinearLayout btnManual, btnAuto;
    private String sName, sTotal;

    @Override
    public void onStart() {
        super.onStart();
        getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_sms, container, false);

        btnClose = v.findViewById(R.id.btnClose);
        btnManual = v.findViewById(R.id.btnManual);
        btnAuto = v.findViewById(R.id.btnAuto);

        sName = getArguments().getString("name", "");
        sTotal = getArguments().getString("total", "");

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        btnManual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((DetailTransactionServiceActivity) getActivity()).sendSMSManual(sName, sTotal);
                dismiss();
            }
        });

        btnAuto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((DetailTransactionServiceActivity) getActivity()).sendSMSAuto(sName, sTotal);
                dismiss();
            }
        });

        return v;
    }

}