package com.tubes.kouveepetshop.Activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tubes.kouveepetshop.API.ApiClient;
import com.tubes.kouveepetshop.API.ApiInterface;
import com.tubes.kouveepetshop.Fragment.AddProductDetailTransactionProductFragment;
import com.tubes.kouveepetshop.Fragment.EditTransactionProductFragment;
import com.tubes.kouveepetshop.Model.DetailTransactionProductDAO;
import com.tubes.kouveepetshop.Model.TransactionProductDAO;
import com.tubes.kouveepetshop.R;
import com.tubes.kouveepetshop.RecyclerAdapter.DetailTransactionProductRecyclerAdapter;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailTransactionProductActivity extends AppCompatActivity {
    private ImageButton btnBack, btnCancel, btnEdit;
    private Button btnAddProduct, btnConfirmDone;
    private String sId, sCode, sDate, sPet, sSubtotal, sTotalPrice, sCustomerService, sStatus;
    private TextView twCode, twDate, twCS, twSubTotal, twPet;
    private int idPet, sumSubTotal, subTotal;
    List<String> idListPet = new ArrayList<String>();
    List<String> nameListPet = new ArrayList<String>();

    Locale localeID = new Locale("in", "ID");
    NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);

    private List<DetailTransactionProductDAO> detailTPProductList;
    private List<TransactionProductDAO> transactionProduct;
    private RecyclerView recyclerView;
    private DetailTransactionProductRecyclerAdapter recyclerAdapter;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_transaction_product);

        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        progressDialog = new ProgressDialog(this);
        progressDialog.show();

        twCode = findViewById(R.id.twCode);
        twDate = findViewById(R.id.twDate);
        twCS = findViewById(R.id.twCS);
        twSubTotal = findViewById(R.id.twSubTotal);
        twPet = findViewById(R.id.twPet);
        btnAddProduct = findViewById(R.id.btnAddProduct);
        btnConfirmDone = findViewById(R.id.btnConfirmDone);
        btnCancel = findViewById(R.id.btnCancel);
        btnEdit = findViewById(R.id.btnEdit);

        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        Intent i = getIntent();
        sCode = i.getStringExtra("kode");

        btnAddProduct.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
            FragmentManager manager = DetailTransactionProductActivity.this.getSupportFragmentManager();
            AddProductDetailTransactionProductFragment dialog = new AddProductDetailTransactionProductFragment();
            dialog.show(manager, "dialog");

            Bundle args = new Bundle();
            args.putString("id", sId);
            dialog.setArguments(args);
            }
        });

        detailTPProductList = new ArrayList<>();
        recyclerView = findViewById(R.id.detailTPRecyclerView);
        recyclerAdapter = new DetailTransactionProductRecyclerAdapter(DetailTransactionProductActivity.this,detailTPProductList, this);
        RecyclerView.LayoutManager LayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(LayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(recyclerAdapter);

        getData();

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancelItemFromList(view);
            }
        });

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager manager = DetailTransactionProductActivity.this.getSupportFragmentManager();
                EditTransactionProductFragment dialog = new EditTransactionProductFragment();
                dialog.show(manager, "dialog");

                Bundle args = new Bundle();
                args.putString("id", sId);
                args.putString("pet", sPet);
                dialog.setArguments(args);
            }
        });

        btnConfirmDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmItemFromList(view);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    public void onBack() {
        super.onPostResume();
        progressDialog.show();
        detailTPProductList.clear();
        getData();
        recyclerAdapter.notifyDataSetChanged();
    }

    private void getData(){
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<List<TransactionProductDAO>> get = apiService.getByCodeTransactionProduct(sCode);

        get.enqueue(new Callback<List<TransactionProductDAO>>() {
            @Override
            public void onResponse(Call<List<TransactionProductDAO>> call, Response<List<TransactionProductDAO>> response) {
                for(int i=0;i<response.body().size();i++)
                {
                    sId = response.body().get(i).getId_tp();
                    sCode = response.body().get(i).getKode();
                    sDate = response.body().get(i).getTanggal();
                    sPet = response.body().get(i).getHewan();
                    sCustomerService = response.body().get(i).getCustomer_service();
                    sStatus = response.body().get(i).getStatus();
                }

                twCode.setText(sCode);
                twDate.setText(sDate);
                twCS.setText(sCustomerService);
                twPet.setText(sPet);

                loadProduct(sId);
            }

            @Override
            public void onFailure(Call<List<TransactionProductDAO>> call, Throwable t) {
                Toast.makeText(DetailTransactionProductActivity.this, "Tidak ditemukan atau jaringan tidak ada", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
    }

    private void loadProduct(String id){
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<List<DetailTransactionProductDAO>> call = apiService.getByIDTPDetailTP(id);

        call.enqueue(new Callback<List<DetailTransactionProductDAO>>() {
            @Override
            public void onResponse(Call<List<DetailTransactionProductDAO>> call, Response<List<DetailTransactionProductDAO>> response) {
                detailTPProductList.addAll(response.body());
                recyclerAdapter.notifyDataSetChanged();
                sumSubTotal = 0;
                for(int i=0;i<response.body().size();i++)
                {
                    sSubtotal = response.body().get(i).getTotal();
                    subTotal = Integer.parseInt(sSubtotal);
                    sumSubTotal = sumSubTotal + subTotal;
                }
                UpdateTransactionProduct(sumSubTotal);
                progressDialog.dismiss();
            }


            @Override
            public void onFailure(Call<List<DetailTransactionProductDAO>> call, Throwable t) {
                UpdateTransactionProduct(0);
                progressDialog.dismiss();
            }
        });
    }

    private void UpdateTransactionProduct(final int sumSubTotal) {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<TransactionProductDAO> call = apiService.updateSubTotalTransactionProduct(sId, Integer.toString(sumSubTotal));

        call.enqueue(new Callback<TransactionProductDAO>() {
            @Override
            public void onResponse(Call<TransactionProductDAO> call, Response<TransactionProductDAO> response) {
                twSubTotal.setText(formatRupiah.format((double)sumSubTotal));
            }

            @Override
            public void onFailure(Call<TransactionProductDAO> call, Throwable t) {
                Toast.makeText(DetailTransactionProductActivity.this, "Kesalahan Jaringan", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void cancelItemFromList(View v) {

        AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());

        builder.setMessage("Batalkan transaksi ?")
                .setCancelable(false)
                .setPositiveButton("YA",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                progressDialog.show();
                                Cancel();
                            }
                        })
                .setNegativeButton("TIDAK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });

        builder.show();

    }

    private void confirmItemFromList(View v) {

        AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());

        builder.setMessage("Yakin untuk menyelesaikan transaksi?")
                .setCancelable(false)
                .setPositiveButton("YA",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                progressDialog.show();
                                Confirm();
                            }
                        })
                .setNegativeButton("TIDAK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });

        builder.show();

    }

    private void Cancel() {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<TransactionProductDAO> call = apiService.deleteTransactionProduct(sId);

        call.enqueue(new Callback<TransactionProductDAO>() {
            @Override
            public void onResponse(Call<TransactionProductDAO> call, Response<TransactionProductDAO> response) {
                Toast.makeText(DetailTransactionProductActivity.this, "Sukses", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
                onBackPressed();
            }

            @Override
            public void onFailure(Call<TransactionProductDAO> call, Throwable t) {
                Toast.makeText(DetailTransactionProductActivity.this, "Gagal", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void Confirm() {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<TransactionProductDAO> call = apiService.confirmTransactionProduct(sId);

        call.enqueue(new Callback<TransactionProductDAO>() {
            @Override
            public void onResponse(Call<TransactionProductDAO> call, Response<TransactionProductDAO> response) {
                Toast.makeText(DetailTransactionProductActivity.this, "Sukses", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
                onBackPressed();
            }

            @Override
            public void onFailure(Call<TransactionProductDAO> call, Throwable t) {
                Toast.makeText(DetailTransactionProductActivity.this, "Gagal", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void DeleteDetailItemList(View v, final String idDetail) {

        AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());

        builder.setMessage("Hapus produk ?")
                .setCancelable(false)
                .setPositiveButton("HAPUS",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                progressDialog.show();
                                DeleteDetail(idDetail);
                            }
                        })
                .setNegativeButton("BATAL", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });

        builder.show();

    }

    public void DeleteDetail(final String id) {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<DetailTransactionProductDAO> call = apiService.deleteDetailTP(id);

        call.enqueue(new Callback<DetailTransactionProductDAO>() {
            @Override
            public void onResponse(Call<DetailTransactionProductDAO> call, Response<DetailTransactionProductDAO> response) {
                onBack();
            }

            @Override
            public void onFailure(Call<DetailTransactionProductDAO> call, Throwable t) {
                progressDialog.dismiss();
            }
        });
    }

}
