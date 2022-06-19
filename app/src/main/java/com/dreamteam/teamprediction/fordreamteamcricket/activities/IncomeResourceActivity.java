package com.dreamteam.teamprediction.fordreamteamcricket.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.dreamteam.teamprediction.databinding.ActivityIncomeResourceBinding;
import com.dreamteam.teamprediction.fordreamteamcricket.utils.Prevalent;
import com.dreamteam.teamprediction.fordreamteamcricket.utils.ShowAds;
import com.ironsource.mediationsdk.IronSource;

import io.paperdb.Paper;

public class IncomeResourceActivity extends AppCompatActivity {

    ActivityIncomeResourceBinding binding;
    ShowAds showAds = new ShowAds();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityIncomeResourceBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getLifecycle().addObserver(showAds);

        binding.backBtn.setOnClickListener(view -> {
            onBackPressed();
        });
        binding.textView15.setText(Paper.book().read(Prevalent.title));
        binding.ownTextUrl.setOnClickListener(view -> {
            openWebPage(Paper.book().read(Prevalent.url));
        });
        binding.salariedCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAds.destroyBanner();
                showAds.showInterstitialAds(IncomeResourceActivity.this);
                Intent intent = new Intent(IncomeResourceActivity.this, LoanAmountActivity.class);
                intent.putExtra("id", "salaried");
                startActivity(intent);
            }
        });

        binding.selfEmployedCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAds.destroyBanner();
                showAds.showInterstitialAds(IncomeResourceActivity.this);
                Intent intent = new Intent(IncomeResourceActivity.this, LoanAmountActivity.class);
                intent.putExtra("id", "self");
                startActivity(intent);
            }
        });

        binding.studentCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAds.destroyBanner();
                showAds.showInterstitialAds(IncomeResourceActivity.this);
                Intent intent = new Intent(IncomeResourceActivity.this, LoanAmountActivity.class);
                intent.putExtra("id", "student");
                startActivity(intent);
            }
        });

        binding.unemployedCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAds.destroyBanner();
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
        showAds.destroyBanner();
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