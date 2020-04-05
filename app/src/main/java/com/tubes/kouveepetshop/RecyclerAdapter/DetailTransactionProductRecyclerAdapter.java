package com.tubes.kouveepetshop.RecyclerAdapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tubes.kouveepetshop.Activity.DetailTransactionProductActivity;
import com.tubes.kouveepetshop.Model.DetailTransactionProductDAO;
import com.tubes.kouveepetshop.Model.TransactionProductDAO;
import com.tubes.kouveepetshop.R;

import java.util.ArrayList;
import java.util.List;

public class DetailTransactionProductRecyclerAdapter extends RecyclerView.Adapter<DetailTransactionProductRecyclerAdapter.RoomViewHolder>{
    private String id;
    private List<DetailTransactionProductDAO> dataList;
    private Context context;

    public DetailTransactionProductRecyclerAdapter(Context context, List<DetailTransactionProductDAO> dataList) {
        this.context=context;
        this.dataList = dataList;
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

        holder.mParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                id = brg.getId_detail_tp();

//                Intent i = new Intent(context, DetailTransactionProductActivity.class);
//                i.putExtra("id",id);
//                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return (dataList != null) ? dataList.size() : 0;
    }

    public class RoomViewHolder extends RecyclerView.ViewHolder{
        private TextView mProduct, mAmount, mTotal;
        private LinearLayout mParent;

        public RoomViewHolder(@NonNull View itemView) {
            super(itemView);
            mProduct = itemView.findViewById(R.id.twProduct);
            mAmount = itemView.findViewById(R.id.twAmount);
            mParent = itemView.findViewById(R.id.linearLayout);
        }
    }
}
