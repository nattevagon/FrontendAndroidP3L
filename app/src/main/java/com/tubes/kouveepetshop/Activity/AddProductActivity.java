package com.tubes.kouveepetshop.Activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.content.CursorLoader;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.squareup.picasso.Picasso;
import com.tubes.kouveepetshop.API.ApiClient;
import com.tubes.kouveepetshop.API.ApiInterface;
import com.tubes.kouveepetshop.Model.FileProductDAO;
import com.tubes.kouveepetshop.Model.ProductDAO;
import com.tubes.kouveepetshop.R;
import java.io.File;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddProductActivity extends AppCompatActivity {
    private ImageButton btnBack;
    private ImageView imgProduct;
    private EditText etName, etStock, etMinimal, etPrice;
    private Spinner spUnit;
    private Button btnAdd;
    private String imagePath;
    private boolean statusPickImage = false;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        progressDialog = new ProgressDialog(this);

        etName = findViewById(R.id.etName);
        etStock = findViewById(R.id.etStock);
        etMinimal = findViewById(R.id.etMinimal);
        etPrice = findViewById(R.id.etPrice);
        spUnit = findViewById(R.id.spUnit);
        btnAdd = findViewById(R.id.btnAdd);
        imgProduct = findViewById(R.id.imgProduct);

        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        imgProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_PICK);
                i.setType("image/*");
                startActivityForResult(i,0);
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(etName.getText().toString().equalsIgnoreCase(""))
                {
                    etName.setError("Kosong!");
                    etName.requestFocus();
                }
                else if(etStock.getText().toString().equalsIgnoreCase(""))
                {
                    etStock.setError("Kosong!");
                    etStock.requestFocus();
                }
                else if(etMinimal.getText().toString().equalsIgnoreCase(""))
                {
                    etMinimal.setError("Kosong!");
                    etMinimal.requestFocus();
                }
                else if(etPrice.getText().toString().equalsIgnoreCase(""))
                {
                    etPrice.setError("Kosong!");
                    etPrice.requestFocus();
                }
                else
                {
                    progressDialog.show();
                    if(statusPickImage == false)
                    {
                        Add(null);
                    }
                    else
                    {
                        UploadImage();
                    }
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK){

            Uri imageUri = data.getData();
            imagePath = getRealPathFromUri(imageUri);
            Picasso.with(this).load(imageUri).resize(400,400).centerCrop().into(imgProduct);
            statusPickImage = true;
        }
    }

    private String getRealPathFromUri(Uri uri){
        String[] projection = {MediaStore.Images.Media.DATA};
        CursorLoader loader = new CursorLoader(getApplicationContext(), uri, projection, null,null,null);
        Cursor cursor = loader.loadInBackground();
        int column_idx = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String result = cursor.getString(column_idx);
        cursor.close();
        return result;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    private void UploadImage() {
        File file = new File(imagePath);
        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("gambar", file.getName(), requestBody);

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<FileProductDAO> call = apiService.upload(body);
        call.enqueue(new Callback<FileProductDAO>() {
            @Override
            public void onResponse(Call<FileProductDAO> call, Response<FileProductDAO> response) {
                String filename = response.body().getFileName();
                Add(filename);
            }

            @Override
            public void onFailure(Call<FileProductDAO> call, Throwable t) {
                Toast.makeText(AddProductActivity.this, "Gagal mengunggah gambar", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void Add(final String image) {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<ProductDAO> add = apiService.addProduct(etName.getText().toString(),etPrice.getText().toString()
                ,etMinimal.getText().toString(),etStock.getText().toString(),spUnit.getSelectedItem().toString(), image);

        add.enqueue(new Callback<ProductDAO>(){
            @Override
            public void onResponse(Call<ProductDAO> call, Response<ProductDAO> response) {
                Toast.makeText(AddProductActivity.this, "Success menambah data", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
                onBackPressed();            }

            @Override
            public void onFailure(Call<ProductDAO> call, Throwable t) {
                Toast.makeText(AddProductActivity.this, "Gagal menambah data", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
