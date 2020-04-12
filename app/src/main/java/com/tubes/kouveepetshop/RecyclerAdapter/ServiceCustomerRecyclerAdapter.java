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

import com.tubes.kouveepetshop.Fragment.ServiceBottomFragment;
import com.tubes.kouveepetshop.Fragment.ServiceCustomerBottomFragment;
import com.tubes.kouveepetshop.Model.ServiceDAO;
import com.tubes.kouveepetshop.R;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ServiceCustomerRecyclerAdapter extends RecyclerView.Adapter<ServiceCustomerRecyclerAdapter.RoomViewHolder> implements Filterable {
    private String id, name, petSize, price;
    private List<ServiceDAO> dataList;
    private List<ServiceDAO> filteredDataList;
    private Context context;

    Locale localeID = new Locale("in", "ID");
    NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);

    public ServiceCustomerRecyclerAdapter(Context context, List<ServiceDAO> dataList) {
        this.context = context;
        this.dataList = dataList;
        this.filteredDataList = dataList;
    }

    @NonNull
    @Override
    public RoomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.recycle_adapter_service_customer, parent, false);
        return new RoomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ServiceCustomerRecyclerAdapter.RoomViewHolder holder, int position) {
        final ServiceDAO brg = filteredDataList.get(position);
        holder.mName.setText(brg.getNama());
        holder.mPetSize.setText(brg.getUkuran_hewan());
        holder.mPrice.setText(formatRupiah.format((double)Double.parseDouble(brg.getHarga())));

        holder.mParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager manager = ((AppCompatActivity) context).getSupportFragmentManager();
                ServiceCustomerBottomFragment bottomSheet = new ServiceCustomerBottomFragment();
                bottomSheet.show(manager, "bottomSheet");

                id = brg.getId_layanan();
                name = brg.getNama();
                petSize = brg.getUkuran_hewan();
                price = formatRupiah.format((double)Double.parseDouble(brg.getHarga()));

                Bundle args = new Bundle();
                args.putString("id", id);
                args.putString("name", name);
                args.putString("petsize", petSize);
                args.putString("price", price);
                bottomSheet.setArguments(args);
            }
        });
    }

    @Override
    public int getItemCount() {
        return filteredDataList.size();
    }

    public class RoomViewHolder extends RecyclerView.ViewHolder{
        private TextView mName, mPetSize, mPrice;
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
                    for (ServiceDAO serviceDAO : dataList) {
                        if (serviceDAO.getNama().toLowerCase().contains(charSequenceString.toLowerCase())) {
                            filteredList.add(serviceDAO);
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
