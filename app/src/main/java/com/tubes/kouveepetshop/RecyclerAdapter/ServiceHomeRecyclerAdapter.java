package com.tubes.kouveepetshop.RecyclerAdapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tubes.kouveepetshop.Fragment.PetTypeSizeBottomFragment;
import com.tubes.kouveepetshop.Fragment.ServiceBottomFragment;
import com.tubes.kouveepetshop.Model.ServiceDAO;
import com.tubes.kouveepetshop.R;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class ServiceHomeRecyclerAdapter extends RecyclerView.Adapter<ServiceHomeRecyclerAdapter.RoomViewHolder> {
    private String id, name, petSize;
    private List<ServiceDAO> dataList;
    private Context context;

    Locale localeID = new Locale("in", "ID");
    NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);

    public ServiceHomeRecyclerAdapter(Context context, List<ServiceDAO> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public RoomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.recycle_adapter_service_home, parent, false);
        return new RoomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ServiceHomeRecyclerAdapter.RoomViewHolder holder, int position) {
        final ServiceDAO brg = dataList.get(position);
        holder.mName.setText(brg.getNama());
        holder.mPetSize.setText(brg.getUkuran_hewan());
        holder.mPrice.setText(formatRupiah.format((double)Double.parseDouble(brg.getHarga())));
    }

    @Override
    public int getItemCount() {
        return dataList.size();
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
}
