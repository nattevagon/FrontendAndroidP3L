package com.tubes.kouveepetshop.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.tubes.kouveepetshop.R;

public class LoginActivity extends AppCompatActivity {

   public Button btnMasuk;
   private EditText etUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnMasuk = findViewById(R.id.btnMasuk);
        etUsername = findViewById(R.id.etUsername);

        btnMasuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String sUsername = etUsername.getText().toString();
                if(sUsername.equalsIgnoreCase("owner") || sUsername.equalsIgnoreCase(""))
                {
                    Intent i = new Intent(LoginActivity.this, MenuOwnerActivity.class);
                    startActivity(i);
                }
                else if(sUsername.equalsIgnoreCase("cs"))
                {
                    Intent i = new Intent(LoginActivity.this, MenuCustomerServiceActivity.class);
                    startActivity(i);
                }
                else if(sUsername.equalsIgnoreCase("c"))
                {
                    Intent i = new Intent(LoginActivity.this, MenuCustomerActivity.class);
                    startActivity(i);
                }
            }
        });
    }
}
