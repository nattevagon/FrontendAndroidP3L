package com.tubes.kouveepetshop.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.tubes.kouveepetshop.Activity.LoginActivity;
import com.tubes.kouveepetshop.Activity.TransactionProductActivity;
import com.tubes.kouveepetshop.R;

public class AccountCustomerFragment extends Fragment {
  private Button btnLogin;
  private CardView cvAboutUs;

  public AccountCustomerFragment() {
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);


    btnLogin = view.findViewById(R.id.btnLogin);
    cvAboutUs = view.findViewById(R.id.cvAboutUs);
    cvAboutUs.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        FragmentManager manager = ((AppCompatActivity) getContext()).getSupportFragmentManager();
        AboutUsFragment dialog = new AboutUsFragment();
        dialog.show(manager, "dialog");
      }
    });

    btnLogin.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        startActivity(intent);
      }
    });

  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    return inflater.inflate(R.layout.fragment_account_customer, container, false);
  }
}
