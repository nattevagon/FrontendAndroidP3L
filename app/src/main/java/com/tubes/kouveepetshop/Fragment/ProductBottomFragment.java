package com.tubes.kouveepetshop.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.squareup.picasso.Picasso;
import com.tubes.kouveepetshop.API.ApiClient;
import com.tubes.kouveepetshop.API.ApiInterface;
import com.tubes.kouveepetshop.Model.ProductDAO;
import com.tubes.kouveepetshop.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductBottomFragment extends BottomSheetDialogFragment{
    private TextView twName, twStock, twUnit, twPrice;
    private ImageView imgProduct, imgClose;
    private String sId, sName, sPrice, sStock, sUnit, sImage, url;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_product_bottom_sheet, container, false);

        sId = getArguments().getString("id", "");
        Toast.makeText(getContext(), "ID : "+sId, Toast.LENGTH_SHORT).show();

        twName = v.findViewById(R.id.twName);
        twStock = v.findViewById(R.id.twStock);
        twUnit = v.findViewById(R.id.twUnit);
        twPrice = v.findViewById(R.id.twPrice);
        imgProduct = v.findViewById(R.id.imgProduct);
        imgClose = v.findViewById(R.id.imgClose);

        imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        getData();

        return v;
    }

    private void getData()
    {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<List<ProductDAO>> get = apiService.getByProduct(sId);

        get.enqueue(new Callback<List<ProductDAO>>() {
            @Override
            public void onResponse(Call<List<ProductDAO>> call, Response<List<ProductDAO>> response) {

                for(int i=0;i<response.body().size();i++)
                {
                    sId = response.body().get(i).getID_PRODUK();
                    sName = response.body().get(i).getNAMA();
                    sStock = response.body().get(i).getSTOK();
                    sUnit = response.body().get(i).getSATUAN();
                    sPrice = response.body().get(i).getHARGA();
                    sImage = response.body().get(i).getGAMBAR();
                }

                twName.setText(sName);
                twStock.setText(sStock);
                twUnit.setText(sUnit);
                twPrice.setText(sPrice);
                url = "https://kouvee.modifierisme.com/upload/"+sImage;
                Picasso.with(getContext()).load(url).resize(300,300).centerCrop().into(imgProduct);
            }

            @Override
            public void onFailure(Call<List<ProductDAO>> call, Throwable t) {
                Toast.makeText(getContext(), "Koneksi hilang", Toast.LENGTH_SHORT).show();
            }
        });
    }
}