package com.tubes.kouveepetshop.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.tubes.kouveepetshop.API.ApiClient;
import com.tubes.kouveepetshop.API.ApiInterface;
import com.tubes.kouveepetshop.Model.PetSizeDAO;
import com.tubes.kouveepetshop.Model.ServiceDAO;
import com.tubes.kouveepetshop.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditServiceActivity extends AppCompatActivity {
    private EditText etNama, etPrice;
    private Button btnSave;
    private ImageView btnBack;
    private String sId, sName, sPetSize, sPrice;
    private int idPetSize;
    private AutoCompleteTextView spPetSize;
    List<String> idListPetSize = new ArrayList<String>();
    List<String> nameListPetSize = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_service);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

        etNama = findViewById(R.id.etNama);
        spPetSize = findViewById(R.id.spPetSize);
        etPrice = findViewById(R.id.etPrice);
        btnBack = findViewById(R.id.btnBack);
        btnSave = findViewById(R.id.btnSave);

        Intent i = getIntent();
        sId = i.getStringExtra("id");
        sName = i.getStringExtra("name");
        sPetSize = i.getStringExtra("petsize");
        sPrice = i.getStringExtra("price");

        etNama.setText(sName);
        spPetSize.setText(sPetSize);
        etPrice.setText(sPrice);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        loadSpinnerPetSize();

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(etNama.getText().toString().equalsIgnoreCase(""))
                {
                    Toast.makeText(EditServiceActivity.this, "Kosong", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Edit();
                }
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
                        (EditServiceActivity.this, android.R.layout.simple_list_item_1, nameListPetSize);
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
                Toast.makeText(EditServiceActivity.this, "Kesalahan Jaringan", Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void Edit() {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<ServiceDAO> add = apiService.updateService(sId, etNama.getText().toString(), idListPetSize.get(idPetSize), etPrice.getText().toString());

        add.enqueue(new Callback<ServiceDAO>(){
            @Override
            public void onResponse(Call<ServiceDAO> call, Response<ServiceDAO> response) {
                Toast.makeText(EditServiceActivity.this, "Success", Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void onFailure(Call<ServiceDAO> call, Throwable t) {
                Toast.makeText(EditServiceActivity.this, "Fail", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}
