package com.tubes.kouveepetshop.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.tubes.kouveepetshop.Activity.CustomerActivity;
import com.tubes.kouveepetshop.Activity.PetActivity;
import com.tubes.kouveepetshop.Activity.PetSizeActivity;
import com.tubes.kouveepetshop.Activity.PetTypeActivity;
import com.tubes.kouveepetshop.Activity.ProductActivity;
import com.tubes.kouveepetshop.Activity.ServiceActivity;
import com.tubes.kouveepetshop.Activity.SupplierActivity;
import com.tubes.kouveepetshop.R;

public class DataMasterCSFragment extends Fragment {
  CardView btnProducts, btnServices, btnPets, btnSuppliers, btnPetTypes, btnPetSizes, btnCustomers;

  public DataMasterCSFragment() {
    // Required empty public constructor
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    btnCustomers = view.findViewById(R.id.cvCustomers);
    btnCustomers.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Intent intent = new Intent(getActivity(), CustomerActivity.class);
        startActivity(intent);
      }
    });

    btnPets = view.findViewById(R.id.cvPet);
    btnPets.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Intent intent = new Intent(getActivity(), PetActivity.class);
        startActivity(intent);
      }
    });
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {

    return inflater.inflate(R.layout.fragment_data_master_cs, container, false);
  }

  public void onBackPressed()
  {
    FragmentManager fm = getActivity().getSupportFragmentManager();
    fm.popBackStack();
  }

}
