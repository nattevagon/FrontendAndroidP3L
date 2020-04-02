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

import com.tubes.kouveepetshop.Activity.TransactionProductActivity;
import com.tubes.kouveepetshop.Activity.TransactionServiceActivity;
import com.tubes.kouveepetshop.R;

public class TransactionFragment extends Fragment {
  CardView btnTransactionProduct, btnTransactionService;

  public TransactionFragment() {
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    return inflater.inflate(R.layout.fragment_transaction, container, false);
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    btnTransactionProduct = view.findViewById(R.id.cvTransactionProduct);
    btnTransactionProduct.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Intent intent = new Intent(getActivity(), TransactionProductActivity.class);
        startActivity(intent);
      }
    });

    btnTransactionService = view.findViewById(R.id.cvTransactionService);
    btnTransactionService.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Intent intent = new Intent(getActivity(), TransactionServiceActivity.class);
        startActivity(intent);
      }
    });
  }
}
