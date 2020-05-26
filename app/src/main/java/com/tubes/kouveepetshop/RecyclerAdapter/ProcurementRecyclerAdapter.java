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

import com.tubes.kouveepetshop.Activity.DetailProcurementActivity;
import com.tubes.kouveepetshop.Model.DetailProcurementDAO;
import com.tubes.kouveepetshop.Model.ProcurementDAO;
import com.tubes.kouveepetshop.R;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ProcurementRecyclerAdapter extends RecyclerView.Adapter<ProcurementRecyclerAdapter.RoomViewHolder> implements Filterable {
    private String id, code;
    private List<ProcurementDAO> dataList;
    private List<ProcurementDAO> filteredDataList;
    private Context context;

    Locale localeID = new Locale("in", "ID");
    NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);

    public ProcurementRecyclerAdapter(Context context, List<ProcurementDAO> dataList) {
        this.context=context;
        this.dataList = dataList;
        this.filteredDataList = dataList;
    }

    @NonNull
    @Override
    public RoomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.recycle_adapter_procurement, parent, false);
        return new RoomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProcurementRecyclerAdapter.RoomViewHolder holder, int position) {
        final ProcurementDAO brg = filteredDataList.get(position);

        holder.mCode.setText(brg.getKode());
        holder.mSupplier.setText(brg.getSupplier());
        holder.mTotal.setText(formatRupiah.format((double)Double.parseDouble(brg.getTotal_harga())));

        holder.mParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                id = brg.getId_pengadaan();
                code = brg.getKode();

                Intent i = new Intent(context, DetailProcurementActivity.class);
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
        private TextView mCode, mSupplier, mTotal, mStatus;
        private LinearLayout mParent;

        public RoomViewHolder(@NonNull View itemView) {
            super(itemView);
            mCode = itemView.findViewById(R.id.twCode);
            mSupplier = itemView.findViewById(R.id.twSupplier);
            mTotal = itemView.findViewById(R.id.twTotal);
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
                    List<ProcurementDAO> filteredList = new ArrayList<>();
                    for (ProcurementDAO ProcurementDAO : dataList) {
                        if (ProcurementDAO.getKode().toLowerCase().contains(charSequenceString.toLowerCase()) || ProcurementDAO.getSupplier().contains(charSequenceString.toLowerCase())) {
                            filteredList.add(ProcurementDAO);
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
                filteredDataList = (List<ProcurementDAO>) results.values;
                notifyDataSetChanged();
            }
        };
    }
}
