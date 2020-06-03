package com.tubes.kouveepetshop.Activity;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tubes.kouveepetshop.API.ApiClient;
import com.tubes.kouveepetshop.API.ApiInterface;
import com.tubes.kouveepetshop.Fragment.AddServiceDetailTransactionServiceFragment;
import com.tubes.kouveepetshop.Fragment.EditTransactionServiceFragment;
import com.tubes.kouveepetshop.Fragment.SMSFragment;
import com.tubes.kouveepetshop.Fragment.SortProductFragment;
import com.tubes.kouveepetshop.Model.CustomerDAO;
import com.tubes.kouveepetshop.Model.DetailTransactionServiceDAO;
import com.tubes.kouveepetshop.Model.PetDAO;
import com.tubes.kouveepetshop.Model.TransactionServiceDAO;
import com.tubes.kouveepetshop.R;
import com.tubes.kouveepetshop.RecyclerAdapter.DetailTransactionServiceRecyclerAdapter;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailTransactionServiceActivity extends AppCompatActivity {
    private ImageButton btnBack, btnCancel, btnEdit;
    private Button btnAddService, btnConfirm, btnDone;
    private String sId, sCode, sDate, sPet, sIdPet, sIdPetSize, sPetSize, sSubtotal, sTotalPrice, sIdCustomer, sCustomerService, sStatus, sCustomerName, sPhoneNumber;
    private TextView twCode, twDate, twCustomer, twSubTotal, twPet;
    private int idPet, sumSubTotal, subTotal;
    List<String> idListPet = new ArrayList<String>();
    List<String> nameListPet = new ArrayList<String>();

    Locale localeID = new Locale("in", "ID");
    NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);


    private List<DetailTransactionServiceDAO> detailTSServiceList;
    private List<TransactionServiceDAO> TransactionService;
    private RecyclerView recyclerView;
    private DetailTransactionServiceRecyclerAdapter recyclerAdapter;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_transaction_service);

        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        progressDialog = new ProgressDialog(this);
        progressDialog.show();

        twCode = findViewById(R.id.twCode);
        twDate = findViewById(R.id.twDate);
        twCustomer = findViewById(R.id.twCustomer);
        twSubTotal = findViewById(R.id.twSubTotal);
        twPet = findViewById(R.id.twPet);
        btnAddService = findViewById(R.id.btnAddService);
        btnDone = findViewById(R.id.btnDone);
        btnConfirm = findViewById(R.id.btnConfirm);
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
        sId = i.getStringExtra("id");
        sCode = i.getStringExtra("kode");

        getData();

        btnAddService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager manager = DetailTransactionServiceActivity.this.getSupportFragmentManager();
                AddServiceDetailTransactionServiceFragment dialog = new AddServiceDetailTransactionServiceFragment();
                dialog.show(manager, "dialog");

                Bundle args = new Bundle();
                args.putString("id", sId);
                args.putString("pet", sPet);
                args.putString("petsize", sPetSize);
                args.putString("idpetsize", sIdPetSize);
                dialog.setArguments(args);
            }
        });

        detailTSServiceList = new ArrayList<>();
        recyclerView = findViewById(R.id.detailTSRecyclerView);
        recyclerAdapter = new DetailTransactionServiceRecyclerAdapter(DetailTransactionServiceActivity.this,detailTSServiceList, this);
        RecyclerView.LayoutManager LayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(LayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(recyclerAdapter);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancelItemFromList(view);
            }
        });

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(sPet.equalsIgnoreCase("Guest"))
                {
                    Toast.makeText(DetailTransactionServiceActivity.this, "Guest tidak dapat mengubah data", Toast.LENGTH_SHORT).show();
                }
                else {
                    FragmentManager manager = DetailTransactionServiceActivity.this.getSupportFragmentManager();
                    EditTransactionServiceFragment dialog = new EditTransactionServiceFragment();
                    dialog.show(manager, "dialog");

                    Bundle args = new Bundle();
                    args.putString("id", sId);
                    args.putString("pet", sPet);
                    args.putString("customer", sCustomerName);
                    dialog.setArguments(args);
                }
            }
        });

        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
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
        progressDialog.show();
        detailTSServiceList.clear();
        getData();
        recyclerAdapter.notifyDataSetChanged();
    }

    private void getData(){
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<List<TransactionServiceDAO>> get = apiService.getByCodeTransactionService(sCode);

        get.enqueue(new Callback<List<TransactionServiceDAO>>() {
            @Override
            public void onResponse(Call<List<TransactionServiceDAO>> call, Response<List<TransactionServiceDAO>> response) {
                for(int i=0;i<response.body().size();i++)
                {
                    sId = response.body().get(i).getId_tl();
                    sCode = response.body().get(i).getKode();
                    sDate = response.body().get(i).getTanggal();
                    sPet = response.body().get(i).getHewan();
                    sIdPet = response.body().get(i).getId_hewan();
                    sIdCustomer = response.body().get(i).getId_customer();
                    sCustomerService = response.body().get(i).getCustomer_service();
                    sStatus = response.body().get(i).getStatus();
                }

                twCode.setText(sCode);
                twDate.setText(sDate);
                twPet.setText(sPet);

                getCustomer(sIdCustomer);
                getPet(sIdPet);
                loadService(sId);
            }

            @Override
            public void onFailure(Call<List<TransactionServiceDAO>> call, Throwable t) {
                Toast.makeText(DetailTransactionServiceActivity.this, "Tidak ditemukan atau jaringan tidak ada", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
    }

    private void getCustomer(String id)
    {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<List<CustomerDAO>> get = apiService.getByCustomer(id);

        get.enqueue(new Callback<List<CustomerDAO>>() {
            @Override
            public void onResponse(Call<List<CustomerDAO>> call, Response<List<CustomerDAO>> response) {

                for(int i=0;i<response.body().size();i++)
                {
                    sCustomerName = response.body().get(i).getNama();
                    sPhoneNumber = response.body().get(i).getNo_telp();
                }

                if(sPet.equalsIgnoreCase("Guest"))
                {
                    twCustomer.setText("Guest");
                }
                else
                {
                    twCustomer.setText(sCustomerName);
                }
            }

            @Override
            public void onFailure(Call<List<CustomerDAO>> call, Throwable t) {
                Toast.makeText(DetailTransactionServiceActivity.this, "Koneksi hilang", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getPet(String id)
    {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<List<PetDAO>> get = apiService.getByPet(id);

        get.enqueue(new Callback<List<PetDAO>>() {
            @Override
            public void onResponse(Call<List<PetDAO>> call, Response<List<PetDAO>> response) {

                for(int i=0;i<response.body().size();i++)
                {
                    sIdPet = response.body().get(i).getId_hewan();
                    sPetSize = response.body().get(i).getUkuran_hewan();
                    sIdPetSize = response.body().get(i).getId_ukuran_hewan();
                }

                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<List<PetDAO>> call, Throwable t) {
                Toast.makeText(DetailTransactionServiceActivity.this, "Koneksi hilang", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadService(String id){
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<List<DetailTransactionServiceDAO>> call = apiService.getByIDTSDetailTS(id);

        call.enqueue(new Callback<List<DetailTransactionServiceDAO>>() {
            @Override
            public void onResponse(Call<List<DetailTransactionServiceDAO>> call, Response<List<DetailTransactionServiceDAO>> response) {
                detailTSServiceList.addAll(response.body());
                recyclerAdapter.notifyDataSetChanged();
                sumSubTotal = 0;
                for(int i=0;i<response.body().size();i++)
                {
                    sSubtotal = response.body().get(i).getTotal();
                    subTotal = Integer.parseInt(sSubtotal);
                    sumSubTotal = sumSubTotal + subTotal;
                }
                UpdateTransactionService(sumSubTotal);
                progressDialog.dismiss();
            }


            @Override
            public void onFailure(Call<List<DetailTransactionServiceDAO>> call, Throwable t) {
                UpdateTransactionService(0);
                progressDialog.dismiss();
            }
        });
    }

    private void UpdateTransactionService(final int sumSubTotal) {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<TransactionServiceDAO> call = apiService.updateSubTotalTransactionService(sId, Integer.toString(sumSubTotal));
        call.enqueue(new Callback<TransactionServiceDAO>() {
            @Override
            public void onResponse(Call<TransactionServiceDAO> call, Response<TransactionServiceDAO> response) {
                twSubTotal.setText(formatRupiah.format((double)sumSubTotal));
            }

            @Override
            public void onFailure(Call<TransactionServiceDAO> call, Throwable t) {
                Toast.makeText(DetailTransactionServiceActivity.this, "Kesalahan Jaringan", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void cancelItemFromList(View v) {

        AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());

        builder.setTitle("Yakin untuk membatalkan transaksi ?")
                .setMessage("Setelah melakukan pembatalan transaksi "+sCode+", " +
                        "maka transaksi akan dipindahkan kedalam history pembatalan transaksi.")
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

    private void confirmItemFromList(View v) {

        AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());

        builder.setTitle("Konfirmasi transaksi ?")
                .setMessage("Anda akan melakukan konfirmasi transaksi penjualan untuk "+sCode+".\n\n" +
                        "Setelah melakukan konfirmasi transaksi penjualan maka anda tidak bisa mengubah transaksi ini lagi.")
                .setIcon(R.drawable.ic_letter)
                .setCancelable(false)
                .setPositiveButton("YA",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                progressDialog.show();
                                Confirm();
                                onBackPressed();
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
        Call<TransactionServiceDAO> call = apiService.deleteTransactionService(sId);

        call.enqueue(new Callback<TransactionServiceDAO>() {
            @Override
            public void onResponse(Call<TransactionServiceDAO> call, Response<TransactionServiceDAO> response) {
                Toast.makeText(DetailTransactionServiceActivity.this, "Sukses", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
                onBackPressed();
            }

            @Override
            public void onFailure(Call<TransactionServiceDAO> call, Throwable t) {
                Toast.makeText(DetailTransactionServiceActivity.this, "Gagal", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void Confirm() {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<TransactionServiceDAO> call = apiService.confirmTransactionService(sId);

        call.enqueue(new Callback<TransactionServiceDAO>() {
            @Override
            public void onResponse(Call<TransactionServiceDAO> call, Response<TransactionServiceDAO> response) {
                Toast.makeText(DetailTransactionServiceActivity.this, "Sukses", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
                onBackPressed();
            }

            @Override
            public void onFailure(Call<TransactionServiceDAO> call, Throwable t) {
                Toast.makeText(DetailTransactionServiceActivity.this, "Gagal", Toast.LENGTH_SHORT).show();
                onBackPressed();
            }
        });
    }

    public void DeleteDetailItemList(View v, final String idDetail) {

        AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());

        builder.setTitle("Hapus layanan ?")
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
        Call<DetailTransactionServiceDAO> call = apiService.deleteDetailTS(id);

        call.enqueue(new Callback<DetailTransactionServiceDAO>() {
            @Override
            public void onResponse(Call<DetailTransactionServiceDAO> call, Response<DetailTransactionServiceDAO> response) {
                onBack();
            }

            @Override
            public void onFailure(Call<DetailTransactionServiceDAO> call, Throwable t) {
                progressDialog.dismiss();
            }
        });
    }

    public void sendSMSManual(String service, String service_total)
    {
        String subTotal = formatRupiah.format((double)Integer.parseInt(service_total));
        String sTotal = formatRupiah.format((double)sumSubTotal);
        Uri uri = Uri.parse("smsto:"+sPhoneNumber);
        Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
        intent.putExtra("sms_body", "Hallo Kami dari Kouvee Pet Shop! Transaksi layanan anda dengan kode "+sCode+" pada tanggal "+sDate+" dan hewan bernama "+sPet+" dengan customer bernama "+sCustomerName+" layanan "+service+" sudah selesai. Biaya Grooming "+subTotal+" dan Total Transaksi Anda "+sTotal+" Terimakasih!");
        startActivity(intent);
        Toast.makeText(DetailTransactionServiceActivity.this, "Sukses mengirim SMS", Toast.LENGTH_SHORT).show();
    }

    public void sendSMSAuto(String service, String service_total)
    {
        String subTotal = formatRupiah.format((double)Integer.parseInt(service_total));
        String sTotal = formatRupiah.format((double)sumSubTotal);
        String sText = "Hallo Kami dari Kouvee Pet Shop! Transaksi layanan anda dengan kode "+sCode+" pada tanggal "+sDate+" dan hewan bernama "+sPet+" dengan customer bernama "+sCustomerName+" layanan "+service+" sudah selesai. Biaya Grooming "+subTotal+" dan Total Transaksi Anda "+sTotal+" Terimakasih!";
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<TransactionServiceDAO> call = apiService.sendSMS(sPhoneNumber, sText);

        call.enqueue(new Callback<TransactionServiceDAO>() {
            @Override
            public void onResponse(Call<TransactionServiceDAO> call, Response<TransactionServiceDAO> response) {
                Toast.makeText(DetailTransactionServiceActivity.this, "Sukses mengirim SMS", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<TransactionServiceDAO> call, Throwable t) {
                Toast.makeText(DetailTransactionServiceActivity.this, "Sukses mengirim SMS", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
