package com.tubes.kouveepetshop.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.tubes.kouveepetshop.Activity.MenuCustomerServiceActivity;
import com.tubes.kouveepetshop.Activity.MenuOwnerActivity;
import com.tubes.kouveepetshop.Java.SessionManager;
import com.tubes.kouveepetshop.R;

public class AccountCustomerServiceFragment extends Fragment {
  private String sId, sName, sRole, sBirthdate, sAddress, sPhoneNumber, sUsername, sPassword;
  private TextView twName, twRole, twBirthdate, twAddress, twPhoneNumber, twUsername;
  private Button btnLogout;
  private SessionManager sessionManager;

  public AccountCustomerServiceFragment() {
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    sessionManager = new SessionManager(getContext());

    twName = view.findViewById(R.id.twName);
    twRole = view.findViewById(R.id.twRole);
    btnLogout = view.findViewById(R.id.btnLogout);

    sName = MenuCustomerServiceActivity.sName;
    sRole = MenuCustomerServiceActivity.sRole;

    twName.setText(sName);
    twRole.setText(sRole);

    btnLogout.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        sessionManager.logoutCS();
        Toast.makeText(getContext(),"Anda telah keluar",Toast.LENGTH_SHORT).show();
      }
    });

  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {

    return inflater.inflate(R.layout.fragment_account_owner, container, false);
  }
}
