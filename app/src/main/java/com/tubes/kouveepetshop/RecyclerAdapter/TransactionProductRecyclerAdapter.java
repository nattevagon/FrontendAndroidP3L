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
import com.tubes.kouveepetshop.Model.TransactionProductDAO;
import com.tubes.kouveepetshop.R;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class TransactionProductRecyclerAdapter extends RecyclerView.Adapter<TransactionProductRecyclerAdapter.RoomViewHolder> implements Filterable {
    private String id, code;
    private List<TransactionProductDAO> dataList;
    private List<TransactionProductDAO> filteredDataList;
    private Context context;

    Locale localeID = new Locale("in", "ID");
    NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);

    public TransactionProductRecyclerAdapter(Context context, List<TransactionProductDAO> dataList) {
        this.context=context;
        this.dataList = dataList;
        this.filteredDataList = dataList;
    }

    @NonNull
    @Override
    public RoomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.recycle_adapter_transaction_product, parent, false);
        return new RoomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TransactionProductRecyclerAdapter.RoomViewHolder holder, int position) {
        final TransactionProductDAO brg = filteredDataList.get(position);

        holder.mCode.setText(brg.getKode());
        holder.mPet.setText(brg.getHewan());
        holder.mSubTotal.setText(formatRupiah.format((double)Double.parseDouble(brg.getSub_total())));

        holder.mParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                id = brg.getId_tp();
                code = brg.getKode();

                Intent i = new Intent(context, DetailTransactionProductActivity.class);
                i.putExtra("id",id);
                i.putExtra("kode",code);
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return filteredDataList.size();
    }

    public class RoomViewHolder extends RecyclerView.ViewHolder{
        private TextView mCode, mPet, mSubTotal, mStatus;
        private LinearLayout mParent;

        public RoomViewHolder(@NonNull View itemView) {
            super(itemView);
            mCode = itemView.findViewById(R.id.twCode);
            mPet = itemView.findViewById(R.id.twPet);
            mSubTotal = itemView.findViewById(R.id.twSubTotal);
            mParent = itemView.findViewById(R.id.linearLayout);
        }
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String charSequenceString = constraint.toString();
                if (charSequenceString.isEmpty()) {
                    filteredDataList = dataList;
                } else {
                    List<TransactionProductDAO> filteredList = new ArrayList<>();
                    for (TransactionProductDAO transactionProductDAO : dataList) {
                        if (transactionProductDAO.getKode().toLowerCase().contains(charSequenceString.toLowerCase()) || transactionProductDAO.getHewan().contains(charSequenceString.toLowerCase())) {
                            filteredList.add(transactionProductDAO);
                        }
                        filteredDataList = filteredList;
                    }

                }
                FilterResults results = new FilterResults();
                results.values = filteredDataList;
                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                filteredDataList = (List<TransactionProductDAO>) results.values;
                notifyDataSetChanged();
            }
        };
    }
}
