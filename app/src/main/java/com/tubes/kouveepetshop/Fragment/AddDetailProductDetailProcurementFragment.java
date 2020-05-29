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
import com.tubes.kouveepetshop.Activity.DetailTransactionProductActivity;
import com.tubes.kouveepetshop.Model.DetailProcurementDAO;
import com.tubes.kouveepetshop.Model.ProductDAO;
import com.tubes.kouveepetshop.R;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddDetailProductDetailProcurementFragment extends BottomSheetDialogFragment{
    private TextView twName;
    private EditText etAmount;
    private ImageView imgProduct, imgClose;
    private Button btnIncrement, btnDecrement, btnAdd;
    private String sIdProduct, sIdDetailP, sIdP, sName, sAmount, sSubTotal, sTotal, sPrice, sImage, url;
    private int amount, fixAmount, price;
    private AddProductDetailProcurementFragment fragment;

    Locale localeID = new Locale("in", "ID");
    NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);

    public AddDetailProductDetailProcurementFragment(AddProductDetailProcurementFragment fragment) {
        this.fragment = fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_add_detail_product_detail_procurement, container, false);

        sIdP = getArguments().getString("id_p", "");
        sIdProduct = getArguments().getString("id_product", "");
        sName = getArguments().getString("name", "");
        sPrice = getArguments().getString("price", "");
        sImage = getArguments().getString("image", "");

        twName = v.findViewById(R.id.twName);
        etAmount = v.findViewById(R.id.etAmount);
        imgProduct = v.findViewById(R.id.imgProduct);
        imgClose = v.findViewById(R.id.imgClose);
        btnIncrement = v.findViewById(R.id.btnIncrement);
        btnDecrement = v.findViewById(R.id.btnDecrement);
        btnAdd = v.findViewById(R.id.btnAdd);

        imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        twName.setText(sName);
        price = Integer.parseInt(sPrice);
        url = "https://kouvee.modifierisme.com/upload/"+sImage;
        Picasso.with(getContext()).load(url).resize(300,300).centerCrop().into(imgProduct);

        etAmount.setText("1");
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

        btnAdd.setOnClickListener(new View.OnClickListener() {
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
                else
                {
                    sAmount = etAmount.getText().toString();
                    amount = Integer.parseInt(sAmount);
                    Add(sIdProduct, amount, amount*price);
                }
            }
        });

        return v;
    }

    private void displayAmount(int number) {
        etAmount.setText("" + number);
    }

    public void Add(final String id_product, final int qty, final int subTotal)
    {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<List<DetailProcurementDAO>> call = apiService.getByProdukDP(sIdP, id_product);

        call.enqueue(new Callback<List<DetailProcurementDAO>>() {
            @Override
            public void onResponse(Call<List<DetailProcurementDAO>> call, Response<List<DetailProcurementDAO>> response) {
                for(int i=0;i<response.body().size();i++)
                {
                    sIdDetailP = response.body().get(i).getId_detail_p();
                    sAmount = response.body().get(i).getJumlah();
                    sTotal = response.body().get(i).getTotal();
                }

                int amount = Integer.parseInt(sAmount)+qty;
                int total = Integer.parseInt(sTotal)+subTotal;

                UpdateDetail(sIdDetailP, sIdP, id_product, Integer.toString(amount), Integer.toString(total));

            }

            @Override
            public void onFailure(Call<List<DetailProcurementDAO>> call, Throwable t) {
                AddProduct(id_product, qty, subTotal);
            }
        });
    }

    private void AddProduct(String id_product, int qty, int total)
    {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<DetailProcurementDAO> add = apiService.addDetailP(sIdP, id_product,Integer.toString(qty),Integer.toString(total));

        add.enqueue(new Callback<DetailProcurementDAO>() {
            @Override
            public void onResponse(Call<DetailProcurementDAO> call, Response<DetailProcurementDAO> response) {
                DetailProcurementActivity detailP = (DetailProcurementActivity) getActivity();
                detailP.onBack();
                dismiss();
                fragment.dismiss();
            }

            @Override
            public void onFailure(Call<DetailProcurementDAO> call, Throwable t) {
                dismiss();
            }
        });
    }

    private void UpdateDetail(String id_detail_p, String id_p, String id_product, String amount, String total)
    {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<DetailProcurementDAO> update = apiService.updateDetailP(id_detail_p, id_p, id_product,amount,total);

        update.enqueue(new Callback<DetailProcurementDAO>() {
            @Override
            public void onResponse(Call<DetailProcurementDAO> call, Response<DetailProcurementDAO> response) {
                DetailProcurementActivity detailP = (DetailProcurementActivity) getActivity();
                detailP.onBack();
                dismiss();
                fragment.dismiss();
            }

            @Override
            public void onFailure(Call<DetailProcurementDAO> call, Throwable t) {
                dismiss();
            }
        });
    }
}