package com.tubes.kouveepetshop.RecyclerAdapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tubes.kouveepetshop.API.ApiClient;
import com.tubes.kouveepetshop.API.ApiInterface;
import com.tubes.kouveepetshop.Activity.DetailTransactionProductActivity;
import com.tubes.kouveepetshop.Activity.DetailTransactionServiceActivity;
import com.tubes.kouveepetshop.Fragment.ControlDetailTransactionProductFragment;
import com.tubes.kouveepetshop.Model.DetailTransactionProductDAO;
import com.tubes.kouveepetshop.Model.TransactionProductDAO;
import com.tubes.kouveepetshop.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailTransactionProductRecyclerAdapter extends RecyclerView.Adapter<DetailTransactionProductRecyclerAdapter.RoomViewHolder>{
    private String sIdProduct, sProduk, sIdDetailTP, sIdTP;
    private List<DetailTransactionProductDAO> dataList;
    private Context context;
    private DetailTransactionProductActivity activity;

    public DetailTransactionProductRecyclerAdapter(Context context, List<DetailTransactionProductDAO> dataList, DetailTransactionProductActivity activity) {
        this.context=context;
        this.dataList = dataList;
        this.activity = activity;
    }

    @NonNull
    @Override
    public RoomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.recycle_adapter_detail_transaction_product, parent, false);
        return new RoomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DetailTransactionProductRecyclerAdapter.RoomViewHolder holder, int position) {
        final DetailTransactionProductDAO brg = dataList.get(position);
        holder.mProduct.setText(brg.getProduk());
        holder.mAmount.setText(brg.getJumlah());

        holder.mEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                FragmentManager manager = ((AppCompatActivity) context).getSupportFragmentManager();
                ControlDetailTransactionProductFragment bottomSheet = new ControlDetailTransactionProductFragment();
                bottomSheet.show(manager, "bottomSheet");

                sIdProduct = brg.getId_produk();
                sProduk = brg.getProduk();
                sIdDetailTP = brg.getId_detail_tp();
                sIdTP = brg.getId_tp();

                Bundle args = new Bundle();
                args.putString("id_product", sIdProduct);
                args.putString("id_detail_tp", sIdDetailTP);
                args.putString("id_tp", sIdTP);
                bottomSheet.setArguments(args);
            }
        });

        holder.mDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.DeleteDetailItemList(view, brg.getId_detail_tp());
            }
        });
    }

    @Override
    public int getItemCount() {
        return (dataList != null) ? dataList.size() : 0;
    }

    public class RoomViewHolder extends RecyclerView.ViewHolder{
        private TextView mProduct, mAmount;
        private ImageButton mEdit, mDelete;

        public RoomViewHolder(@NonNull View itemView) {
            super(itemView);
            mProduct = itemView.findViewById(R.id.twProduct);
            mAmount = itemView.findViewById(R.id.twAmount);
            mEdit = itemView.findViewById(R.id.btnEdit);
            mDelete = itemView.findViewById(R.id.btnDelete);
        }
    }
}
