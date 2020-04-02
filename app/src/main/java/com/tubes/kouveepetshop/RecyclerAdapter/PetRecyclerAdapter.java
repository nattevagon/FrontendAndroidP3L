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

import com.tubes.kouveepetshop.Activity.DetailPetActivity;
import com.tubes.kouveepetshop.Model.PetDAO;
import com.tubes.kouveepetshop.R;

import java.util.ArrayList;
import java.util.List;

public class PetRecyclerAdapter extends RecyclerView.Adapter<PetRecyclerAdapter.RoomViewHolder> implements Filterable {
    private String id, nama, tglLahir, jenis, ukuran, customer;
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
        holder.mNama.setText(brg.getNAMA());
        holder.mTglLahir.setText(brg.getTGL_LAHIR());
        holder.mJenisHewan.setText(brg.getJENIS_HEWAN());
        holder.mUkuranHewan.setText(brg.getUKURAN_HEWAN());
        holder.mCustomer.setText(brg.getCUSTOMER());

        holder.mParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                id = brg.getID_HEWAN();

                Intent i = new Intent(context, DetailPetActivity.class);
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
        private TextView mNama, mTglLahir, mJenisHewan, mUkuranHewan, mCustomer;
        private LinearLayout mParent;

        public RoomViewHolder(@NonNull View itemView) {
            super(itemView);
            mNama = itemView.findViewById(R.id.twName);
            mTglLahir = itemView.findViewById(R.id.twPetBirthdate);
            mJenisHewan = itemView.findViewById(R.id.twPetType);
            mUkuranHewan = itemView.findViewById(R.id.twPetSize);
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
                        if (PetDAO.getNAMA().toLowerCase().contains(charSequenceString.toLowerCase())) {
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
