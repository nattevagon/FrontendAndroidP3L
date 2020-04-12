package com.tubes.kouveepetshop.Fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.tubes.kouveepetshop.API.ApiClient;
import com.tubes.kouveepetshop.API.ApiInterface;
import com.tubes.kouveepetshop.Activity.EditServiceActivity;
import com.tubes.kouveepetshop.Activity.ServiceActivity;
import com.tubes.kouveepetshop.Model.ServiceDAO;
import com.tubes.kouveepetshop.R;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ServiceBottomFragment extends BottomSheetDialogFragment{
    private ImageButton btnClose;
    private Button btnEdit, btnDelete;
    private TextView twName, twPetSize, twPrice;
    private String sId, sName, sPetSize, sPrice;

    Locale localeID = new Locale("in", "ID");
    NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_service_bottom_sheet, container, false);

        twName = v.findViewById(R.id.twName);
        twPetSize = v.findViewById(R.id.twPetSize);
        twPrice = v.findViewById(R.id.twPrice);
        btnEdit = v.findViewById(R.id.btnEditSheet);
        btnDelete = v.findViewById(R.id.btnDeleteSheet);
        btnClose = v.findViewById(R.id.btnClose);

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        sId = getArguments().getString("id", "");
        getService();

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), EditServiceActivity.class);
                i.putExtra("id",sId);
                i.putExtra("name",sName);
                i.putExtra("petsize",sPetSize);
                i.putExtra("price",sPrice);
                startActivity(i);
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteService(view,sId);
            }
        });

        return v;
    }


    @Override
    public void onResume() {
        super.onResume();
        getService();
    }

    private void getService()
    {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<List<ServiceDAO>> get = apiService.getByService(sId);

        get.enqueue(new Callback<List<ServiceDAO>>() {
            @Override
            public void onResponse(Call<List<ServiceDAO>> call, Response<List<ServiceDAO>> response) {

                for(int i=0;i<response.body().size();i++)
                {
                    sName = response.body().get(i).getNama();
                    sPetSize = response.body().get(i).getUkuran_hewan();
                    sPrice = response.body().get(i).getHarga();
                }

                twName.setText(sName);
                twPetSize.setText(sPetSize);
                twPrice.setText(formatRupiah.format((double)Double.parseDouble(sPrice)));
            }

            @Override
            public void onFailure(Call<List<ServiceDAO>> call, Throwable t) {
                Toast.makeText(getContext(), "Koneksi hilang", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void deleteService(View v, final String myId) {
        AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
        builder.setMessage("Hapus data ?")
                .setCancelable(false)
                .setPositiveButton("HAPUS",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
                                Call<ServiceDAO> delete = apiService.deleteService(myId);

                                delete.enqueue(new Callback<ServiceDAO>() {
                                    @Override
                                    public void onResponse(Call<ServiceDAO> call, Response<ServiceDAO> response) {
                                        Toast.makeText(getContext(), "Berhasil dihapus", Toast.LENGTH_SHORT).show();
                                        dismiss();
                                        ServiceActivity serviceActivity = (ServiceActivity) getActivity();
                                        serviceActivity.onPostResume();
                                    }

                                    @Override
                                    public void onFailure(Call<ServiceDAO> call, Throwable t) {
                                        Toast.makeText(getContext(), "Koneksi hilang", Toast.LENGTH_SHORT).show();
                                    }
                                });

                            }
                        })
                .setNegativeButton("BATAL", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });

        builder.show();
    }
}