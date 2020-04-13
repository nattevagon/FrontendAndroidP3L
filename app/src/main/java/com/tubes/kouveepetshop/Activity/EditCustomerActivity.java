package com.tubes.kouveepetshop.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.tubes.kouveepetshop.API.ApiClient;
import com.tubes.kouveepetshop.API.ApiInterface;
import com.tubes.kouveepetshop.Java.SessionManager;
import com.tubes.kouveepetshop.Model.CustomerDAO;
import com.tubes.kouveepetshop.R;

import java.util.Calendar;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditCustomerActivity extends AppCompatActivity {
    private ImageButton btnBack;
    private EditText etName, etBirthdate, etAddress, etPhoneNumber;
    private Button btnSave;
    private String sId, sName, sBirthdate, sAddress, sPhoneNumber, sYear, sMonth, sDate, sEmployee;
    private DatePickerDialog.OnDateSetListener mOnDateSetListener;
    private ProgressDialog progressDialog;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_customer);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        progressDialog = new ProgressDialog(this);

        sessionManager = new SessionManager(this);
        HashMap<String, String> user = sessionManager.getUserDetail();
        sEmployee = user.get(sessionManager.NAME);

        etName = findViewById(R.id.etName);
        etBirthdate = findViewById(R.id.etBirthdate);
        etAddress = findViewById(R.id.etAddress);
        etPhoneNumber = findViewById(R.id.etPhoneNumber);
        btnSave = findViewById(R.id.btnSave);

        Intent i = getIntent();
        sId = i.getStringExtra("id");
        sName = i.getStringExtra("name");
        sBirthdate = i.getStringExtra("birthdate");
        sAddress = i.getStringExtra("address");
        sPhoneNumber = i.getStringExtra("phonenumber");

        etName.setText(sName);
        etAddress.setText(sAddress);
        etPhoneNumber.setText(sPhoneNumber);

        sYear = sBirthdate.substring(0, 4);
        sMonth = sBirthdate.substring(5, 7);
        sDate = sBirthdate.substring(8, 10);
        etBirthdate.setText(sDate+"/"+sMonth+"/"+sYear);

        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        etBirthdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar mCalendar =  Calendar.getInstance();
                int year = mCalendar.get(Calendar.YEAR);
                int month = mCalendar.get(Calendar.MONTH);
                int day = mCalendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog mDatePickerDialog = new DatePickerDialog(
                        EditCustomerActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mOnDateSetListener,
                        year,month,day);
                mDatePickerDialog.updateDate(Integer.parseInt(sYear), Integer.parseInt(sMonth)-1, Integer.parseInt(sDate));
                mDatePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                mDatePickerDialog.show();
            }
        });

        mOnDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month+1;
                String mDate = dayOfMonth+"/"+month+"/"+year;
                sBirthdate = year+"-"+month+"/"+dayOfMonth;
                etBirthdate.setText(mDate);
            }
        };

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(etName.getText().toString().equalsIgnoreCase(""))
                {
                    etName.setError("Kosong!");
                    etName.requestFocus();
                }
                else if(etBirthdate.getText().toString().equalsIgnoreCase(""))
                {
                    etBirthdate.setError("Kosong!");
                    etBirthdate.requestFocus();
                }
                else if(etAddress.getText().toString().equalsIgnoreCase(""))
                {
                    etAddress.setError("Kosong!");
                    etAddress.requestFocus();
                }
                else if(etPhoneNumber.getText().toString().equalsIgnoreCase(""))
                {
                    etPhoneNumber.setError("Kosong!");
                    etPhoneNumber.requestFocus();
                }
                else
                {
                    progressDialog.show();
                    Edit();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    private void Edit() {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<CustomerDAO> update = apiService.updateCustomer(sId, etName.getText().toString(),sBirthdate
                ,etAddress.getText().toString(),etPhoneNumber.getText().toString(), sEmployee);

        update.enqueue(new Callback<CustomerDAO>(){
            @Override
            public void onResponse(Call<CustomerDAO> call, Response<CustomerDAO> response) {
                Toast.makeText(EditCustomerActivity.this, "Sukses mengubah data", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
                onBackPressed();
            }

            @Override
            public void onFailure(Call<CustomerDAO> call, Throwable t) {
                Toast.makeText(EditCustomerActivity.this, "Gagal mengubah data", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
                onBackPressed();
            }
        });
    }
}
