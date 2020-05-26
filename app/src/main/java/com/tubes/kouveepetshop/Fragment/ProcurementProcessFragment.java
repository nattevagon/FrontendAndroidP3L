package com.tubes.kouveepetshop.Fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.tubes.kouveepetshop.API.ApiClient;
import com.tubes.kouveepetshop.API.ApiInterface;
import com.tubes.kouveepetshop.Model.DetailProcurementDAO;
import com.tubes.kouveepetshop.Model.ProcurementDAO;
import com.tubes.kouveepetshop.Model.ProductDAO;
import com.tubes.kouveepetshop.R;
import com.tubes.kouveepetshop.RecyclerAdapter.ProcurementProcessRecyclerAdapter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProcurementProcessFragment extends Fragment {
    private List<ProcurementDAO> list;
    private RecyclerView recyclerView;
    private ProcurementProcessRecyclerAdapter recyclerAdapter;
    private ShimmerFrameLayout mShimmerViewContainer;
    private ImageView imEmpty;

    public ProcurementProcessFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_procurement_history, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mShimmerViewContainer = view.findViewById(R.id.shimmer_view_container);
        mShimmerViewContainer.startShimmerAnimation();

        imEmpty = view.findViewById(R.id.imEmpty);
        imEmpty.setVisibility(View.INVISIBLE);

        recyclerView = view.findViewById(R.id.recyclerView);
        load();
    }

    public void onBack() {
        super.onResume();
        mShimmerViewContainer.startShimmerAnimation();
        load();
    }

    public void load(){
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<List<ProcurementDAO>> call = apiService.getAllByStatusProcurement("Dipesan");

        call.enqueue(new Callback<List<ProcurementDAO>>() {
            @Override
            public void onResponse(Call<List<ProcurementDAO>> call, Response<List<ProcurementDAO>> response) {
                for(int i=0;i<response.body().size();i++) {
                    String id = response.body().get(i).getId_pengadaan();

                    if(id.equalsIgnoreCase("false"))
                    {
                        recyclerView.setAdapter(null);
                        imEmpty.setVisibility(View.VISIBLE);
                    }
                    else
                    {
                        generateDataList(response.body());
                        recyclerAdapter.notifyDataSetChanged();
                    }
                }
                mShimmerViewContainer.stopShimmerAnimation();
                mShimmerViewContainer.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<List<ProcurementDAO>> call, Throwable t) {
                Toast.makeText(getContext(), "Kesalahan Jaringan", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void generateDataList(List<ProcurementDAO> list) {
        recyclerAdapter = new ProcurementProcessRecyclerAdapter(getContext(),list, this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(recyclerAdapter);
    }

    public void DoneItemList(View v, final String idDetail, String code) {

        AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());

        builder.setMessage("Pesanan pegadaan "+code+" sudah datang ?")
                .setCancelable(false)
                .setPositiveButton("SELESAI",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                mShimmerViewContainer.startShimmerAnimation();
                                Done(idDetail);
                            }
                        })
                .setNegativeButton("BATAL", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });

        builder.show();

    }

    public void Done(final String id) {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<ProcurementDAO> call = apiService.doneProcurement(id);

        call.enqueue(new Callback<ProcurementDAO>() {
            @Override
            public void onResponse(Call<ProcurementDAO> call, Response<ProcurementDAO> response) {
                loadDetail(id);
                onBack();
            }

            @Override
            public void onFailure(Call<ProcurementDAO> call, Throwable t) {
                mShimmerViewContainer.stopShimmerAnimation();
                mShimmerViewContainer.setVisibility(View.GONE);
            }
        });
    }

    public void loadDetail(String id){
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<List<DetailProcurementDAO>> call = apiService.getByIdPDetailP(id);

        call.enqueue(new Callback<List<DetailProcurementDAO>>() {
            @Override
            public void onResponse(Call<List<DetailProcurementDAO>> call, Response<List<DetailProcurementDAO>> response) {
                int amount, stok, totalStock;
                for(int i=0;i<response.body().size();i++) {
                    amount = Integer.parseInt(response.body().get(i).getJumlah());
                    stok = Integer.parseInt(response.body().get(i).getStok());
                    totalStock = amount+stok;
                    UpdateStock(response.body().get(i).getId_produk(), Integer.toString(totalStock));
                }

            }

            @Override
            public void onFailure(Call<List<DetailProcurementDAO>> call, Throwable t) {
            }
        });
    }

    public void UpdateStock(String idProduct, String stock) {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<ProductDAO> call = apiService.updateStockProduct(idProduct, stock);

        call.enqueue(new Callback<ProductDAO>() {
            @Override
            public void onResponse(Call<ProductDAO> call, Response<ProductDAO> response) {
            }

            @Override
            public void onFailure(Call<ProductDAO> call, Throwable t) {
            }
        });
    }
}
