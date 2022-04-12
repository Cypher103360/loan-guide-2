package com.instantloanguide.allloantips.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.instantloanguide.allloantips.R;
import com.instantloanguide.allloantips.databinding.ActivityCheckDocumentBinding;

public class CheckDocumentActivity extends AppCompatActivity {
    ActivityCheckDocumentBinding binding;
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCheckDocumentBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        id = getIntent().getStringExtra("id");
        binding.submitDocBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CheckDocumentActivity.this,SalaryAmountActivity.class);
                intent.putExtra("id",id);
                startActivity(intent);
            }
        });
    }
}