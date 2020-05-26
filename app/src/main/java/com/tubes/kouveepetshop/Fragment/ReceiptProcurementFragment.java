package com.tubes.kouveepetshop.Fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import com.tubes.kouveepetshop.R;

public class ReceiptProcurementFragment extends DialogFragment {
    private ImageButton btnClose;
    private LinearLayout btnShow, btnDownload;
    private String sId;

    @Override
    public void onStart() {
        super.onStart();
        getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_receipt_procurement, container, false);

        btnClose = v.findViewById(R.id.btnClose);
        btnShow = v.findViewById(R.id.btnShow);
        btnDownload = v.findViewById(R.id.btnDownload);

        sId = getArguments().getString("id_procurement", "");

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        btnShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager manager2 = ((AppCompatActivity) getContext()).getSupportFragmentManager();
                PDFViewerFragment receiptDialog = new PDFViewerFragment();
                receiptDialog.show(manager2, "dialog");

                Bundle args = new Bundle();
                args.putString("id_procurement", sId);
                receiptDialog.setArguments(args);

                dismiss();
            }
        });

        btnDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "https://kouvee.modifierisme.com/AndroAPI/struk?id_pengadaan="+sId;
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(browserIntent);
                dismiss();
            }
        });

        return v;
    }

    private void downloadPDF()
    {

    }

}