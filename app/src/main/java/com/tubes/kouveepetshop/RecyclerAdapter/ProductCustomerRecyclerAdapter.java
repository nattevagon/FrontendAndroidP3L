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

import com.squareup.picasso.Picasso;
import com.tubes.kouveepetshop.Fragment.ProductBottomFragment;
import com.tubes.kouveepetshop.Model.ProductDAO;
import com.tubes.kouveepetshop.R;

import java.util.ArrayList;
import java.util.List;

public class ProductCustomerRecyclerAdapter extends RecyclerView.Adapter<ProductCustomerRecyclerAdapter.RoomViewHolder> implements Filterable {
    private String id, url, nama;
    private List<ProductDAO> dataList;
    private List<ProductDAO> filteredDataList;
    private Context context;

    public ProductCustomerRecyclerAdapter(Context context, List<ProductDAO> dataList) {
        this.context = context;
        this.dataList = dataList;
        this.filteredDataList = dataList;
    }

    @NonNull
    @Override
    public RoomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.recycle_adapter_product_customer, parent, false);
        return new RoomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductCustomerRecyclerAdapter.RoomViewHolder holder, int position) {
        final ProductDAO brg = filteredDataList.get(position);
        holder.mName.setText(brg.getNama());
        holder.mUnit.setText(brg.getSatuan());
        holder.mStock.setText(brg.getStok());
        holder.mPrice.setText(brg.getHarga());

        url = "https://kouvee.modifierisme.com/upload/"+brg.getGambar();
        Picasso.with(context).load(url).resize(300,300).centerCrop().into(holder.mImage);

        holder.mParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                FragmentManager manager = ((AppCompatActivity) context).getSupportFragmentManager();
                ProductBottomFragment bottomSheet = new ProductBottomFragment();
                bottomSheet.show(manager, "bottomSheet");

                id = brg.getId_produk();

                Bundle args = new Bundle();
                args.putString("id", id);
                bottomSheet.setArguments(args);
            }
        });
    }

    @Override
    public int getItemCount() {
        return filteredDataList.size();
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

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String charSequenceString = constraint.toString();
                if (charSequenceString.isEmpty()) {
                    filteredDataList = dataList;
                } else {
                    List<ProductDAO> filteredList = new ArrayList<>();
                    for (ProductDAO productDAO : dataList) {
                        if (productDAO.getNama().toLowerCase().contains(charSequenceString.toLowerCase())) {
                            filteredList.add(productDAO);
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
                filteredDataList = (List<ProductDAO>) results.values;
                notifyDataSetChanged();
            }
        };
    }
}
