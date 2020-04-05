package com.tubes.kouveepetshop.RecyclerAdapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tubes.kouveepetshop.Model.PetSizeDAO;
import com.tubes.kouveepetshop.Fragment.MasterBottomFragment;
import com.tubes.kouveepetshop.R;

import java.util.ArrayList;
import java.util.List;

public class PetSizeRecyclerAdapter extends RecyclerView.Adapter<PetSizeRecyclerAdapter.RoomViewHolder> implements Filterable {
    private String nama, id;
    private List<PetSizeDAO> dataList;
    private List<PetSizeDAO> filteredDataList;
    private Context context;

    public PetSizeRecyclerAdapter(Context context, List<PetSizeDAO> dataList) {
        this.context=context;
        this.dataList = dataList;
        this.filteredDataList = dataList;
    }

    @NonNull
    @Override
    public RoomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.recycle_adapter_pet_size, parent, false);
        return new RoomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PetSizeRecyclerAdapter.RoomViewHolder holder, int position) {
        final PetSizeDAO brg = filteredDataList.get(position);
        holder.mName.setText(brg.getNama());

        holder.mParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager manager = ((AppCompatActivity) context).getSupportFragmentManager();
                MasterBottomFragment bottomSheet = new MasterBottomFragment();
                bottomSheet.show(manager, "bottomSheet");

                id = brg.getId_ukuran_hewan();
                nama = brg.getNama();

                Bundle args = new Bundle();
                args.putString("id", id);
                args.putString("name", nama);
                args.putString("menu", "PetSize");
                bottomSheet.setArguments(args);
            }
        });
    }

    @Override
    public int getItemCount() {
        return filteredDataList.size();
    }

    public class RoomViewHolder extends RecyclerView.ViewHolder{
        private TextView mName;
        private LinearLayout mParent;

        public RoomViewHolder(@NonNull View itemView) {
            super(itemView);
            mName = itemView.findViewById(R.id.twName);
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
                    List<PetSizeDAO> filteredList = new ArrayList<>();
                    for (PetSizeDAO PetSize : dataList) {
                        if (PetSize.getNama().toLowerCase().contains(charSequenceString.toLowerCase())) {
                            filteredList.add(PetSize);
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
                filteredDataList = (List<PetSizeDAO>) results.values;
                notifyDataSetChanged();
            }
        };
    }
}
