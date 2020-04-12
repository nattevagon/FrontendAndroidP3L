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

import com.tubes.kouveepetshop.Activity.ViewCustomerActivity;
import com.tubes.kouveepetshop.Model.CustomerDAO;
import com.tubes.kouveepetshop.R;

import java.util.ArrayList;
import java.util.List;

public class CustomerRecyclerAdapter extends RecyclerView.Adapter<CustomerRecyclerAdapter.RoomViewHolder> implements Filterable {
    private String id;
    private List<CustomerDAO> dataList;
    private List<CustomerDAO> filteredDataList;
    private Context context;

    public CustomerRecyclerAdapter(Context context, List<CustomerDAO> dataList) {
        this.context=context;
        this.dataList = dataList;
        this.filteredDataList = dataList;
    }

    @NonNull
    @Override
    public RoomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.recycle_adapter_customer, parent, false);
        return new RoomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomerRecyclerAdapter.RoomViewHolder holder, int position) {
        final CustomerDAO brg = filteredDataList.get(position);
        holder.mIcon.setText(brg.getNama().substring(0, 1));
        holder.mName.setText(brg.getNama());
        holder.mAddress.setText(brg.getAlamat());
        holder.mPhoneNumber.setText(brg.getNo_telp());

        holder.mParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                id = brg.getId_customer();

                Intent i = new Intent(context, ViewCustomerActivity.class);
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
        private TextView mName, mAddress, mPhoneNumber, mIcon;
        private LinearLayout mParent;

        public RoomViewHolder(@NonNull View itemView) {
            super(itemView);
            mIcon = itemView.findViewById(R.id.twIcon);
            mName = itemView.findViewById(R.id.twName);
            mAddress = itemView.findViewById(R.id.twAddress);
            mPhoneNumber = itemView.findViewById(R.id.twPhoneNumber);
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
                    List<CustomerDAO> filteredList = new ArrayList<>();
                    for (CustomerDAO CustomerDAO : dataList) {
                        if (CustomerDAO.getNama().toLowerCase().contains(charSequenceString.toLowerCase())) {
                            filteredList.add(CustomerDAO);
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
                filteredDataList = (List<CustomerDAO>) results.values;
                notifyDataSetChanged();
            }
        };
    }
}
