package com.tubes.kouveepetshop.RecyclerAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.tubes.kouveepetshop.Model.ProductDAO;
import com.tubes.kouveepetshop.R;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class ProductHomeRecyclerAdapter extends RecyclerView.Adapter<ProductHomeRecyclerAdapter.RoomViewHolder> {
    private String id, url;
    private List<ProductDAO> dataList;
    private Context context;

    Locale localeID = new Locale("in", "ID");
    NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);

    public ProductHomeRecyclerAdapter(Context context, List<ProductDAO> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public RoomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.recycle_adapter_product_home, parent, false);
        return new RoomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductHomeRecyclerAdapter.RoomViewHolder holder, int position) {
        final ProductDAO brg = dataList.get(position);
        holder.mName.setText(brg.getNama());
        holder.mUnit.setText(brg.getSatuan());
        holder.mStock.setText(brg.getStok());
        holder.mPrice.setText(formatRupiah.format((double)Double.parseDouble(brg.getHarga())));

        url = "https://kouvee.modifierisme.com/upload/"+brg.getGambar();
        Picasso.with(context).load(url).resize(300,300).centerCrop().into(holder.mImage);
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class RoomViewHolder extends RecyclerView.ViewHolder{
        private TextView mName, mUnit, mStock, mPrice;
        ImageView mImage;
        private LinearLayout mParent;

        public RoomViewHolder(@NonNull View itemView) {
            super(itemView);
            mName = itemView.findViewById(R.id.twName);
            mUnit = itemView.findViewById(R.id.twUnit);
            mStock = itemView.findViewById(R.id.twStock);
            mPrice = itemView.findViewById(R.id.twPrice);
            mImage = itemView.findViewById(R.id.imgProduct);
            mParent = itemView.findViewById(R.id.linearLayout);
        }
    }
}
