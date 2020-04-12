package com.tubes.kouveepetshop.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.squareup.picasso.Picasso;
import com.tubes.kouveepetshop.API.ApiClient;
import com.tubes.kouveepetshop.API.ApiInterface;
import com.tubes.kouveepetshop.Model.ProductDAO;
import com.tubes.kouveepetshop.R;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailProductActivity extends AppCompatActivity {
    private TextView twName, twStock, twMinimal, twUnit, twPrice;
    private ImageButton btnEdit;
    private ImageView btnBack, btnDelete, imgProduct;
    private String sId, sName, sPrice, sStock, sMinimal, sUnit, sImage, url;
    private ShimmerFrameLayout mShimmerViewContainer;

    private Locale localeID = new Locale("in", "ID");
    private NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_product);

        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

        mShimmerViewContainer = findViewById(R.id.shimmer_loading);
        mShimmerViewContainer.startShimmerAnimation();

        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        twName = findViewById(R.id.twName);
        twStock = findViewById(R.id.twStock);
        twMinimal = findViewById(R.id.twMinimal);
        twUnit = findViewById(R.id.twUnit);
        twPrice = findViewById(R.id.twPrice);
        btnDelete = findViewById(R.id.btnDelete);
        btnEdit = findViewById(R.id.btnEdit);
        imgProduct = findViewById(R.id.imgProduct);

        Intent i = getIntent();
        sId = i.getStringExtra("id");

        getData();

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(DetailProductActivity.this, EditProductActivity.class);
                i.putExtra("id",sId);
                i.putExtra("name",sName);
                i.putExtra("price",sPrice);
                i.putExtra("stock",sStock);
                i.putExtra("minimal",sMinimal);
                i.putExtra("unit",sUnit);
                i.putExtra("image",sImage);
                startActivity(i);
            }
        });

        Toast.makeText(DetailProductActivity.this, "Coba hapus"+sId, Toast.LENGTH_SHORT).show();

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                deleteItemFromList(view);
            }
        });
    }

    @Override
    public void onRestart() {
        super.onRestart();
        getData();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
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
                    sId = response.body().get(i).getId_produk();
                    sName = response.body().get(i).getNama();
                    sStock = response.body().get(i).getStok();
                    sMinimal = response.body().get(i).getMinimal();
                    sUnit = response.body().get(i).getSatuan();
                    sPrice = response.body().get(i).getHarga();
                    sImage = response.body().get(i).getGambar();
                }

                twName.setText(sName);
                twStock.setText(sStock);
                twMinimal.setText(sMinimal);
                twUnit.setText(sUnit);
                twPrice.setText(formatRupiah.format((double)Double.parseDouble(sPrice)));
                url = "https://kouvee.modifierisme.com/upload/"+sImage;
                Picasso.with(DetailProductActivity.this).load(url).resize(300,300).centerCrop().into(imgProduct);
                mShimmerViewContainer.stopShimmerAnimation();
                mShimmerViewContainer.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<List<ProductDAO>> call, Throwable t) {
                Toast.makeText(DetailProductActivity.this, "Koneksi hilang", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void deleteItemFromList(View v) {

        AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());

        builder.setMessage("Hapus data ?")
                .setCancelable(false)
                .setPositiveButton("HAPUS",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                deleteData();
                            }
                        })
                .setNegativeButton("BATAL", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });

        builder.show();

    }

    private void deleteData()
    {
        mShimmerViewContainer.startShimmerAnimation();
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<ProductDAO> delete = apiService.deleteProduct(sId);

        delete.enqueue(new Callback<ProductDAO>() {
            @Override
            public void onResponse(Call<ProductDAO> call, Response<ProductDAO> response) {
                Toast.makeText(DetailProductActivity.this, "Berhasil dihapus", Toast.LENGTH_SHORT).show();
                mShimmerViewContainer.stopShimmerAnimation();
                mShimmerViewContainer.setVisibility(View.GONE);
                onBackPressed();
            }

            @Override
            public void onFailure(Call<ProductDAO> call, Throwable t) {
                Toast.makeText(DetailProductActivity.this, "Koneksi hilang", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
