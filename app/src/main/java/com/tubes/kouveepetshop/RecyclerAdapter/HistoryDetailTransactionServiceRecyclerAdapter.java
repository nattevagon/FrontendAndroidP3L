package com.tubes.kouveepetshop.RecyclerAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tubes.kouveepetshop.Model.DetailTransactionServiceDAO;
import com.tubes.kouveepetshop.R;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class HistoryDetailTransactionServiceRecyclerAdapter extends RecyclerView.Adapter<HistoryDetailTransactionServiceRecyclerAdapter.RoomViewHolder>{
    private String sIdService, sIdDetailTS, sIdTS, sName, sPrice, sTotal, sAmountDay;
    private int price;
    private List<DetailTransactionServiceDAO> dataList;
    private Context context;

    Locale localeID = new Locale("in", "ID");
    NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);

    public HistoryDetailTransactionServiceRecyclerAdapter(Context context, List<DetailTransactionServiceDAO> dataList) {
        this.context=context;
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public RoomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.recycle_adapter_history_detail_transaction_service, parent, false);
        return new RoomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final HistoryDetailTransactionServiceRecyclerAdapter.RoomViewHolder holder, final int position) {
        final DetailTransactionServiceDAO brg = dataList.get(position);

        int total = Integer.parseInt(brg.getTotal());
        int jumlah = Integer.parseInt(brg.getJumlah());

        holder.mName.setText(brg.getLayanan()+" "+brg.getUkuran_hewan());

        if(brg.getLayanan().equalsIgnoreCase("Penitipan Hewan"))
        {

            holder.mSubTotalAmount.setText(formatRupiah.format((double)Double.parseDouble(Integer.toString(total/jumlah)))+" x "+brg.getJumlah()+" Hari = "+formatRupiah.format((double)Double.parseDouble(Integer.toString(total))));

        }
        else
        {
            holder.mSubTotalAmount.setText(formatRupiah.format((double)Double.parseDouble(Integer.toString(total))));
        }
    }

    @Override
    public int getItemCount() {
        return (dataList != null) ? dataList.size() : 0;
    }

    public class RoomViewHolder extends RecyclerView.ViewHolder{
        private TextView mName, mSubTotalAmount;

        public RoomViewHolder(@NonNull View itemView) {
            super(itemView);
            mName = itemView.findViewById(R.id.twName);
            mSubTotalAmount = itemView.findViewById(R.id.twSubTotalAmount);
        }
    }
}
