package com.instantloanguide.allloantips.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.instantloanguide.allloantips.R;
import com.instantloanguide.allloantips.databinding.ActivityPurposeForLoanBinding;

public class PurposeForLoanActivity extends AppCompatActivity {

    ActivityPurposeForLoanBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPurposeForLoanBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.medicalLoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PurposeForLoanActivity.this,CategoryActivity.class);
                startActivity(intent);
            }
        });
        binding.homeLoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PurposeForLoanActivity.this,CategoryActivity.class);
                startActivity(intent);
            }
        });
        binding.shoppingLoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PurposeForLoanActivity.this,CategoryActivity.class);
                startActivity(intent);
            }
        });
        binding.personalLoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PurposeForLoanActivity.this,CategoryActivity.class);
                startActivity(intent);
            }
        });
    }
}