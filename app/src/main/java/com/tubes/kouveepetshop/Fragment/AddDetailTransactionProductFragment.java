package com.tubes.kouveepetshop.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import com.tubes.kouveepetshop.Model.DetailTransactionProductDAO;
import com.tubes.kouveepetshop.Model.ProductDAO;
import com.tubes.kouveepetshop.R;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddDetailTransactionProductFragment extends BottomSheetDialogFragment{
    private TextView twName, twStock, twAmount, twSubTotal;
    private ImageView imgProduct, imgClose;
    private Button btnIncrement, btnDecrement, btnSave;
    private String sIdProduct, sIdDetailTP, sIdTP, sName, sAmount, sStock, sSubTotal, sPrice, sImage, url;
    private int amount, subTotal, price, stock;

    Locale localeID = new Locale("in", "ID");
    NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_control_detail_transaction_product, container, false);

        sIdProduct = getArguments().getString("id_product", "");
        sIdDetailTP = getArguments().getString("id_detail_tp", "");
        sIdTP = getArguments().getString("id_tp", "");
        Toast.makeText(getContext(), "id P: "+sIdProduct+"id DTP: "+sIdDetailTP+", ID TP: "+sIdTP, Toast.LENGTH_SHORT).show();

        twName = v.findViewById(R.id.twName);
        twStock = v.findViewById(R.id.twStock);
        twAmount = v.findViewById(R.id.twAmount);
        twSubTotal = v.findViewById(R.id.twSubTotal);
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
        getDataDetailTP();

        btnIncrement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(amount>=stock)
                {
                    Toast.makeText(getContext(), "Jumlah tidak boleh lebih dari minimal stok", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    amount = amount+1 ;
                    displayAmount(amount);
                    subTotal = price*amount;
                    displaySubTotal(subTotal);
                }
            }
        });

        btnDecrement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(amount<2)
                {
                    Toast.makeText(getContext(), "Jumlah tidak boleh kurang dari 1", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    amount = amount-1;
                    displayAmount(amount);
                    subTotal = price*amount;
                    displaySubTotal(subTotal);
                }
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "idDetail: "+sIdDetailTP+"ID TP: "+sIdTP+"id produk : "+sIdProduct+"amount : "+amount+"Sub Total : "+subTotal, Toast.LENGTH_SHORT).show();
                UpdateDetail(sIdDetailTP, sIdTP, sIdProduct, Integer.toString(amount), Integer.toString(subTotal));
            }
        });

        return v;
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
                    sStock = response.body().get(i).getStok();
                    sPrice = response.body().get(i).getHarga();
                    sImage = response.body().get(i).getGambar();
                }

                twName.setText(sName);
                twStock.setText("Stok : "+sStock);
                stock = Integer.parseInt(sStock);
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

    private void getDataDetailTP()
    {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<List<DetailTransactionProductDAO>> get = apiService.getByIDDetailTP(sIdDetailTP);

        get.enqueue(new Callback<List<DetailTransactionProductDAO>>() {
            @Override
            public void onResponse(Call<List<DetailTransactionProductDAO>> call, Response<List<DetailTransactionProductDAO>> response) {

                for(int i=0;i<response.body().size();i++)
                {
                    sAmount = response.body().get(i).getJumlah();
                    sSubTotal = response.body().get(i).getTotal();
                }
                amount = Integer.parseInt(sAmount);
                subTotal = Integer.parseInt(sSubTotal);
                displayAmount(amount);
                displaySubTotal(subTotal);
            }

            @Override
            public void onFailure(Call<List<DetailTransactionProductDAO>> call, Throwable t) {
                Toast.makeText(getContext(), "Koneksi hilang", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void displayAmount(int number) {
        twAmount.setText("" + number);
    }

    private void displaySubTotal(int subTotal)
    {
        twSubTotal.setText(formatRupiah.format((double)subTotal));
    }

    private void UpdateDetail(String id_detail_tp, String id_tp, String id_product, String amount, String total)
    {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<DetailTransactionProductDAO> update = apiService.updateDetailTP(id_detail_tp, id_tp, id_product,amount,total);

        update.enqueue(new Callback<DetailTransactionProductDAO>() {
            @Override
            public void onResponse(Call<DetailTransactionProductDAO> call, Response<DetailTransactionProductDAO> response) {
                dismiss();
                DetailTransactionProductActivity detailTP = (DetailTransactionProductActivity) getActivity();
                detailTP.onBack();
            }

            @Override
            public void onFailure(Call<DetailTransactionProductDAO> call, Throwable t) {
                Toast.makeText(getContext(), "Fail", Toast.LENGTH_SHORT).show();
                dismiss();
                DetailTransactionProductActivity detailTP = (DetailTransactionProductActivity) getActivity();
                detailTP.onBack();
            }
        });
    }
}