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
import com.tubes.kouveepetshop.Activity.ViewSupplierActivity;
import com.tubes.kouveepetshop.Model.SupplierDAO;
import com.tubes.kouveepetshop.R;

import java.util.ArrayList;
import java.util.List;

public class SupplierRecyclerAdapter extends RecyclerView.Adapter<SupplierRecyclerAdapter.RoomViewHolder> implements Filterable {
    private String id, nama, noTelp, alamat;
    private List<SupplierDAO> dataList;
    private List<SupplierDAO> filteredDataList;
    private Context context;

    public SupplierRecyclerAdapter(Context context, List<SupplierDAO> dataList) {
        this.context=context;
        this.dataList = dataList;
        this.filteredDataList = dataList;
    }

    @NonNull
    @Override
    public RoomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.recycle_adapter_supplier, parent, false);
        return new RoomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SupplierRecyclerAdapter.RoomViewHolder holder, int position) {
        final SupplierDAO brg = filteredDataList.get(position);
        holder.mNama.setText(brg.getNama());
        holder.mNoTelp.setText(brg.getNo_telp());
        holder.mAlamat.setText(brg.getAlamat());

        holder.mParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                id = brg.getId_supplier();

                Intent i = new Intent(context, ViewSupplierActivity.class);
                i.putExtra("id",id);
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return filteredDataList.size();
    }

    public class RoomViewHolder extends RecyclerView.ViewHolder{
        private TextView mNama, mAlamat, mNoTelp;
        private LinearLayout mParent;

        public RoomViewHolder(@NonNull View itemView) {
            super(itemView);
            mNama = itemView.findViewById(R.id.twName);
            mAlamat = itemView.findViewById(R.id.twAlamat);
            mNoTelp = itemView.findViewById(R.id.twNoTelp);
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
                    List<SupplierDAO> filteredList = new ArrayList<>();
                    for (SupplierDAO SupplierDAO : dataList) {
                        if (SupplierDAO.getNama().toLowerCase().contains(charSequenceString.toLowerCase())) {
                            filteredList.add(SupplierDAO);
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
                filteredDataList = (List<SupplierDAO>) results.values;
                notifyDataSetChanged();
            }
        };
    }
}
