package com.tubes.kouveepetshop.RecyclerAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.tubes.kouveepetshop.Fragment.AddDetailProductTransactionFragment;
import com.tubes.kouveepetshop.Model.ProductDAO;
import com.tubes.kouveepetshop.R;

import java.util.ArrayList;
import java.util.List;

public class AddDetailTPRecyclerAdapter extends RecyclerView.Adapter<AddDetailTPRecyclerAdapter.RoomViewHolder> implements Filterable {
    private String id, price, url;
    private List<ProductDAO> dataList;
    private List<ProductDAO> filteredDataList;
    private Context context;
    private AddDetailProductTransactionFragment fragment;

    public AddDetailTPRecyclerAdapter(Context context, List<ProductDAO> dataList, AddDetailProductTransactionFragment fragment) {
        this.fragment = fragment;
        this.context=context;
        this.dataList = dataList;
        this.filteredDataList = dataList;
    }

    @NonNull
    @Override
    public RoomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.recycle_adapter_add_detail_tp, parent, false);
        return new RoomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AddDetailTPRecyclerAdapter.RoomViewHolder holder, int position) {
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
                id = brg.getId_produk();
                price = brg.getHarga();

                fragment.Add(id, price);

//                Intent i = new Intent(context, DetailTransactionProductActivity.class);
//                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                context.startActivity(i);
//
//                Intent i = new Intent(context, DetailProductActivity.class);
//                i.putExtra("id",id);
//                context.startActivity(i);
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
