package com.tubes.kouveepetshop.RecyclerAdapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.view.menu.MenuPopupHelper;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tubes.kouveepetshop.Activity.DetailProcurementActivity;
import com.tubes.kouveepetshop.Activity.DetailTransactionProductActivity;
import com.tubes.kouveepetshop.Fragment.AddDetailProductDetailProcurementFragment;
import com.tubes.kouveepetshop.Fragment.ControlDetailTransactionProductFragment;
import com.tubes.kouveepetshop.Fragment.EditPetCareFragment;
import com.tubes.kouveepetshop.Fragment.EditProductDetailProcurementFragment;
import com.tubes.kouveepetshop.Model.DetailProcurementDAO;
import com.tubes.kouveepetshop.Model.DetailTransactionProductDAO;
import com.tubes.kouveepetshop.R;

import java.util.List;

public class DetailProcurementRecyclerAdapter extends RecyclerView.Adapter<DetailProcurementRecyclerAdapter.RoomViewHolder>{
    private List<DetailProcurementDAO> dataList;
    private Context context;
    private DetailProcurementActivity activity;

    public DetailProcurementRecyclerAdapter(Context context, List<DetailProcurementDAO> dataList, DetailProcurementActivity activity) {
        this.context=context;
        this.dataList = dataList;
        this.activity = activity;
    }

    @NonNull
    @Override
    public RoomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.recycle_adapter_detail_procurement, parent, false);
        return new RoomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DetailProcurementRecyclerAdapter.RoomViewHolder holder, int position) {
        final DetailProcurementDAO brg = dataList.get(position);

        String number = Integer.toString(holder.getAdapterPosition()+1);
        holder.mNo.setText(number);
        holder.mName.setText(brg.getProduk());
        holder.mAmount.setText(brg.getJumlah());

        holder.mMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                MenuBuilder menuBuilder =new MenuBuilder(context);
                MenuInflater inflater = new MenuInflater(context);
                inflater.inflate(R.menu.detail_procurement, menuBuilder);
                MenuPopupHelper optionsMenu = new MenuPopupHelper(context, menuBuilder, view);
                optionsMenu.setForceShowIcon(true);

                menuBuilder.setCallback(new MenuBuilder.Callback() {
                    @Override
                    public boolean onMenuItemSelected(MenuBuilder menu, MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.edit:
                                FragmentManager manager = ((AppCompatActivity) context).getSupportFragmentManager();
                                EditProductDetailProcurementFragment dialog = new EditProductDetailProcurementFragment();
                                dialog.show(manager, "dialog");

                                Bundle args = new Bundle();
                                args.putString("id_detail_p", brg.getId_detail_p());
                                args.putString("id_procurement", brg.getId_pengadaan());
                                args.putString("id_product", brg.getId_produk());
                                args.putString("amount", brg.getJumlah());
                                dialog.setArguments(args);
                                return true;
                            case R.id.delete:
                                activity.DeleteDetailItemList(view, brg.getId_detail_p());
                                return true;
                            default:
                                return false;
                        }
                    }

                    @Override
                    public void onMenuModeChange(MenuBuilder menu) {}
                });

                optionsMenu.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return (dataList != null) ? dataList.size() : 0;
    }

    public class RoomViewHolder extends RecyclerView.ViewHolder{
        private TextView mNo, mName, mAmount;
        private ImageButton mMore;

        public RoomViewHolder(@NonNull View itemView) {
            super(itemView);
            mNo = itemView.findViewById(R.id.twNo);
            mName = itemView.findViewById(R.id.twName);
            mAmount = itemView.findViewById(R.id.twAmount);
            mMore = itemView.findViewById(R.id.btnMore);
        }
    }
}
