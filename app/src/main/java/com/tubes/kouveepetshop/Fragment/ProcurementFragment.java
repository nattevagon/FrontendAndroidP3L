package com.tubes.kouveepetshop.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tubes.kouveepetshop.Activity.MinimumStockActivity;
import com.tubes.kouveepetshop.Activity.ProcurementActivity;
import com.tubes.kouveepetshop.R;

public class ProcurementFragment extends Fragment {
  CardView btnProcurementList, btnStockList;

  public ProcurementFragment() {
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {

    return inflater.inflate(R.layout.fragment_procurement, container, false);
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    btnProcurementList = view.findViewById(R.id.cvProcurementList);
    btnProcurementList.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Intent intent = new Intent(getActivity(), ProcurementActivity.class);
        startActivity(intent);
      }
    });

    btnStockList = view.findViewById(R.id.cvMinimumProduct);
    btnStockList.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Intent intent = new Intent(getActivity(), MinimumStockActivity.class);
        startActivity(intent);
      }
    });
  }
}
