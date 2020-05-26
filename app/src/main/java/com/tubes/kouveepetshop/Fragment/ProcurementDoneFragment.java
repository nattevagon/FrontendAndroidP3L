package com.tubes.kouveepetshop.Fragment;

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
import com.tubes.kouveepetshop.Model.ProcurementDAO;
import com.tubes.kouveepetshop.R;
import com.tubes.kouveepetshop.RecyclerAdapter.ProcurementDoneRecyclerAdapter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProcurementDoneFragment extends Fragment {
    private List<ProcurementDAO> list;
    private RecyclerView recyclerView;
    private ProcurementDoneRecyclerAdapter recyclerAdapter;
    private ShimmerFrameLayout mShimmerViewContainer;
    private ImageView imEmpty;

    public ProcurementDoneFragment() {
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
        list.clear();
        load();
        recyclerAdapter.notifyDataSetChanged();
    }

    public void load(){
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<List<ProcurementDAO>> call = apiService.getAllByStatusProcurement("Selesai");

        call.enqueue(new Callback<List<ProcurementDAO>>() {
            @Override
            public void onResponse(Call<List<ProcurementDAO>> call, Response<List<ProcurementDAO>> response) {
                for(int i=0;i<response.body().size();i++) {
                    String id = response.body().get(i).getId_pengadaan();

                    if(id.equalsIgnoreCase("false"))
                    {
                        imEmpty.setVisibility(View.VISIBLE);
                    }
                    else
                    {
                        generateDataList(response.body());
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
        recyclerAdapter = new ProcurementDoneRecyclerAdapter(getContext(),list);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(recyclerAdapter);
    }
}
