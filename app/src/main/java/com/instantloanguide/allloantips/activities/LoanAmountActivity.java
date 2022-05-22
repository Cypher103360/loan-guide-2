package com.instantloanguide.allloantips.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.instantloanguide.allloantips.databinding.ActivityLoanAmountBinding;
import com.instantloanguide.allloantips.utils.Ads;
import com.instantloanguide.allloantips.utils.ShowAds;

public class LoanAmountActivity extends AppCompatActivity {

    ActivityLoanAmountBinding binding;
    String id;
    ShowAds showAds = new ShowAds();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoanAmountBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        id = getIntent().getStringExtra("id");

        binding.backBtn.setOnClickListener(view -> {
            onBackPressed();
        });

         showAds = new ShowAds(this, binding.adViewTop, binding.adViewBottom);
        getLifecycle().addObserver(showAds);

        binding.submitBtn.setOnClickListener(view -> {
            Ads.destroyBanner();
            showAds.showInterstitialAds(LoanAmountActivity.this);
            if (TextUtils.isEmpty(binding.amountEdt.getText().toString())) {
                Toast.makeText(LoanAmountActivity.this, "Please enter your amount", Toast.LENGTH_SHORT).show();
            } else {
                Intent intent = new Intent(LoanAmountActivity.this, CheckDocumentActivity.class);
                intent.putExtra("id", id);
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