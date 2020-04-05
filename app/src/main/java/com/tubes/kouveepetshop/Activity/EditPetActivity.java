package com.tubes.kouveepetshop.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
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

public class EditPetActivity extends AppCompatActivity {
    private EditText etName, etDate;
    private ImageView btnBack;
    private String sId, sName, sBirthdate, sPetType, sPetSize, sCustomer, sYear, sMonth, sDate;
    private int idPetType, idPetSize, idCustomer;
    private AutoCompleteTextView spPetType, spPetSize, spCustomer;
    private Button btnSave;
    private DatePickerDialog.OnDateSetListener mOnDateSetListener;
    List<String> idListPetType = new ArrayList<String>();
    List<String> idListPetSize = new ArrayList<String>();
    List<String> idListCustomer = new ArrayList<String>();
    List<String> nameListPetType = new ArrayList<String>();
    List<String> nameListPetSize = new ArrayList<String>();
    List<String> nameListCustomer = new ArrayList<String>();
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_pet);

        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        progressDialog = new ProgressDialog(this);

        etName = findViewById(R.id.etName);
        etDate = findViewById(R.id.etDate);
        spPetType = findViewById(R.id.spPetType);
        spPetSize = findViewById(R.id.spPetSize);
        spCustomer = findViewById(R.id.spCustomer);
        btnSave = findViewById(R.id.btnSave);

        Intent i = getIntent();
        sId = i.getStringExtra("id");
        sName = i.getStringExtra("name");
        sBirthdate = i.getStringExtra("birthdate");
        sPetType = i.getStringExtra("pettype");
        sPetSize = i.getStringExtra("petsize");
        sCustomer = i.getStringExtra("customer");

        etName.setText(sName);
        spPetType.setText(sPetType);
        spPetSize.setText(sPetSize);
        spCustomer.setText(sCustomer);

        sYear = sBirthdate.substring(0, 4);
        sMonth = sBirthdate.substring(5, 7);
        sDate = sBirthdate.substring(8, 10);
        etDate.setText(sDate+"/"+sMonth+"/"+sYear);

        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
    });

        etDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar mCalendar =  Calendar.getInstance();
                int year = mCalendar.get(Calendar.YEAR);
                int month = mCalendar.get(Calendar.MONTH);
                int day = mCalendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog mDatePickerDialog = new DatePickerDialog(
                        EditPetActivity.this,
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
                etDate.setText(mDate);
            }
        };

        loadSpinnerPetType();
        loadSpinnerPetSize();
        loadSpinnerCustomer();

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.show();
                Edit();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
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
                        (EditPetActivity.this, android.R.layout.simple_list_item_1, nameListPetType);
                spPetType.setAdapter(adapterPetType);

                spPetType.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View view, MotionEvent motionEvent) {
                        spPetType.showDropDown();
                        return false;
                    }
                });

                idPetType = nameListPetType.indexOf(sPetType);
                spPetType.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        idPetType = nameListPetType.indexOf(spPetType.getText().toString());
                        Toast.makeText(EditPetActivity.this, "POS"+idPetType, Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onFailure(Call<List<PetTypeDAO>> call, Throwable t) {
                Toast.makeText(EditPetActivity.this, "Kesalahan Jaringan", Toast.LENGTH_SHORT).show();

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
                        (EditPetActivity.this, android.R.layout.simple_list_item_1, nameListPetSize);
                spPetSize.setAdapter(adapterPetSize);

                spPetSize.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View view, MotionEvent motionEvent) {
                        spPetSize.showDropDown();
                        return false;
                    }
                });

                idPetSize = nameListPetSize.indexOf(sPetSize);
                spPetSize.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        idPetSize = nameListPetSize.indexOf(spPetSize.getText().toString());
                    }
                });
            }

            @Override
            public void onFailure(Call<List<PetSizeDAO>> call, Throwable t) {
                Toast.makeText(EditPetActivity.this, "Kesalahan Jaringan", Toast.LENGTH_SHORT).show();

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
                        (EditPetActivity.this, android.R.layout.simple_list_item_1, nameListCustomer);
                spCustomer.setAdapter(adapterCustomer);

                idCustomer = nameListCustomer.indexOf(sCustomer);
                spCustomer.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        idCustomer = nameListCustomer.indexOf(spCustomer.getText().toString());
                    }
                });

                Toast.makeText(EditPetActivity.this, "IDCustomer"+idListCustomer.get(idCustomer), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<List<CustomerDAO>> call, Throwable t) {
                Toast.makeText(EditPetActivity.this, "Kesalahan Jaringan", Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void Edit() {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<PetDAO> update = apiService.updatePet(sId, etName.getText().toString(), idListPetType.get(idPetType),
                idListPetSize.get(idPetSize), idListCustomer.get(idCustomer), sBirthdate);

        update.enqueue(new Callback<PetDAO>(){
            @Override
            public void onResponse(Call<PetDAO> call, Response<PetDAO> response) {
                Toast.makeText(EditPetActivity.this, "Success"+sId, Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
                onBackPressed();
            }

            @Override
            public void onFailure(Call<PetDAO> call, Throwable t) {
                Toast.makeText(EditPetActivity.this, "Fail"+sId, Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
                onBackPressed();
            }
        });
    }
}
