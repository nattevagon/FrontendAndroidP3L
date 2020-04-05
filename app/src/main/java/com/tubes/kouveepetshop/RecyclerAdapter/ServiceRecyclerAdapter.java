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
import com.tubes.kouveepetshop.Model.ServiceDAO;
import com.tubes.kouveepetshop.Fragment.MasterBottomFragment;
import com.tubes.kouveepetshop.R;

import java.util.ArrayList;
import java.util.List;

public class ServiceRecyclerAdapter extends RecyclerView.Adapter<ServiceRecyclerAdapter.RoomViewHolder> implements Filterable {
    private String nama, id;
    private List<ServiceDAO> dataList;
    private List<ServiceDAO> filteredDataList;
    private Context context;

    public ServiceRecyclerAdapter(Context context, List<ServiceDAO> dataList) {
        this.context = context;
        this.dataList = dataList;
        this.filteredDataList = dataList;
    }

    @NonNull
    @Override
    public RoomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.recycle_adapter_service, parent, false);
        return new RoomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ServiceRecyclerAdapter.RoomViewHolder holder, int position) {
        final ServiceDAO brg = filteredDataList.get(position);
        holder.mName.setText(brg.getNama());

        holder.mParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager manager = ((AppCompatActivity) context).getSupportFragmentManager();
                MasterBottomFragment bottomSheet = new MasterBottomFragment();
                bottomSheet.show(manager, "bottomSheet");

                id = brg.getId_layanan();
                nama = brg.getNama();

                Bundle args = new Bundle();
                args.putString("id", id);
                args.putString("name", nama);
                args.putString("menu", "Service");
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
