package com.instantloanguide.allloantips.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

import com.instantloanguide.allloantips.R;
import com.instantloanguide.allloantips.databinding.ActivityAppDetailsBinding;

public class AppDetailsActivity extends AppCompatActivity {
    ActivityAppDetailsBinding binding;
    String name,interest,amount,age,requirement,url;
    int image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAppDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.backBtn.setOnClickListener(view -> {
            onBackPressed();
        });

        image = getIntent().getIntExtra("img",0);
        name = getIntent().getStringExtra("name");
        interest = getIntent().getStringExtra("interest");
        amount = getIntent().getStringExtra("amount");
        age = getIntent().getStringExtra("age");
        requirement = getIntent().getStringExtra("requirement");
        url = getIntent().getStringExtra("url");

        binding.actTitle.setText(name);

        binding.appImage.setImageResource(image);
        binding.appName.setText(name);
        binding.appInterest.setText(interest);
        binding.appAmount.setText(amount);
        binding.appAge.setText(age);
        binding.appRequirement.setText(requirement);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}