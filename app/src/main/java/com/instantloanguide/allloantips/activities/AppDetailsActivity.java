package com.instantloanguide.allloantips.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.instantloanguide.allloantips.databinding.ActivityAppDetailsBinding;
import com.instantloanguide.allloantips.utils.Ads;
import com.instantloanguide.allloantips.utils.ShowAds;

public class AppDetailsActivity extends AppCompatActivity {
    ActivityAppDetailsBinding binding;
    String name, interest, amount, age, requirement, url,image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAppDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.backBtn.setOnClickListener(view -> {
            onBackPressed();
        });

        ShowAds showAds = new ShowAds(this, binding.adViewTop, binding.adViewBottom);
        getLifecycle().addObserver(showAds);

        image = getIntent().getStringExtra("img");
        name = getIntent().getStringExtra("name");
        interest = getIntent().getStringExtra("interest");
        amount = getIntent().getStringExtra("amount");
        age = getIntent().getStringExtra("age");
        requirement = getIntent().getStringExtra("requirement");
        url = getIntent().getStringExtra("url");

        binding.actTitle.setText(name);

        Glide.with(AppDetailsActivity.this).load(
                "https://gedgetsworld.in/Loan_App/loan_app_images/"+image).into(binding.appImage);
        binding.appName.setText(name);
        binding.appInterest.setText(interest);
        binding.appAmount.setText(amount);
        binding.appAge.setText(age);
        binding.appRequirement.setText(requirement);
        binding.applyNowBtn.setOnClickListener(view -> {
            openWebPage(url);
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Ads.destroyBanner();
        finish();
    }

    @SuppressLint("QueryPermissionsNeeded")
    public void openWebPage(String url) {
        Uri webpage = Uri.parse(url);
        Ads.destroyBanner();
        Intent intent = new Intent(Intent.ACTION_VIEW, webpage
        );
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }
}