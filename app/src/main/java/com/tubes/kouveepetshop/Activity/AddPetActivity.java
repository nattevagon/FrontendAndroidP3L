package com.tubes.kouveepetshop.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.tubes.kouveepetshop.API.ApiClient;
import com.tubes.kouveepetshop.API.ApiInterface;
import com.tubes.kouveepetshop.Model.CustomerDAO;
import com.tubes.kouveepetshop.Model.PetDAO;
import com.tubes.kouveepetshop.Model.PetSizeDAO;
import com.tubes.kouveepetshop.Model.PetTypeDAO;
import com.tubes.kouveepetshop.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddPetActivity extends AppCompatActivity {
    private EditText etName, etBirthdate;
    private ImageView btnBack;
    private String sBirthdate;
    private int idPetType, idPetSize, idCustomer;
    private AutoCompleteTextView spPetType, spPetSize, spCustomer;
    private Button btnAdd;
    private DatePickerDialog.OnDateSetListener mOnDateSetListener;
    private ProgressDialog progressDialog;
    List<String> idListPetType = new ArrayList<String>();
    List<String> idListPetSize = new ArrayList<String>();
    List<String> idListCustomer = new ArrayList<String>();
    List<String> nameListPetType = new ArrayList<String>();
    List<String> nameListPetSize = new ArrayList<String>();
    List<String> nameListCustomer = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_pet);

        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        progressDialog = new ProgressDialog(this);

        etName = findViewById(R.id.etName);
        etBirthdate = findViewById(R.id.etBirthdate);
        spPetType = findViewById(R.id.spPetType);
        spPetSize = findViewById(R.id.spPetSize);
        spCustomer = findViewById(R.id.spCustomer);
        btnAdd = findViewById(R.id.btnAdd);

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
                        AddPetActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mOnDateSetListener,
                        year,month,day);
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

        loadSpinnerPetType();
        loadSpinnerPetSize();
        loadSpinnerCustomer();

        btnAdd.setOnClickListener(new View.OnClickListener() {
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
                else
                {
                    progressDialog.show();
                    Add();
                }
            }
        });
    }

    public void loadSpinnerPetType(){
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<List<PetTypeDAO>> call = apiService.getAllPetType();

        call.enqueue(new Callback<List<PetTypeDAO>>() {
            @Override
            public void onResponse(Call<List<PetTypeDAO>> call, Response<List<PetTypeDAO>> response) {
                for (int i = 0; i < response.body().size(); i++){
                    idListPetType.add(response.body().get(i).getId_jenis_hewan());
                    nameListPetType.add(response.body().get(i).getNama());
                }

                ArrayAdapter<String> adapterPetType = new ArrayAdapter<String>
                        (AddPetActivity.this, android.R.layout.simple_list_item_1, nameListPetType);
                spPetType.setAdapter(adapterPetType);

                spPetType.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View view, MotionEvent motionEvent) {
                        spPetType.showDropDown();
                        return false;
                    }
                });

                spPetType.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        idPetType = nameListPetType.indexOf(spPetType.getText().toString());
                    }
                });
            }

            @Override
            public void onFailure(Call<List<PetTypeDAO>> call, Throwable t) {
                Toast.makeText(AddPetActivity.this, "Kesalahan Jaringan", Toast.LENGTH_SHORT).show();

            }
        });
    }

    public void loadSpinnerPetSize(){
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<List<PetSizeDAO>> call = apiService.getAllPetSize();

        call.enqueue(new Callback<List<PetSizeDAO>>() {
            @Override
            public void onResponse(Call<List<PetSizeDAO>> call, Response<List<PetSizeDAO>> response) {
                for (int i = 0; i < response.body().size(); i++){
                    idListPetSize.add(response.body().get(i).getId_ukuran_hewan());
                    nameListPetSize.add(response.body().get(i).getNama());
                }

                ArrayAdapter<String> adapterPetSize = new ArrayAdapter<String>
                        (AddPetActivity.this, android.R.layout.simple_list_item_1, nameListPetSize);
                spPetSize.setAdapter(adapterPetSize);

                spPetSize.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View view, MotionEvent motionEvent) {
                        spPetSize.showDropDown();
                        return false;
                    }
                });

                spPetSize.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        idPetSize = nameListPetSize.indexOf(spPetSize.getText().toString());
                    }
                });
            }

            @Override
            public void onFailure(Call<List<PetSizeDAO>> call, Throwable t) {
                Toast.makeText(AddPetActivity.this, "Kesalahan Jaringan", Toast.LENGTH_SHORT).show();

            }
        });
    }

    public void loadSpinnerCustomer(){
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<List<CustomerDAO>> call = apiService.getAllCustomer();

        call.enqueue(new Callback<List<CustomerDAO>>() {
            @Override
            public void onResponse(Call<List<CustomerDAO>> call, Response<List<CustomerDAO>> response) {
                for (int i = 0; i < response.body().size(); i++){
                    idListCustomer.add(response.body().get(i).getId_customer());
                    nameListCustomer.add(response.body().get(i).getNama());
                }

                ArrayAdapter<String> adapterCustomer = new ArrayAdapter<String>
                        (AddPetActivity.this, android.R.layout.simple_list_item_1, nameListCustomer);
                spCustomer.setAdapter(adapterCustomer);

                spCustomer.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        idCustomer = nameListCustomer.indexOf(spCustomer.getText().toString());
                    }
                });

                Toast.makeText(AddPetActivity.this, "IDCustomer"+idListCustomer.get(idCustomer), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<List<CustomerDAO>> call, Throwable t) {
                Toast.makeText(AddPetActivity.this, "Kesalahan Jaringan", Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void Add() {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<PetDAO> add = apiService.addPet(etName.getText().toString(), idListPetType.get(idPetType),
                idListPetSize.get(idPetSize), idListCustomer.get(idCustomer), sBirthdate);

        add.enqueue(new Callback<PetDAO>(){
            @Override
            public void onResponse(Call<PetDAO> call, Response<PetDAO> response) {
                Toast.makeText(AddPetActivity.this, "Success", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
                onBackPressed();
            }

            @Override
            public void onFailure(Call<PetDAO> call, Throwable t) {
                Toast.makeText(AddPetActivity.this, "Fail", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
    }
}
