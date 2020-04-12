package com.tubes.kouveepetshop.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.tubes.kouveepetshop.Fragment.AccountCustomerFragment;
import com.tubes.kouveepetshop.Fragment.HomeFragment;
import com.tubes.kouveepetshop.Java.SessionManager;
import com.tubes.kouveepetshop.R;

import java.util.HashMap;

public class MenuCustomerActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigation;
    SessionManager sessionManager;
    private String sRole;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_customer);
        sessionManager = new SessionManager(this);

        bottomNavigation = findViewById(R.id.bottom_navigation);
        bottomNavigation.setOnNavigationItemSelectedListener(navigationItemSelectedListener);

        Fragment selectedFragment = new HomeFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();

        Toast.makeText(MenuCustomerActivity.this, "Is Login Owner : "+sessionManager.isLoggin(), Toast.LENGTH_SHORT).show();
        if(sessionManager.isLoggin())
        {
            HashMap<String, String> user = sessionManager.getUserDetail();
            sRole = user.get(sessionManager.ROLE);

            if(sRole.equalsIgnoreCase("Owner"))
            {
                Intent i = new Intent(MenuCustomerActivity.this, MenuOwnerActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
            }
            else if(sRole.equalsIgnoreCase("Customer Service"))
            {
                Intent i = new Intent(MenuCustomerActivity.this, MenuCustomerServiceActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
            }
        }
    }

    BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener =
        new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment = null;
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        selectedFragment = new HomeFragment();
                        break;
                    case R.id.navigation_account:
                        selectedFragment = new AccountCustomerFragment();
                        break;
                }

                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();

                return true;
            }
        };
}
