package com.tubes.kouveepetshop.RecyclerAdapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.view.menu.MenuPopupHelper;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tubes.kouveepetshop.Activity.PDFViewerActivity;
import com.tubes.kouveepetshop.Fragment.DetailProcurementFragment;
import com.tubes.kouveepetshop.Fragment.ProcurementProcessFragment;
import com.tubes.kouveepetshop.Model.ProcurementDAO;
import com.tubes.kouveepetshop.R;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ProcurementProcessRecyclerAdapter extends RecyclerView.Adapter<ProcurementProcessRecyclerAdapter.RoomViewHolder> implements Filterable {
    private String id, code;
    private List<ProcurementDAO> dataList;
    private List<ProcurementDAO> filteredDataList;
    private Context context;
    private ProcurementProcessFragment fragment;

    Locale localeID = new Locale("in", "ID");
    NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);

    public ProcurementProcessRecyclerAdapter(Context context, List<ProcurementDAO> dataList, ProcurementProcessFragment fragment) {
        this.context=context;
        this.dataList = dataList;
        this.filteredDataList = dataList;
        this.fragment = fragment;
    }

    @NonNull
    @Override
    public RoomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.recycle_adapter_procurement_history, parent, false);
        return new RoomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProcurementProcessRecyclerAdapter.RoomViewHolder holder, int position) {
        final ProcurementDAO brg = filteredDataList.get(position);

        holder.mCode.setText(brg.getKode());
        holder.mSupplier.setText(brg.getSupplier());
        holder.mTotal.setText(formatRupiah.format((double)Double.parseDouble(brg.getTotal_harga())));

        holder.mMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view){
                MenuBuilder menuBuilder =new MenuBuilder(context);
                MenuInflater inflater = new MenuInflater(context);
                inflater.inflate(R.menu.procurement_process, menuBuilder);
                MenuPopupHelper optionsMenu = new MenuPopupHelper(context, menuBuilder, view);
                optionsMenu.setForceShowIcon(true);

                menuBuilder.setCallback(new MenuBuilder.Callback() {
                    @Override
                    public boolean onMenuItemSelected(MenuBuilder menu, MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.detail:
                                FragmentManager manager = ((AppCompatActivity) context).getSupportFragmentManager();
                                DetailProcurementFragment dialog = new DetailProcurementFragment(brg.getId_pengadaan());
                                dialog.show(manager, "dialog");

                                Bundle args = new Bundle();
                                args.putString("code", brg.getKode());
                                args.putString("supplier", brg.getSupplier());
                                args.putString("total", brg.getTotal_harga());
                                dialog.setArguments(args);
                                return true;
                            case R.id.letter:
                                Intent i = new Intent(context, PDFViewerActivity.class);
                                i.putExtra("id_procurement",brg.getId_pengadaan());
                                i.putExtra("code",brg.getKode());
                                context.startActivity(i);
                                return true;
                            case R.id.confirm:
                                fragment.DoneItemList(view, brg.getId_pengadaan(), brg.getKode());
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
        return filteredDataList.size();
    }

    public class RoomViewHolder extends RecyclerView.ViewHolder{
        private TextView mCode, mSupplier, mTotal;
        private ImageButton mMore;

        public RoomViewHolder(@NonNull View itemView) {
            super(itemView);
            mCode = itemView.findViewById(R.id.twCode);
            mSupplier = itemView.findViewById(R.id.twSupplier);
            mTotal = itemView.findViewById(R.id.twTotal);
            mMore = itemView.findViewById(R.id.btnMore);
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
                    List<ProcurementDAO> filteredList = new ArrayList<>();
                    for (ProcurementDAO ProcurementDAO : dataList) {
                        if (ProcurementDAO.getKode().toLowerCase().contains(charSequenceString.toLowerCase()) || ProcurementDAO.getSupplier().contains(charSequenceString.toLowerCase())) {
                            filteredList.add(ProcurementDAO);
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
                filteredDataList = (List<ProcurementDAO>) results.values;
                notifyDataSetChanged();
            }
        };
    }
}
