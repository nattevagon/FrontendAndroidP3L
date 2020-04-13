package com.tubes.kouveepetshop.Fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.tubes.kouveepetshop.API.ApiClient;
import com.tubes.kouveepetshop.API.ApiInterface;
import com.tubes.kouveepetshop.Activity.EditSupplierActivity;
import com.tubes.kouveepetshop.Activity.ViewCustomerActivity;
import com.tubes.kouveepetshop.Model.EmployeeDAO;
import com.tubes.kouveepetshop.Model.EmployeeDAO;
import com.tubes.kouveepetshop.Model.EmployeeDAO;
import com.tubes.kouveepetshop.Model.TransactionServiceDAO;
import com.tubes.kouveepetshop.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePasswordFragment extends DialogFragment {
    private ImageButton btnClose;
    private EditText etPastPassword, etNewPassword, etConfirmNewPassword;
    private Button btnSave;
    private String sId, sPastPassword, sNewPassword, sConfirmPassword;
    private ProgressDialog progressDialog;

    @Override
    public void onStart() {
        super.onStart();
        getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_change_password, container, false);

        progressDialog = new ProgressDialog(getContext());
        progressDialog.show();

        btnClose = v.findViewById(R.id.btnClose);
        etPastPassword = v.findViewById(R.id.etPastPassword);
        etNewPassword = v.findViewById(R.id.etNewPassword);
        etConfirmNewPassword = v.findViewById(R.id.etConfirmNewPassword);
        btnSave = v.findViewById(R.id.btnSave);

        sId = getArguments().getString("id", "");
        getData(sId);

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(etPastPassword.getText().toString().equalsIgnoreCase(""))
                {
                    etPastPassword.setError("Kosong!", null);
                    etPastPassword.requestFocus();
                }
                else if(etPastPassword.getText().toString().equalsIgnoreCase(sPastPassword))
                {
                    if(etNewPassword.getText().toString().equalsIgnoreCase(""))
                    {
                        etNewPassword.setError("Kosong!", null);
                        etNewPassword.requestFocus();
                    }
                    else if(etNewPassword.getText().toString().length() < 5)
                    {
                        etNewPassword.setError("Kurang dari 5!", null);
                        etNewPassword.requestFocus();
                    }
                    else if(!etConfirmNewPassword.getText().toString().equalsIgnoreCase(etNewPassword.getText().toString()))
                    {
                        etConfirmNewPassword.setError("Konfirmasi password salah!", null);
                        etConfirmNewPassword.requestFocus();
                    }
                    else
                    {
                        progressDialog.show();
                        Update();
                    }
                }
                else
                {
                    etPastPassword.setError("Salah!", null);
                    etPastPassword.requestFocus();
                }

            }
        });

        return v;
    }

    private void getData(String id)
    {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<List<EmployeeDAO>> get = apiService.getByIDEmployee(id);

        get.enqueue(new Callback<List<EmployeeDAO>>() {
            @Override
            public void onResponse(Call<List<EmployeeDAO>> call, Response<List<EmployeeDAO>> response) {

                for(int i=0;i<response.body().size();i++)
                {
                    sPastPassword = response.body().get(i).getPassword();
                }
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<List<EmployeeDAO>> call, Throwable t) {
                Toast.makeText(getContext(), "Koneksi hilang", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void Update() {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<EmployeeDAO> update = apiService.changePassword(sId, etNewPassword.getText().toString());

        update.enqueue(new Callback<EmployeeDAO>(){
            @Override
            public void onResponse(Call<EmployeeDAO> call, Response<EmployeeDAO> response) {
                Toast.makeText(getContext(), "Sukses mengubah password", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
                dismiss();
            }

            @Override
            public void onFailure(Call<EmployeeDAO> call, Throwable t) {
                Toast.makeText(getContext(), "Gagal mengubah password", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
                dismiss();
            }
        });
    }
}