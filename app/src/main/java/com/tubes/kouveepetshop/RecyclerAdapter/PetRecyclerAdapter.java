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

import com.tubes.kouveepetshop.Activity.ViewPetActivity;
import com.tubes.kouveepetshop.Model.PetDAO;
import com.tubes.kouveepetshop.R;

import java.util.ArrayList;
import java.util.List;

public class PetRecyclerAdapter extends RecyclerView.Adapter<PetRecyclerAdapter.RoomViewHolder> implements Filterable {
    private String id, name, birthdate, petType, petSize, customer;
    private List<PetDAO> dataList;
    private List<PetDAO> filteredDataList;
    private Context context;

    public PetRecyclerAdapter(Context context, List<PetDAO> dataList) {
        this.context=context;
        this.dataList = dataList;
        this.filteredDataList = dataList;
    }

    @NonNull
    @Override
    public RoomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.recycle_adapter_pet, parent, false);
        return new RoomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PetRecyclerAdapter.RoomViewHolder holder, int position) {
        final PetDAO brg = filteredDataList.get(position);
        holder.mName.setText(brg.getNama());
        holder.mBirthdate.setText(brg.getTgl_lahir());
        holder.mPetType.setText(brg.getJenis_hewan());
        holder.mPetSize.setText(brg.getUkuran_hewan());
        holder.mCustomer.setText(brg.getCustomer());

        holder.mParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                id = brg.getId_hewan();

                Intent i = new Intent(context, ViewPetActivity.class);
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
        private TextView mName, mBirthdate, mPetSize, mPetType, mCustomer;
        private LinearLayout mParent;

        public RoomViewHolder(@NonNull View itemView) {
            super(itemView);
            mName = itemView.findViewById(R.id.twName);
            mBirthdate = itemView.findViewById(R.id.twPetBirthdate);
            mPetType = itemView.findViewById(R.id.twPetType);
            mPetSize = itemView.findViewById(R.id.twPetSize);
            mCustomer = itemView.findViewById(R.id.twCustomer);
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
                    List<PetDAO> filteredList = new ArrayList<>();
                    for (PetDAO PetDAO : dataList) {
                        if (PetDAO.getNama().toLowerCase().contains(charSequenceString.toLowerCase())) {
                            filteredList.add(PetDAO);
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
                filteredDataList = (List<PetDAO>) results.values;
                notifyDataSetChanged();
            }
        };
    }
}
