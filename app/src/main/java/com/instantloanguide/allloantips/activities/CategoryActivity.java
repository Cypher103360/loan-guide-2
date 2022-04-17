package com.instantloanguide.allloantips.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.instantloanguide.allloantips.R;
import com.instantloanguide.allloantips.databinding.ActivityCategoryBinding;
import com.instantloanguide.allloantips.utils.Ads;
import com.instantloanguide.allloantips.utils.ShowAds;

public class CategoryActivity extends AppCompatActivity {
    
    ActivityCategoryBinding binding;
    ShowAds showAds;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCategoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

         showAds = new ShowAds(this, binding.adViewTop, binding.adViewBottom);
        getLifecycle().addObserver(showAds);

        binding.backBtn.setOnClickListener(view -> {
            onBackPressed();
        });

        binding.personalLoanCard.setOnClickListener(view -> {
            Ads.destroyBanner();
            showAds.showInterstitialAds(this);
            Intent intent = new Intent(CategoryActivity.this,LoanAppsActivity.class);
            intent.putExtra("title","Personal Loan Apps");
            intent.putExtra("id","personalLoan");
            startActivity(intent);
        });
        binding.bankLoanCard.setOnClickListener(view -> {
            Ads.destroyBanner();
            showAds.showInterstitialAds(this);
            Intent intent = new Intent(CategoryActivity.this,LoanAppsActivity.class);
            intent.putExtra("title","Bank Loan Apps");
            intent.putExtra("id","bankLoan");
            startActivity(intent);
        });
        binding.businessLoanCard.setOnClickListener(view -> {
            Ads.destroyBanner();
            showAds.showInterstitialAds(this);
            Intent intent = new Intent(CategoryActivity.this,LoanAppsActivity.class);
            intent.putExtra("title","Business Loan Apps");
            intent.putExtra("id","businessLoan");
            startActivity(intent);
        });
        binding.homeLoanCard.setOnClickListener(view -> {
            Ads.destroyBanner();
            showAds.showInterstitialAds(this);
            Intent intent = new Intent(CategoryActivity.this,LoanAppsActivity.class);
            intent.putExtra("title","Home Loan Apps");
            intent.putExtra("id","homeLoan");
            startActivity(intent);
        });
        binding.aadharLoanCard.setOnClickListener(view -> {
            Ads.destroyBanner();
            showAds.showInterstitialAds(this);
            Intent intent = new Intent(CategoryActivity.this,LoanAppsActivity.class);
            intent.putExtra("title","Aadhar Loan Apps");
            intent.putExtra("id","aadharLoan");
            startActivity(intent);
        });
        binding.studentLoanCard.setOnClickListener(view -> {
            Ads.destroyBanner();
            showAds.showInterstitialAds(this);
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
        Ads.destroyBanner();
        finish();
    }
}