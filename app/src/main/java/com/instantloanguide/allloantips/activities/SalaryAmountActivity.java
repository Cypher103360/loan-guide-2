package com.instantloanguide.allloantips.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.instantloanguide.allloantips.R;
import com.instantloanguide.allloantips.databinding.ActivitySalaryAmountBinding;

public class SalaryAmountActivity extends AppCompatActivity {

    String id;
    ActivitySalaryAmountBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySalaryAmountBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.backBtn.setOnClickListener(view -> {
            onBackPressed();
        });
        id = getIntent().getStringExtra("id");
        switch (id) {
            case "salaried":
                binding.actTitle.setText("How much salary you get?");
                break;
            case "self":
                binding.actTitle.setText("Business earning per year");
                break;
            case "student":
            case "unemployed":
                binding.actTitle.setText("Earning in a month?");
                break;
        }

        binding.submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(binding.salaryEdt.getText().toString())){
                    Toast.makeText(SalaryAmountActivity.this, "Please enter your salary", Toast.LENGTH_SHORT).show();
                }else {
                    startActivity(new Intent(SalaryAmountActivity.this, PurposeForLoanActivity.class));
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}