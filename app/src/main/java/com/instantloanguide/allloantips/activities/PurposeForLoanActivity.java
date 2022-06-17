package com.instantloanguide.allloantips.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.instantloanguide.allloantips.R;
import com.instantloanguide.allloantips.databinding.ActivityPurposeForLoanBinding;
import com.instantloanguide.allloantips.utils.Ads;
import com.instantloanguide.allloantips.utils.Prevalent;
import com.instantloanguide.allloantips.utils.ShowAds;
import com.ironsource.mediationsdk.IronSource;

import io.paperdb.Paper;

public class PurposeForLoanActivity extends AppCompatActivity {

    ActivityPurposeForLoanBinding binding;
    ShowAds showAds = new ShowAds();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPurposeForLoanBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        getLifecycle().addObserver(showAds);

        binding.backBtn.setOnClickListener(view -> {
            onBackPressed();
        });
        binding.textView15.setText(Paper.book().read(Prevalent.title));
        binding.ownTextUrl.setOnClickListener(view -> {
            openWebPage(Paper.book().read(Prevalent.url));
        });
        binding.medicalLoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAds.destroyBanner();
                showAds.showInterstitialAds(PurposeForLoanActivity.this);
                Intent intent = new Intent(PurposeForLoanActivity.this,CategoryActivity.class);
                startActivity(intent);
            }
        });

        binding.homeLoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAds.destroyBanner();
                showAds.showInterstitialAds(PurposeForLoanActivity.this);
                Intent intent = new Intent(PurposeForLoanActivity.this,CategoryActivity.class);
                startActivity(intent);
            }
        });
        binding.shoppingLoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAds.destroyBanner();
                showAds.showInterstitialAds(PurposeForLoanActivity.this);
                Intent intent = new Intent(PurposeForLoanActivity.this,CategoryActivity.class);
                startActivity(intent);
            }
        });
        binding.personalLoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAds.destroyBanner();
                showAds.showInterstitialAds(PurposeForLoanActivity.this);
                Intent intent = new Intent(PurposeForLoanActivity.this,CategoryActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        showAds.destroyBanner();
        super.onBackPressed();
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();
        showAds.showTopBanner(this, binding.adViewTop);
        showAds.showBottomBanner(this, binding.adViewBottom);


    }

    @Override
    protected void onResume() {
        super.onResume();
        IronSource.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        IronSource.onPause(this);
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
