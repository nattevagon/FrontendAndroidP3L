package com.tubes.kouveepetshop.RecyclerAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tubes.kouveepetshop.Model.DetailTransactionProductDAO;
import com.tubes.kouveepetshop.R;

import java.util.List;

public class HistoryDetailTransactionProductRecyclerAdapter extends RecyclerView.Adapter<HistoryDetailTransactionProductRecyclerAdapter.RoomViewHolder>{
    private String sIdService, sIdDetailTS, sIdTS, sName, sPrice, sTotal, sAmountDay;
    private int price;
    private List<DetailTransactionProductDAO> dataList;
    private Context context;

    public HistoryDetailTransactionProductRecyclerAdapter(Context context, List<DetailTransactionProductDAO> dataList) {
        this.context=context;
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public RoomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.recycle_adapter_history_detail_transaction_product, parent, false);
        return new RoomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final HistoryDetailTransactionProductRecyclerAdapter.RoomViewHolder holder, final int position) {
        final DetailTransactionProductDAO brg = dataList.get(position);

        holder.mName.setText(brg.getProduk());
        holder.mAmount.setText(brg.getJumlah());

    }

    @Override
    public int getItemCount() {
        return (dataList != null) ? dataList.size() : 0;
    }

    public class RoomViewHolder extends RecyclerView.ViewHolder{
        private TextView mName, mAmount;

        public RoomViewHolder(@NonNull View itemView) {
            super(itemView);
            mName = itemView.findViewById(R.id.twProduct);
            mAmount = itemView.findViewById(R.id.twAmount);
        }
    }
}
