package com.tubes.kouveepetshop.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.tubes.kouveepetshop.Activity.CustomerActivity;
import com.tubes.kouveepetshop.Activity.PetActivity;
import com.tubes.kouveepetshop.Activity.PetSizeActivity;
import com.tubes.kouveepetshop.Activity.PetTypeActivity;
import com.tubes.kouveepetshop.Activity.ProductActivity;
import com.tubes.kouveepetshop.Activity.ServiceActivity;
import com.tubes.kouveepetshop.Activity.SupplierActivity;
import com.tubes.kouveepetshop.R;

public class DataMasterFragment extends Fragment {
  CardView btnProducts, btnServices, btnPets, btnSuppliers, btnPetTypes, btnPetSizes, btnCustomers;

  public DataMasterFragment() {
    // Required empty public constructor
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    btnProducts = view.findViewById(R.id.cvProducts);
    btnProducts.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Intent intent = new Intent(getActivity(), ProductActivity.class);
        startActivity(intent);
      }
    });

    btnServices = view.findViewById(R.id.cvServices);
    btnServices.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Intent intent = new Intent(getActivity(), ServiceActivity.class);
        startActivity(intent);
      }
    });

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

    btnPetTypes = view.findViewById(R.id.cvPetTypes);
    btnPetTypes.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Intent intent = new Intent(getActivity(), PetTypeActivity.class);
        startActivity(intent);
      }
    });

    btnPetSizes = view.findViewById(R.id.cvPetSizes);
    btnPetSizes.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Intent intent = new Intent(getActivity(), PetSizeActivity.class);
        startActivity(intent);
      }
    });

    btnSuppliers = view.findViewById(R.id.cvSupplier);
    btnSuppliers.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Intent intent = new Intent(getActivity(), SupplierActivity.class);
        startActivity(intent);
      }
    });
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {

    return inflater.inflate(R.layout.fragment_data_master, container, false);
  }

  public void onBackPressed()
  {
    FragmentManager fm = getActivity().getSupportFragmentManager();
    fm.popBackStack();
  }

}
