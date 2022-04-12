package com.instantloanguide.allloantips.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.instantloanguide.allloantips.R;
import com.instantloanguide.allloantips.databinding.ActivityLoanAmountBinding;

public class LoanAmountActivity extends AppCompatActivity {

    ActivityLoanAmountBinding binding;
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoanAmountBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        id = getIntent().getStringExtra("id");

        binding.submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(binding.amountEdt.getText().toString())){
                    Toast.makeText(LoanAmountActivity.this, "Please enter your amount", Toast.LENGTH_SHORT).show();
                }else {
                    Intent intent = new Intent(LoanAmountActivity.this, CheckDocumentActivity.class);
                    intent.putExtra("id", id);
                    startActivity(intent);
                }
            }
        });
    }
}