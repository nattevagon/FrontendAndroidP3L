package com.tubes.kouveepetshop.RecyclerAdapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tubes.kouveepetshop.Activity.DetailTransactionServiceActivity;
import com.tubes.kouveepetshop.Fragment.EditPetCareFragment;
import com.tubes.kouveepetshop.Model.DetailTransactionServiceDAO;
import com.tubes.kouveepetshop.R;

import java.util.List;

public class DetailTransactionServiceRecyclerAdapter extends RecyclerView.Adapter<DetailTransactionServiceRecyclerAdapter.RoomViewHolder>{
    private String sIdService, sIdDetailTS, sIdTS, sName, sPrice, sTotal, sAmountDay;
    private int price;
    private List<DetailTransactionServiceDAO> dataList;
    private Context context;
    private DetailTransactionServiceActivity activity;

    public DetailTransactionServiceRecyclerAdapter(Context context, List<DetailTransactionServiceDAO> dataList, DetailTransactionServiceActivity activity) {
        this.context=context;
        this.dataList = dataList;
        this.activity = activity;
    }

    @NonNull
    @Override
    public RoomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.recycle_adapter_detail_transaction_service, parent, false);
        return new RoomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DetailTransactionServiceRecyclerAdapter.RoomViewHolder holder, final int position) {
        final DetailTransactionServiceDAO brg = dataList.get(position);
        if(brg.getLayanan().equalsIgnoreCase("Penitipan Hewan"))
        {
            holder.mName.setText(brg.getLayanan()+" "+brg.getJumlah()+" Hari.099");
            holder.mEdit.setVisibility(View.VISIBLE);

            holder.mEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view){
                    FragmentManager manager = ((AppCompatActivity) context).getSupportFragmentManager();
                    EditPetCareFragment dialog = new EditPetCareFragment();
                    dialog.show(manager, "dialog");

                    sIdService = brg.getId_layanan();
                    sIdDetailTS = brg.getId_detail_tl();
                    sIdTS = brg.getId_tl();
                    sName = brg.getLayanan();
                    sTotal = brg.getTotal();
                    sAmountDay = brg.getJumlah();

                    price = Integer.parseInt(sTotal)/Integer.parseInt(sAmountDay);
                    sPrice = Integer.toString(price);

                    Bundle args = new Bundle();
                    args.putString("id_service", sIdService);
                    args.putString("id_detail_ts", sIdDetailTS);
                    args.putString("id_ts", sIdTS);
                    args.putString("name", sName);
                    args.putString("price", sPrice);
                    args.putString("amount_day", sAmountDay);
                    dialog.setArguments(args);
                }
            });

        }
        else
        {
            holder.mName.setText(brg.getLayanan());
            holder.mEdit.setVisibility(View.GONE);
        }


        holder.mDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.DeleteDetailItemList(view, brg.getId_detail_tl());
            }
        });
    }

    @Override
    public int getItemCount() {
        return (dataList != null) ? dataList.size() : 0;
    }

    public class RoomViewHolder extends RecyclerView.ViewHolder{
        private TextView mName, mAmount;
        private ImageButton mEdit, mDelete;

        public RoomViewHolder(@NonNull View itemView) {
            super(itemView);
            mName = itemView.findViewById(R.id.twName);
            mEdit = itemView.findViewById(R.id.btnEdit);
            mDelete = itemView.findViewById(R.id.btnDelete);
        }
    }
}
