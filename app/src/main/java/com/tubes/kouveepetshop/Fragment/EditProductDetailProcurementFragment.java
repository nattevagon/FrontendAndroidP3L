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
import com.squareup.picasso.Picasso;
import com.tubes.kouveepetshop.API.ApiClient;
import com.tubes.kouveepetshop.API.ApiInterface;
import com.tubes.kouveepetshop.Activity.DetailProcurementActivity;
import com.tubes.kouveepetshop.Model.DetailProcurementDAO;
import com.tubes.kouveepetshop.Model.ProductDAO;
import com.tubes.kouveepetshop.R;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditProductDetailProcurementFragment extends BottomSheetDialogFragment{
    private TextView twName;
    private EditText etAmount;
    private ImageView imgProduct, imgClose;
    private Button btnIncrement, btnDecrement, btnSave;
    private String sIdProduct, sIdDetailP, sIdP, sName, sAmount, sSubTotal, sTotal, sPrice, sImage, url;
    private int amount, price;

    Locale localeID = new Locale("in", "ID");
    NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_edit_detail_product_detail_procurement, container, false);

        sIdDetailP = getArguments().getString("id_detail_p", "");
        sIdP = getArguments().getString("id_procurement", "");
        sIdProduct = getArguments().getString("id_product", "");
        sAmount = getArguments().getString("amount", "");

        twName = v.findViewById(R.id.twName);
        etAmount = v.findViewById(R.id.etAmount);
        imgProduct = v.findViewById(R.id.imgProduct);
        imgClose = v.findViewById(R.id.imgClose);
        btnIncrement = v.findViewById(R.id.btnIncrement);
        btnDecrement = v.findViewById(R.id.btnDecrement);
        btnSave = v.findViewById(R.id.btnSave);

        imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        getDataProduct();

        etAmount.setText(sAmount);
        sAmount = etAmount.getText().toString();
        amount = Integer.parseInt(sAmount);
        displayAmount(amount);

        btnIncrement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(etAmount.getText().toString().equalsIgnoreCase(""))
                {
                    etAmount.setText("1");
                }
                else
                {
                    sAmount = etAmount.getText().toString();
                    amount = Integer.parseInt(sAmount);
                    amount = amount + 1;
                    displayAmount(amount);
                }
            }
        });

        btnDecrement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(amount<2||etAmount.getText().toString().equalsIgnoreCase(""))
                {}
                else
                {
                    sAmount = etAmount.getText().toString();
                    amount = Integer.parseInt(sAmount);
                    amount = amount-1;
                    displayAmount(amount);
                }
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(etAmount.getText().toString().equalsIgnoreCase(""))
                {
                    etAmount.setError("Kosong", null);
                    etAmount.requestFocus();
                }
                else if(etAmount.getText().toString().equalsIgnoreCase("0"))
                {
                    etAmount.setError("Kurang dari 1!", null);
                    etAmount.requestFocus();
                }
                else {
                    sAmount = etAmount.getText().toString();
                    amount = Integer.parseInt(sAmount);
                    price = Integer.parseInt(sPrice);
                    UpdateDetail(sIdDetailP, sIdP, sIdProduct, Integer.toString(amount), Integer.toString(amount * price));
                }
            }
        });

        return v;
    }

    private void displayAmount(int number) {
        etAmount.setText("" + number);
    }

    private void getDataProduct()
    {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<List<ProductDAO>> get = apiService.getByProduct(sIdProduct);

        get.enqueue(new Callback<List<ProductDAO>>() {
            @Override
            public void onResponse(Call<List<ProductDAO>> call, Response<List<ProductDAO>> response) {

                for(int i=0;i<response.body().size();i++)
                {
                    sName = response.body().get(i).getNama();
                    sPrice = response.body().get(i).getHarga();
                    sImage = response.body().get(i).getGambar();
                }

                twName.setText(sName);
                price = Integer.parseInt(sPrice);
                url = "https://kouvee.modifierisme.com/upload/"+sImage;
                Picasso.with(getContext()).load(url).resize(300,300).centerCrop().into(imgProduct);
            }

            @Override
            public void onFailure(Call<List<ProductDAO>> call, Throwable t) {
                Toast.makeText(getContext(), "Koneksi hilang", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void UpdateDetail(String id_detail_p, String id_p, String id_product, String amount, String total)
    {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<DetailProcurementDAO> update = apiService.updateDetailP(id_detail_p, id_p, id_product, amount, total);

        update.enqueue(new Callback<DetailProcurementDAO>() {
            @Override
            public void onResponse(Call<DetailProcurementDAO> call, Response<DetailProcurementDAO> response) {
                DetailProcurementActivity detailP = (DetailProcurementActivity) getActivity();
                detailP.onBack();
                dismiss();
            }

            @Override
            public void onFailure(Call<DetailProcurementDAO> call, Throwable t) {
                dismiss();
            }
        });
    }
}