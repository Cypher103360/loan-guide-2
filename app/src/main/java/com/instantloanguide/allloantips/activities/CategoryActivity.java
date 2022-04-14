package com.instantloanguide.allloantips.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.instantloanguide.allloantips.R;
import com.instantloanguide.allloantips.databinding.ActivityCategoryBinding;

public class CategoryActivity extends AppCompatActivity {
    
    ActivityCategoryBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCategoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.backBtn.setOnClickListener(view -> {
            onBackPressed();
        });

        binding.personalLoanCard.setOnClickListener(view -> {
            Intent intent = new Intent(CategoryActivity.this,LoanAppsActivity.class);
            intent.putExtra("title","Personal Loan Apps");
            intent.putExtra("id","personalLoan");
            startActivity(intent);
        });
        binding.bankLoanCard.setOnClickListener(view -> {
            Intent intent = new Intent(CategoryActivity.this,LoanAppsActivity.class);
            intent.putExtra("title","Bank Loan Apps");
            intent.putExtra("id","bankLoan");
            startActivity(intent);
        });
        binding.businessLoanCard.setOnClickListener(view -> {
            Intent intent = new Intent(CategoryActivity.this,LoanAppsActivity.class);
            intent.putExtra("title","Business Loan Apps");
            intent.putExtra("id","businessLoan");
            startActivity(intent);
        });
        binding.homeLoanCard.setOnClickListener(view -> {
            Intent intent = new Intent(CategoryActivity.this,LoanAppsActivity.class);
            intent.putExtra("title","Home Loan Apps");
            intent.putExtra("id","homeLoan");
            startActivity(intent);
        });
        binding.aadharLoanCard.setOnClickListener(view -> {
            Intent intent = new Intent(CategoryActivity.this,LoanAppsActivity.class);
            intent.putExtra("title","Aadhar Loan Apps");
            intent.putExtra("id","aadharLoan");
            startActivity(intent);
        });
        binding.studentLoanCard.setOnClickListener(view -> {
            Intent intent = new Intent(CategoryActivity.this,LoanAppsActivity.class);
            intent.putExtra("title","Student Loan Apps");
            intent.putExtra("id","studentLoan");
            startActivity(intent);
        });
//        binding.emiCalculatorCard.setOnClickListener(view -> {
//            Intent intent = new Intent();
//            intent.setAction(Intent.ACTION_MAIN);
//            intent.addCategory(Intent.CATEGORY_APP_CALCULATOR);
//            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            startActivity(intent);
//        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}