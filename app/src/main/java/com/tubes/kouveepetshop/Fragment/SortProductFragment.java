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

import com.tubes.kouveepetshop.Activity.ProductCustomerActivity;
import com.tubes.kouveepetshop.R;

public class SortProductFragment extends DialogFragment {
    private ImageButton btnClose;
    private LinearLayout sortStockAsc, sortStockDesc, sortPriceAsc, sortPriceDesc;

    @Override
    public void onStart() {
        super.onStart();
        getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_sort_product, container, false);

        btnClose = v.findViewById(R.id.btnClose);
        sortStockAsc = v.findViewById(R.id.sortStockAsc);
        sortStockDesc = v.findViewById(R.id.sortStockDesc);
        sortPriceAsc = v.findViewById(R.id.sortPriceAsc);
        sortPriceDesc = v.findViewById(R.id.sortPriceDesc);

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        sortStockAsc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((ProductCustomerActivity) getActivity()).loadByStock("asc");
                dismiss();
            }
        });

        sortStockDesc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((ProductCustomerActivity) getActivity()).loadByStock("desc");
                dismiss();
            }
        });

        sortPriceAsc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((ProductCustomerActivity) getActivity()).loadByPrice("asc");
                dismiss();
            }
        });

        sortPriceDesc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((ProductCustomerActivity) getActivity()).loadByPrice("desc");
                dismiss();
            }
        });

        return v;
    }

}