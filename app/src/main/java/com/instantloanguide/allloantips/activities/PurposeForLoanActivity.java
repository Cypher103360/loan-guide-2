package com.instantloanguide.allloantips.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.instantloanguide.allloantips.R;
import com.instantloanguide.allloantips.databinding.ActivityPurposeForLoanBinding;
import com.instantloanguide.allloantips.utils.Ads;
import com.instantloanguide.allloantips.utils.ShowAds;

public class PurposeForLoanActivity extends AppCompatActivity {

    ActivityPurposeForLoanBinding binding;

    ShowAds showAds;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPurposeForLoanBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

         showAds = new ShowAds(this, binding.adViewTop, binding.adViewBottom);
        getLifecycle().addObserver(showAds);

        binding.backBtn.setOnClickListener(view -> {
            onBackPressed();
        });
        binding.medicalLoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Ads.destroyBanner();
                showAds.showInterstitialAds(PurposeForLoanActivity.this);
                Intent intent = new Intent(PurposeForLoanActivity.this,CategoryActivity.class);
                startActivity(intent);
            }
        });
        binding.homeLoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Ads.destroyBanner();
                showAds.showInterstitialAds(PurposeForLoanActivity.this);
                Intent intent = new Intent(PurposeForLoanActivity.this,CategoryActivity.class);
                startActivity(intent);
            }
        });
        binding.shoppingLoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Ads.destroyBanner();
                showAds.showInterstitialAds(PurposeForLoanActivity.this);
                Intent intent = new Intent(PurposeForLoanActivity.this,CategoryActivity.class);
                startActivity(intent);
            }
        });
        binding.personalLoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Ads.destroyBanner();
                showAds.showInterstitialAds(PurposeForLoanActivity.this);
                Intent intent = new Intent(PurposeForLoanActivity.this,CategoryActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        Ads.destroyBanner();
        super.onBackPressed();
        finish();
    }
}