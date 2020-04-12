package com.tubes.kouveepetshop.Fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.tubes.kouveepetshop.API.ApiClient;
import com.tubes.kouveepetshop.API.ApiInterface;
import com.tubes.kouveepetshop.Activity.EditPetSizeActivity;
import com.tubes.kouveepetshop.Activity.EditPetTypeActivity;
import com.tubes.kouveepetshop.Activity.PetSizeActivity;
import com.tubes.kouveepetshop.Activity.PetTypeActivity;
import com.tubes.kouveepetshop.Model.PetSizeDAO;
import com.tubes.kouveepetshop.Model.PetTypeDAO;
import com.tubes.kouveepetshop.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PetTypeSizeBottomFragment extends BottomSheetDialogFragment{
    private ImageButton btnClose;
    private Button btnEdit, btnDelete;
    private TextView twNameTitle;
    private String menuMaster, id, name;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_pet_type_size_bottom_sheet, container, false);

        id = getArguments().getString("id", "");
        menuMaster = getArguments().getString("menu", "");
        name = getArguments().getString("name", "");

        twNameTitle = v.findViewById(R.id.twName);
        btnEdit = v.findViewById(R.id.btnEditSheet);
        btnDelete = v.findViewById(R.id.btnDeleteSheet);
        btnClose = v.findViewById(R.id.btnClose);

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        twNameTitle.setText(name);

        if(menuMaster.equalsIgnoreCase("PetSize"))
        {
            twNameTitle.setText(name);
            btnEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(getActivity(), EditPetSizeActivity.class);
                    i.putExtra("id",id);
                    i.putExtra("name",name);
                    startActivity(i);
                }
            });

            btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    deletePetSize(view,id);
                }
            });
        }
        else if(menuMaster.equalsIgnoreCase("PetType"))
        {
            twNameTitle.setText(name);
            btnEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(getActivity(), EditPetTypeActivity.class);
                    i.putExtra("id",id);
                    i.putExtra("name",name);
                    startActivity(i);
                }
            });

            btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    deletePetType(view,id);
                }
            });
        }

        return v;
    }


    @Override
    public void onResume() {
        super.onResume();
        if(menuMaster.equalsIgnoreCase("PetSize"))
        {
            getPetSize();
        }
        else if(menuMaster.equalsIgnoreCase("PetType"))
        {
            getPetType();
        }

        twNameTitle.setText(name);
    }

    private void getPetSize()
    {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<List<PetSizeDAO>> get = apiService.getByPetSize(id);

        get.enqueue(new Callback<List<PetSizeDAO>>() {
            @Override
            public void onResponse(Call<List<PetSizeDAO>> call, Response<List<PetSizeDAO>> response) {

                for(int i=0;i<response.body().size();i++)
                {
                    name = response.body().get(i).getNama();

                }

                twNameTitle.setText(name);
            }

            @Override
            public void onFailure(Call<List<PetSizeDAO>> call, Throwable t) {
                Toast.makeText(getContext(), "Koneksi hilang", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getPetType()
    {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<List<PetTypeDAO>> get = apiService.getByPetType(id);

        get.enqueue(new Callback<List<PetTypeDAO>>() {
            @Override
            public void onResponse(Call<List<PetTypeDAO>> call, Response<List<PetTypeDAO>> response) {

                for(int i=0;i<response.body().size();i++)
                {
                    name = response.body().get(i).getNama();

                }

                twNameTitle.setText(name);
            }

            @Override
            public void onFailure(Call<List<PetTypeDAO>> call, Throwable t) {
                Toast.makeText(getContext(), "Koneksi hilang", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void deletePetType(View v, final String sId) {
        AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
        builder.setMessage("Hapus data ?")
                .setCancelable(false)
                .setPositiveButton("HAPUS",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
                                Call<PetTypeDAO> delete = apiService.deletePetType(sId);

                                delete.enqueue(new Callback<PetTypeDAO>() {
                                    @Override
                                    public void onResponse(Call<PetTypeDAO> call, Response<PetTypeDAO> response) {
                                        Toast.makeText(getContext(), "Berhasil dihapus", Toast.LENGTH_SHORT).show();
                                        dismiss();
                                        PetTypeActivity petTypeActivity = (PetTypeActivity) getActivity();
                                        petTypeActivity.onPostResume();
                                    }

                                    @Override
                                    public void onFailure(Call<PetTypeDAO> call, Throwable t) {
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

    private void deletePetSize(View v, final String sId) {
        AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
        builder.setMessage("Hapus data ?")
                .setCancelable(false)
                .setPositiveButton("HAPUS",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
                                Call<PetSizeDAO> delete = apiService.deletePetSize(sId);

                                delete.enqueue(new Callback<PetSizeDAO>() {
                                    @Override
                                    public void onResponse(Call<PetSizeDAO> call, Response<PetSizeDAO> response) {
                                        Toast.makeText(getContext(), "Berhasil dihapus", Toast.LENGTH_SHORT).show();
                                        dismiss();
                                        PetSizeActivity petSizeActivity = (PetSizeActivity) getActivity();
                                        petSizeActivity.onPostResume();
                                    }

                                    @Override
                                    public void onFailure(Call<PetSizeDAO> call, Throwable t) {
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