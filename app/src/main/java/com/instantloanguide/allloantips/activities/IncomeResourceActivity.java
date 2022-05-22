package com.instantloanguide.allloantips.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.instantloanguide.allloantips.databinding.ActivityIncomeResourceBinding;
import com.instantloanguide.allloantips.utils.Ads;
import com.instantloanguide.allloantips.utils.ShowAds;

public class IncomeResourceActivity extends AppCompatActivity {

    ActivityIncomeResourceBinding binding;
    ShowAds showAds = new ShowAds();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityIncomeResourceBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        showAds = new ShowAds(this, binding.adViewTop, binding.adViewBottom);
        getLifecycle().addObserver(showAds);

        binding.backBtn.setOnClickListener(view -> {
            onBackPressed();
        });
        binding.salariedCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Ads.destroyBanner();
                showAds.showInterstitialAds(IncomeResourceActivity.this);
                Intent intent = new Intent(IncomeResourceActivity.this, LoanAmountActivity.class);
                intent.putExtra("id", "salaried");
                startActivity(intent);
            }
        });

        binding.selfEmployedCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Ads.destroyBanner();
                showAds.showInterstitialAds(IncomeResourceActivity.this);
                Intent intent = new Intent(IncomeResourceActivity.this, LoanAmountActivity.class);
                intent.putExtra("id", "self");
                startActivity(intent);
            }
        });

        binding.studentCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Ads.destroyBanner();
                showAds.showInterstitialAds(IncomeResourceActivity.this);
                Intent intent = new Intent(IncomeResourceActivity.this, LoanAmountActivity.class);
                intent.putExtra("id", "student");
                startActivity(intent);
            }
        });

        binding.unemployedCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Ads.destroyBanner();
                showAds.showInterstitialAds(IncomeResourceActivity.this);
                Intent intent = new Intent(IncomeResourceActivity.this, LoanAmountActivity.class);
                intent.putExtra("id", "unemployed");
                startActivity(intent);
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Ads.destroyBanner();
        finish();
    }
}