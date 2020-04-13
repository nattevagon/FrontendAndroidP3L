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
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.tubes.kouveepetshop.Activity.MenuCustomerServiceActivity;
import com.tubes.kouveepetshop.Activity.MenuOwnerActivity;
import com.tubes.kouveepetshop.Java.SessionManager;
import com.tubes.kouveepetshop.R;

import java.util.HashMap;

public class AccountCustomerServiceFragment extends Fragment {
  private String sId, sName, sRole, sBirthdate, sAddress, sPhoneNumber, sUsername;
  private TextView twName, twRole, twBirthdate, twAddress, twPhoneNumber, twUsername;
  private Button btnLogout;
  private CardView cvChangePassword;
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

    twName = view.findViewById(R.id.twName);
    twBirthdate = view.findViewById(R.id.twBirthdate);
    twRole = view.findViewById(R.id.twRole);
    btnLogout = view.findViewById(R.id.btnLogout);
    cvChangePassword = view.findViewById(R.id.cvChangePassword);

    sessionManager = new SessionManager(getContext());
    HashMap<String, String> user = sessionManager.getUserDetail();
    sId = user.get(sessionManager.ID);
    sName = user.get(sessionManager.NAME);
    sBirthdate = user.get(sessionManager.BIRTHDATE);
    sRole = user.get(sessionManager.ROLE);

    twName.setText(sName);
    twBirthdate.setText(sBirthdate);
    twRole.setText(sRole);

    cvChangePassword.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        FragmentManager manager = ((AppCompatActivity) getContext()).getSupportFragmentManager();
        ChangePasswordFragment dialog = new ChangePasswordFragment();
        dialog.show(manager, "dialog");

        Bundle args = new Bundle();
        args.putString("id", sId);
        dialog.setArguments(args);
      }
    });

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

    return inflater.inflate(R.layout.fragment_account_customer_service, container, false);
  }
}
