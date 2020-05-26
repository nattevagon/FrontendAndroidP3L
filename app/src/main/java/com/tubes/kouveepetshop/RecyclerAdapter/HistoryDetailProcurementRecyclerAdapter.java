package com.tubes.kouveepetshop.RecyclerAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tubes.kouveepetshop.Model.DetailProcurementDAO;
import com.tubes.kouveepetshop.R;

import java.util.List;

public class HistoryDetailProcurementRecyclerAdapter extends RecyclerView.Adapter<HistoryDetailProcurementRecyclerAdapter.RoomViewHolder>{
    private List<DetailProcurementDAO> dataList;
    private Context context;

    public HistoryDetailProcurementRecyclerAdapter(Context context, List<DetailProcurementDAO> dataList) {
        this.context=context;
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public RoomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.recycle_adapter_history_detail_procurement, parent, false);
        return new RoomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final HistoryDetailProcurementRecyclerAdapter.RoomViewHolder holder, final int position) {
        final DetailProcurementDAO brg = dataList.get(position);

        String number = Integer.toString(holder.getAdapterPosition()+1);
        holder.mNo.setText(number);
        holder.mName.setText(brg.getProduk());
        holder.mAmount.setText(brg.getJumlah());

    }

    @Override
    public int getItemCount() {
        return (dataList != null) ? dataList.size() : 0;
    }

    public class RoomViewHolder extends RecyclerView.ViewHolder{
        private TextView mNo, mName, mAmount;

        public RoomViewHolder(@NonNull View itemView) {
            super(itemView);
            mNo = itemView.findViewById(R.id.twNo);
            mName = itemView.findViewById(R.id.twName);
            mAmount = itemView.findViewById(R.id.twAmount);
        }
    }
}
