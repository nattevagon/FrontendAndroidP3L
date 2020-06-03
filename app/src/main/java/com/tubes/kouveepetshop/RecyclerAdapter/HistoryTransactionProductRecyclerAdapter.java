package com.tubes.kouveepetshop.RecyclerAdapter;

import android.content.Context;
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

import com.tubes.kouveepetshop.Activity.HistoryTransactionProductActivity;
import com.tubes.kouveepetshop.Fragment.HistoryDetailTransactionProductFragment;
import com.tubes.kouveepetshop.Model.TransactionProductDAO;
import com.tubes.kouveepetshop.R;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class HistoryTransactionProductRecyclerAdapter extends RecyclerView.Adapter<HistoryTransactionProductRecyclerAdapter.RoomViewHolder> implements Filterable {
    private String id, code;
    private List<TransactionProductDAO> dataList;
    private List<TransactionProductDAO> filteredDataList;
    private Context context;
    private HistoryTransactionProductActivity activity;

    Locale localeID = new Locale("in", "ID");
    NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);

    public HistoryTransactionProductRecyclerAdapter(Context context, List<TransactionProductDAO> dataList, HistoryTransactionProductActivity activity) {
        this.context=context;
        this.dataList = dataList;
        this.filteredDataList = dataList;
        this.activity = activity;
    }

    @NonNull
    @Override
    public RoomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.recycle_adapter_history_transaction_product, parent, false);
        return new RoomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryTransactionProductRecyclerAdapter.RoomViewHolder holder, int position) {
        final TransactionProductDAO brg = filteredDataList.get(position);
        holder.mCode.setText(brg.getKode());
        holder.mPet.setText(brg.getHewan());
        holder.mSubTotal.setText(formatRupiah.format((double)Double.parseDouble(brg.getSub_total())));

        holder.mMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view){
                MenuBuilder menuBuilder =new MenuBuilder(context);
                MenuInflater inflater = new MenuInflater(context);
                inflater.inflate(R.menu.restore_transaction, menuBuilder);
                MenuPopupHelper optionsMenu = new MenuPopupHelper(context, menuBuilder, view);
                optionsMenu.setForceShowIcon(true);

                menuBuilder.setCallback(new MenuBuilder.Callback() {
                    @Override
                    public boolean onMenuItemSelected(MenuBuilder menu, MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.detail:
                                FragmentManager manager = ((AppCompatActivity) context).getSupportFragmentManager();
                                HistoryDetailTransactionProductFragment dialog = new HistoryDetailTransactionProductFragment(brg.getId_tp());
                                dialog.show(manager, "dialog");

                                Bundle args = new Bundle();
                                args.putString("code", brg.getKode());
                                args.putString("pet", brg.getHewan());
                                args.putString("total", brg.getSub_total());
                                dialog.setArguments(args);
                                return true;
                            case R.id.restore:
                                activity.RestoreItemList(view, brg.getId_tp(), brg.getKode());
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
        private TextView mCode, mPet, mSubTotal;
        private ImageButton mMore;

        public RoomViewHolder(@NonNull View itemView) {
            super(itemView);
            mCode = itemView.findViewById(R.id.twCode);
            mPet = itemView.findViewById(R.id.twPet);
            mSubTotal = itemView.findViewById(R.id.twSubTotal);
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
                    List<TransactionProductDAO> filteredList = new ArrayList<>();
                    for (TransactionProductDAO TransactionProductDAO : dataList) {
                        if (TransactionProductDAO.getKode().toLowerCase().contains(charSequenceString.toLowerCase()) || TransactionProductDAO.getHewan().contains(charSequenceString.toLowerCase())) {
                            filteredList.add(TransactionProductDAO);
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
                filteredDataList = (List<TransactionProductDAO>) results.values;
                notifyDataSetChanged();
            }
        };
    }
}
