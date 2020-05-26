package com.tubes.kouveepetshop.RecyclerAdapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.view.menu.MenuPopupHelper;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tubes.kouveepetshop.Activity.DetailTransactionServiceActivity;
import com.tubes.kouveepetshop.Fragment.EditPetCareFragment;
import com.tubes.kouveepetshop.Fragment.SMSFragment;
import com.tubes.kouveepetshop.Model.DetailTransactionServiceDAO;
import com.tubes.kouveepetshop.R;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class DetailTransactionServiceRecyclerAdapter extends RecyclerView.Adapter<DetailTransactionServiceRecyclerAdapter.RoomViewHolder>{
    private String sIdService, sIdDetailTS, sIdTS, sName, sPrice, sTotal, sAmountDay;
    private int price;
    private List<DetailTransactionServiceDAO> dataList;
    private Context context;
    private DetailTransactionServiceActivity activity;

    Locale localeID = new Locale("in", "ID");
    NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);

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
    public void onBindViewHolder(@NonNull final DetailTransactionServiceRecyclerAdapter.RoomViewHolder holder, final int position) {
        final DetailTransactionServiceDAO brg = dataList.get(position);

        int total = Integer.parseInt(brg.getTotal());
        int jumlah = Integer.parseInt(brg.getJumlah());

        holder.mName.setText(brg.getLayanan()+" "+brg.getUkuran_hewan());

        if(brg.getLayanan().equalsIgnoreCase("Penitipan Hewan"))
        {

            holder.mSubTotalAmount.setText(formatRupiah.format((double)Double.parseDouble(Integer.toString(total/jumlah)))+" x "+brg.getJumlah()+" Hari = "+formatRupiah.format((double)Double.parseDouble(Integer.toString(total))));

        }
        else
        {
            holder.mSubTotalAmount.setText(formatRupiah.format((double)Double.parseDouble(Integer.toString(total))));
        }

        holder.mMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                if(brg.getLayanan().equalsIgnoreCase("Penitipan Hewan"))
                {
                    MenuBuilder menuBuilder =new MenuBuilder(context);
                    MenuInflater inflater = new MenuInflater(context);
                    inflater.inflate(R.menu.detail_transaction_service_1, menuBuilder);
                    MenuPopupHelper optionsMenu = new MenuPopupHelper(context, menuBuilder, view);
                    optionsMenu.setForceShowIcon(true);

                    menuBuilder.setCallback(new MenuBuilder.Callback() {
                        @Override
                        public boolean onMenuItemSelected(MenuBuilder menu, MenuItem item) {
                            switch (item.getItemId()) {
                                case R.id.edit:
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
                                    return true;
                                case R.id.delete:
                                    activity.DeleteDetailItemList(view, brg.getId_detail_tl());
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
                else if(brg.getLayanan().substring(0, 8).equalsIgnoreCase("Grooming") && !brg.getHewan().equalsIgnoreCase("Guest"))
                {
                    MenuBuilder menuBuilder =new MenuBuilder(context);
                    MenuInflater inflater = new MenuInflater(context);
                    inflater.inflate(R.menu.detail_transaction_service_2, menuBuilder);
                    MenuPopupHelper optionsMenu = new MenuPopupHelper(context, menuBuilder, view);
                    optionsMenu.setForceShowIcon(true);

                    menuBuilder.setCallback(new MenuBuilder.Callback() {
                        @Override
                        public boolean onMenuItemSelected(MenuBuilder menu, MenuItem item) {
                            switch (item.getItemId()) {
                                case R.id.delete:
                                    activity.DeleteDetailItemList(view, brg.getId_detail_tl());
                                    return true;
                                case R.id.sms:
                                    FragmentManager manager = ((AppCompatActivity) context).getSupportFragmentManager();
                                    SMSFragment SMSdialog = new SMSFragment();
                                    SMSdialog.show(manager, "dialog");

                                    Bundle args = new Bundle();
                                    args.putString("name", brg.getLayanan());
                                    args.putString("total", brg.getTotal());
                                    SMSdialog.setArguments(args);
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
                else
                {
                    MenuBuilder menuBuilder =new MenuBuilder(context);
                    MenuInflater inflater = new MenuInflater(context);
                    inflater.inflate(R.menu.detail_transaction_service_3, menuBuilder);
                    MenuPopupHelper optionsMenu = new MenuPopupHelper(context, menuBuilder, view);
                    optionsMenu.setForceShowIcon(true);

                    menuBuilder.setCallback(new MenuBuilder.Callback() {
                        @Override
                        public boolean onMenuItemSelected(MenuBuilder menu, MenuItem item) {
                            switch (item.getItemId()) {
                                case R.id.delete:
                                    activity.DeleteDetailItemList(view, brg.getId_detail_tl());
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
            }
        });
    }

    @Override
    public int getItemCount() {
        return (dataList != null) ? dataList.size() : 0;
    }

    public class RoomViewHolder extends RecyclerView.ViewHolder{
        private TextView mName, mSubTotalAmount;
        private ImageButton mEdit, mMore;

        public RoomViewHolder(@NonNull View itemView) {
            super(itemView);
            mName = itemView.findViewById(R.id.twName);
            mSubTotalAmount = itemView.findViewById(R.id.twSubTotalAmount);
            mMore = itemView.findViewById(R.id.btnMore);
        }
    }
}
