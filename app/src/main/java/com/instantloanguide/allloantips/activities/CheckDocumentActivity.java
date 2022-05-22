package com.instantloanguide.allloantips.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.instantloanguide.allloantips.databinding.ActivityCheckDocumentBinding;
import com.instantloanguide.allloantips.utils.Ads;
import com.instantloanguide.allloantips.utils.ShowAds;

public class CheckDocumentActivity extends AppCompatActivity {
    ActivityCheckDocumentBinding binding;
    String id;
    ShowAds showAds = new ShowAds();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCheckDocumentBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        id = getIntent().getStringExtra("id");

        binding.backBtn.setOnClickListener(view -> {
            onBackPressed();
        });

        showAds = new ShowAds(this, binding.adViewTop, binding.adViewBottom);
        getLifecycle().addObserver(showAds);

        binding.submitDocBtn.setOnClickListener(view -> {
            Ads.destroyBanner();
            showAds.showInterstitialAds(CheckDocumentActivity.this);
            Intent intent = new Intent(CheckDocumentActivity.this, SalaryAmountActivity.class);
            intent.putExtra("id", id);
            startActivity(intent);
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Ads.destroyBanner();
        finish();
    }
}