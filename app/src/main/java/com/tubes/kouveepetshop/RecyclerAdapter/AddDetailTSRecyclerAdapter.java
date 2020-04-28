package com.tubes.kouveepetshop.RecyclerAdapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tubes.kouveepetshop.Fragment.AddPetCareFragment;
import com.tubes.kouveepetshop.Fragment.AddServiceDetailTransactionServiceFragment;
import com.tubes.kouveepetshop.Fragment.ControlDetailTransactionProductFragment;
import com.tubes.kouveepetshop.Model.ServiceDAO;
import com.tubes.kouveepetshop.R;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class AddDetailTSRecyclerAdapter extends RecyclerView.Adapter<AddDetailTSRecyclerAdapter.RoomViewHolder> implements Filterable {
    private String id, idTS, name, petSize, price;
    private List<ServiceDAO> dataList;
    private List<ServiceDAO> filteredDataList;
    private Context context;
    private AddServiceDetailTransactionServiceFragment fragment;

    Locale localeID = new Locale("in", "ID");
    NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);

    public AddDetailTSRecyclerAdapter(Context context, List<ServiceDAO> dataList, AddServiceDetailTransactionServiceFragment fragment) {
        this.fragment = fragment;
        this.context=context;
        this.dataList = dataList;
        this.filteredDataList = dataList;
    }

    @NonNull
    @Override
    public RoomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.recycle_adapter_add_detail_ts, parent, false);
        return new RoomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AddDetailTSRecyclerAdapter.RoomViewHolder holder, int position) {
        final ServiceDAO brg = filteredDataList.get(position);
        holder.mName.setText(brg.getNama());
        holder.mPetSize.setText(brg.getUkuran_hewan());
        holder.mPrice.setText(formatRupiah.format((double)Double.parseDouble(brg.getHarga())));

        holder.mParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                id = brg.getId_layanan();
                idTS = fragment.getId_tl();
                name = brg.getNama();
                petSize = brg.getUkuran_hewan();
                price = brg.getHarga();

                if(name.equalsIgnoreCase("Penitipan Hewan"))
                {
                    FragmentManager manager = ((AppCompatActivity) context).getSupportFragmentManager();
                    AddPetCareFragment dialog = new AddPetCareFragment(fragment);
                    dialog.show(manager, "dialog");

                    Bundle args = new Bundle();
                    args.putString("id_service", id);
                    args.putString("id_ts", idTS);
                    args.putString("name", name);
                    args.putString("petsize", petSize);
                    args.putString("price", price);
                    dialog.setArguments(args);
                }
                else
                {
                    fragment.Add(id, price);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return filteredDataList.size();
    }

    public class RoomViewHolder extends RecyclerView.ViewHolder{
        private TextView mName, mPetSize, mPrice;
        ImageView mImage;
        private LinearLayout mParent;

        public RoomViewHolder(@NonNull View itemView) {
            super(itemView);
            mName = itemView.findViewById(R.id.twName);
            mPetSize = itemView.findViewById(R.id.twPetSize);
            mPrice = itemView.findViewById(R.id.twPrice);
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
                    List<ServiceDAO> filteredList = new ArrayList<>();
                    for (ServiceDAO ServiceDAO : dataList) {
                        if (ServiceDAO.getNama().toLowerCase().contains(charSequenceString.toLowerCase())) {
                            filteredList.add(ServiceDAO);
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
                filteredDataList = (List<ServiceDAO>) results.values;
                notifyDataSetChanged();
            }
        };
    }
}
