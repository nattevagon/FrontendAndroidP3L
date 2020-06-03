package com.tubes.kouveepetshop.Activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
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

import com.google.android.material.snackbar.Snackbar;
import com.tubes.kouveepetshop.API.ApiClient;
import com.tubes.kouveepetshop.API.ApiInterface;
import com.tubes.kouveepetshop.Fragment.AddProductDetailProcurementFragment;
import com.tubes.kouveepetshop.Fragment.AddProductDetailTransactionProductFragment;
import com.tubes.kouveepetshop.Fragment.EditProcurementFragment;
import com.tubes.kouveepetshop.Fragment.EditTransactionProductFragment;
import com.tubes.kouveepetshop.Fragment.EditTransactionServiceFragment;
import com.tubes.kouveepetshop.Model.CustomerDAO;
import com.tubes.kouveepetshop.Model.DetailProcurementDAO;
import com.tubes.kouveepetshop.Model.ProcurementDAO;
import com.tubes.kouveepetshop.R;
import com.tubes.kouveepetshop.RecyclerAdapter.DetailProcurementRecyclerAdapter;
import com.tubes.kouveepetshop.RecyclerAdapter.DetailTransactionProductRecyclerAdapter;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailProcurementActivity extends AppCompatActivity {
    private ImageButton btnBack, btnCancel, btnEdit;
    private Button btnAddProduct, btnConfirm, btnDone;
    private String sId, sCode, sDate, sSupplier, sTotal, sAddress;
    private TextView twCode, twDate, twTotal, twSupplier, twAddress;
    private int sumTotal, total;
    private Snackbar sbProduct;

    Locale localeID = new Locale("in", "ID");
    NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);

    private List<DetailProcurementDAO> detailProcurement;
    private List<ProcurementDAO> procurement;
    private RecyclerView recyclerView;
    private DetailProcurementRecyclerAdapter recyclerAdapter;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_procurement);

        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        progressDialog = new ProgressDialog(this);
        progressDialog.show();

        twCode = findViewById(R.id.twCode);
        twDate = findViewById(R.id.twDate);
        twSupplier = findViewById(R.id.twSupplier);
        twAddress = findViewById(R.id.twAddress);
        twTotal = findViewById(R.id.twTotal);
        btnAddProduct = findViewById(R.id.btnAddProduct);
        btnConfirm = findViewById(R.id.btnConfirm);
        btnDone = findViewById(R.id.btnDone);
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
                FragmentManager manager = DetailProcurementActivity.this.getSupportFragmentManager();
                AddProductDetailProcurementFragment dialog = new AddProductDetailProcurementFragment();
                dialog.show(manager, "dialog");

                Bundle args = new Bundle();
                args.putString("id", sId);
                dialog.setArguments(args);
                }
            });

        detailProcurement = new ArrayList<>();
        recyclerView = findViewById(R.id.detailPRecyclerView);
        recyclerAdapter = new DetailProcurementRecyclerAdapter(DetailProcurementActivity.this,detailProcurement, this);
        RecyclerView.LayoutManager LayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(LayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(recyclerAdapter);

        getData();

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager manager = DetailProcurementActivity.this.getSupportFragmentManager();
                EditProcurementFragment dialog = new EditProcurementFragment();
                dialog.show(manager, "dialog");

                Bundle args = new Bundle();
                args.putString("id", sId);
                args.putString("supplier", sSupplier);
                args.putString("code", sCode);
                args.putString("date", sDate);
                dialog.setArguments(args);
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancelItemFromList(view);
            }
        });

        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();            }
        });

        btnConfirm.setOnClickListener(new View.OnClickListener() {
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
//        progressDialog.show();
        detailProcurement.clear();
        getData();
        recyclerAdapter.notifyDataSetChanged();
    }

    private void getData(){
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<List<ProcurementDAO>> get = apiService.getByCodeProcurement(sCode);

        get.enqueue(new Callback<List<ProcurementDAO>>() {
            @Override
            public void onResponse(Call<List<ProcurementDAO>> call, Response<List<ProcurementDAO>> response) {
                for(int i=0;i<response.body().size();i++)
                {
                    sId = response.body().get(i).getId_pengadaan();
                    sCode = response.body().get(i).getKode();
                    sDate = response.body().get(i).getTanggal();
                    sSupplier = response.body().get(i).getSupplier();
                    sAddress = response.body().get(i).getAlamat();
                }

                twCode.setText(sCode);
                twDate.setText(sDate);
                twSupplier.setText(sSupplier);
                twAddress.setText(sAddress);
                loadProduct(sId);
            }

            @Override
            public void onFailure(Call<List<ProcurementDAO>> call, Throwable t) {
                Toast.makeText(DetailProcurementActivity.this, "Tidak ditemukan atau jaringan tidak ada", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
    }

    private void loadProduct(String id){
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<List<DetailProcurementDAO>> call = apiService.getByIdPDetailP(id);

        call.enqueue(new Callback<List<DetailProcurementDAO>>() {
            @Override
            public void onResponse(Call<List<DetailProcurementDAO>> call, Response<List<DetailProcurementDAO>> response) {
                detailProcurement.addAll(response.body());
                recyclerAdapter.notifyDataSetChanged();
                sumTotal = 0;
                for(int i=0;i<response.body().size();i++)
                {
                    sTotal = response.body().get(i).getTotal();
                    total = Integer.parseInt(sTotal);
                    sumTotal = sumTotal + total;
                }

                UpdateTransactionProduct(sumTotal);
//                Toast.makeText(DetailProcurementActivity.this, "Total "+sumTotal, Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }


            @Override
            public void onFailure(Call<List<DetailProcurementDAO>> call, Throwable t) {
                UpdateTransactionProduct(0);
                progressDialog.dismiss();
            }
        });
    }

    private void UpdateTransactionProduct(final int sumTotal) {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<ProcurementDAO> call = apiService.updateTotalProcurement(sId, Integer.toString(sumTotal));

        call.enqueue(new Callback<ProcurementDAO>() {
            @Override
            public void onResponse(Call<ProcurementDAO> call, Response<ProcurementDAO> response) {
                twTotal.setText(formatRupiah.format((double)sumTotal));
            }

            @Override
            public void onFailure(Call<ProcurementDAO> call, Throwable t) {
                Toast.makeText(DetailProcurementActivity.this, "Kesalahan Jaringan", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void cancelItemFromList(View v) {

        AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());

        builder.setTitle("Yakin untuk membatalkan pengadaan ?")
                .setMessage("Setelah melakukan pembatalan pengadaan "+sCode+", " +
                        "maka pengadaan akan dipindahkan kedalam history pengadaan bagian batal.")
                .setIcon(R.drawable.ic_cancel)
                .setCancelable(false)
                .setPositiveButton("BATALKAN",
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

    private void confirmItemFromList(final View v) {

        final AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());

        builder.setTitle("Konfirmasi pemesanan pengadaan ?")
                .setMessage("Anda akan melakukan konfirmasi pemesanan pengadaan untuk "+sCode+".\n\n" +
                        "Setelah melakukan konfirmasi dan mencetak surat pemesanan anda tidak bisa mengubah pemesanan lagi.")
                .setIcon(R.drawable.ic_letter)
                .setCancelable(false)
                .setPositiveButton("KONFIRMASI",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                if(detailProcurement.isEmpty())
                                {
                                    AlertDialog.Builder builder2 = new AlertDialog.Builder(v.getContext());
                                    builder2.setMessage("Produk dalam pengadaan kosong!")
                                            .setCancelable(false)
                                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int id) {
                                                }
                                            });
                                    AlertDialog alert = builder2.create();
                                    alert.show();
                                }
                                else
                                {
                                    progressDialog.show();
                                    Confirm();
                                    Intent i = new Intent(DetailProcurementActivity.this, PDFViewerActivity.class);
                                    i.putExtra("id_procurement",sId);
                                    startActivity(i);
                                }
                            }
                        })
                .setNegativeButton("BATAL", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });

        builder.show();

    }

    private void Cancel() {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<ProcurementDAO> call = apiService.deleteProcurement(sId);

        call.enqueue(new Callback<ProcurementDAO>() {
            @Override
            public void onResponse(Call<ProcurementDAO> call, Response<ProcurementDAO> response) {
                Toast.makeText(DetailProcurementActivity.this, "Sukses", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
                onBackPressed();
            }

            @Override
            public void onFailure(Call<ProcurementDAO> call, Throwable t) {
                Toast.makeText(DetailProcurementActivity.this, "Gagal", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void Confirm() {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<ProcurementDAO> call = apiService.confirmProcurement(sId);

        call.enqueue(new Callback<ProcurementDAO>() {
            @Override
            public void onResponse(Call<ProcurementDAO> call, Response<ProcurementDAO> response) {
                Toast.makeText(DetailProcurementActivity.this, "Sukses", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
                onBackPressed();
            }

            @Override
            public void onFailure(Call<ProcurementDAO> call, Throwable t) {
                Toast.makeText(DetailProcurementActivity.this, "Gagal", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void DeleteDetailItemList(View v, final String idDetail) {

        AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());

        builder.setTitle("Hapus produk ?")
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
        Call<DetailProcurementDAO> call = apiService.deleteDetailP(id);

        call.enqueue(new Callback<DetailProcurementDAO>() {
            @Override
            public void onResponse(Call<DetailProcurementDAO> call, Response<DetailProcurementDAO> response) {
                onBack();
            }

            @Override
            public void onFailure(Call<DetailProcurementDAO> call, Throwable t) {
                progressDialog.dismiss();
            }
        });
    }

}
