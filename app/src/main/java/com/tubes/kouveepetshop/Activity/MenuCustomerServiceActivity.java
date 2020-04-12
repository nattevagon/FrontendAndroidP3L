package com.tubes.kouveepetshop.Activity;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.tubes.kouveepetshop.API.ApiClient;
import com.tubes.kouveepetshop.API.ApiInterface;
import com.tubes.kouveepetshop.Fragment.AccountCustomerServiceFragment;
import com.tubes.kouveepetshop.Fragment.AccountOwnerFragment;
import com.tubes.kouveepetshop.Fragment.DataMasterFragment;
import com.tubes.kouveepetshop.Fragment.HomeFragment;
import com.tubes.kouveepetshop.Fragment.TransactionFragment;
import com.tubes.kouveepetshop.Java.SessionManager;
import com.tubes.kouveepetshop.Model.EmployeeDAO;
import com.tubes.kouveepetshop.R;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MenuCustomerServiceActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavigation;
    private SessionManager sessionManager;
    public static String sId, sName, sRole, sBirthdate, sAddress, sPhoneNumber, sUsername, sPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_customer_service);

        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

        sessionManager = new SessionManager(this);
        sessionManager.loginCS();

        HashMap<String, String> user = sessionManager.getUserDetail();
        sId = user.get(sessionManager.ID);
        getData();

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
                    case R.id.navigation_transaction:
                        selectedFragment = new TransactionFragment();
                        break;
                    case R.id.navigation_data_master:
                        selectedFragment = new DataMasterFragment();
                        break;
                    case R.id.navigation_account:
                        selectedFragment = new AccountCustomerServiceFragment();
                        break;
                }

                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();

                return true;
            }
        };

    private void getData()
    {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<List<EmployeeDAO>> get = apiService.getByIDEmployee(sId);

        get.enqueue(new Callback<List<EmployeeDAO>>() {
            @Override
            public void onResponse(Call<List<EmployeeDAO>> call, Response<List<EmployeeDAO>> response) {
                for(int i=0;i<response.body().size();i++)
                {
                    sName = response.body().get(i).getNama();
                    sRole = response.body().get(i).getPeran();
                    sBirthdate = response.body().get(i).getTgl_lahir();
                    sAddress = response.body().get(i).getAlamat();
                    sUsername = response.body().get(i).getUsername();
                    sPassword = response.body().get(i).getPassword();
                    sPhoneNumber = response.body().get(i).getNo_telp();
                }
            }

            @Override
            public void onFailure(Call<List<EmployeeDAO>> call, Throwable t) {

            }
        });

    }

    @Override
    public void onBackPressed()
    { }
}
