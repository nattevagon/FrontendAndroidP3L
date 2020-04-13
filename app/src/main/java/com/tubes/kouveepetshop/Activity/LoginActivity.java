package com.tubes.kouveepetshop.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.tubes.kouveepetshop.API.ApiClient;
import com.tubes.kouveepetshop.API.ApiInterface;
import com.tubes.kouveepetshop.Java.SessionManager;
import com.tubes.kouveepetshop.Model.LoginDAO;
import com.tubes.kouveepetshop.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private ImageButton btnBack;
    private Button btnMasuk;
    private EditText etUsername, etPassword;
    private String sStatus, sId, sName, sBirthdate, sAddress, sRole, sUsername, sPassword, sPhoneNumber;
    private SessionManager sessionManager;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        progressDialog = new ProgressDialog(this);

        sessionManager = new SessionManager(this);

        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btnBack = findViewById(R.id.btnBack);
        btnMasuk = findViewById(R.id.btnMasuk);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        btnMasuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(etUsername.getText().toString().equalsIgnoreCase(""))
                {
                    etUsername.setError("Username kosong!");
                    etUsername.requestFocus();
                }
                else if(etPassword.getText().toString().equalsIgnoreCase(""))
                {
                    etPassword.setError("Password kosong!", null);
                    etPassword.requestFocus();
                }
                else {
                    progressDialog.show();
                    login();
                }
            }
        });
    }

    private void login()
    {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<LoginDAO> add = apiService.login(etUsername.getText().toString(), etPassword.getText().toString());

        add.enqueue(new Callback<LoginDAO>(){
            @Override
            public void onResponse(Call<LoginDAO> call, Response<LoginDAO> response) {
                sStatus = response.body().getStatus();

                if(sStatus.equalsIgnoreCase("true")) {
                    sId = response.body().getId_pegawai();
                    sName = response.body().getNama();
                    sBirthdate = response.body().getTgl_lahir();
                    sAddress = response.body().getAlamat();
                    sRole = response.body().getPeran();
                    sUsername = response.body().getUsername();
                    sPassword = response.body().getPassword();
                    sPhoneNumber = response.body().getNo_telp();

                    if(sRole.equalsIgnoreCase("Owner"))
                    {
                        progressDialog.dismiss();
                        sessionManager.createSession(sId, sName, sBirthdate, sAddress, sRole, sUsername, sPassword, sPhoneNumber);
                        Intent i = new Intent(LoginActivity.this, MenuOwnerActivity.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(i);

                    }
                    else if(sRole.equalsIgnoreCase("Customer Service"))
                    {
                        progressDialog.dismiss();
                        sessionManager.createSession(sId, sName, sBirthdate, sAddress, sRole, sUsername, sPassword, sPhoneNumber);
                        Intent i = new Intent(LoginActivity.this, MenuCustomerServiceActivity.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(i);
                    }
                    else{
                        progressDialog.dismiss();
                        Toast.makeText(LoginActivity.this, "Hanya pihak Customer Service dan Owner saja yang bisa login", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    progressDialog.dismiss();
                    Toast.makeText(LoginActivity.this, "Username atau password salah", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginDAO> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "Fail"+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}
