package com.tubes.kouveepetshop.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.tubes.kouveepetshop.Fragment.AccountOwnerFragment;
import com.tubes.kouveepetshop.Fragment.DataMasterFragment;
import com.tubes.kouveepetshop.Fragment.HomeFragment;
import com.tubes.kouveepetshop.Fragment.ProcurementFragment;
import com.tubes.kouveepetshop.Fragment.ReportFragment;
import com.tubes.kouveepetshop.Java.SessionManager;
import com.tubes.kouveepetshop.R;
import java.util.HashMap;

public class MenuOwnerActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavigation;
    private SessionManager sessionManager;
    public static String sId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_owner);

        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);

        sessionManager = new SessionManager(this);
        sessionManager.loginOwner();

        HashMap<String, String> user = sessionManager.getUserDetail();
        sId = user.get(sessionManager.ID);

        bottomNavigation = findViewById(R.id.bottom_navigation);
        bottomNavigation.setOnNavigationItemSelectedListener(navigationItemSelectedListener);

        Fragment selectedFragment = new HomeFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
    }

    BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment = null;
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        selectedFragment = new HomeFragment();
                        break;
                    case R.id.navigation_data_master:
                        selectedFragment = new DataMasterFragment();
                        break;
                    case R.id.navigation_procurement:
                        selectedFragment = new ProcurementFragment();
                        break;
                    case R.id.navigation_report:
                        selectedFragment = new ReportFragment();
                        break;
                    case R.id.navigation_account:
                        selectedFragment = new AccountOwnerFragment();
                        break;
                }

                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();

                return true;
            }
        };

    @Override
    public void onBackPressed()
    { }
}
