package com.instantloanguide.allloantips.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;

import com.instantloanguide.allloantips.R;
import com.instantloanguide.allloantips.databinding.ActivityAppDetailsBinding;
import com.instantloanguide.allloantips.utils.Ads;
import com.instantloanguide.allloantips.utils.ShowAds;

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

        ShowAds showAds = new ShowAds(this, binding.adViewTop, binding.adViewBottom);
        getLifecycle().addObserver(showAds);

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
        binding.downloadBtn.setOnClickListener(view -> {
            openWebPage(url);
        });
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
        Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }
}