package com.instantloanguide.allloantips.activities;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.applovin.sdk.AppLovinSdk;
import com.instantloanguide.allloantips.databinding.ActivityWelcomeBinding;
import com.instantloanguide.allloantips.utils.Ads;
import com.instantloanguide.allloantips.utils.CommonMethods;
import com.instantloanguide.allloantips.utils.ShowAds;

import java.io.UnsupportedEncodingException;

public class WelcomeActivity extends AppCompatActivity {

    ActivityWelcomeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityWelcomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
//        AppLovinSdk.getInstance( this ).showMediationDebugger();
        ShowAds showAds = new ShowAds(this, binding.adViewTop, binding.adViewBottom);
        getLifecycle().addObserver(showAds);

        binding.startBtn.setOnClickListener(view -> {
            binding.lottieRunning.setVisibility(View.VISIBLE);
            new Handler().postDelayed(() -> {
                Ads.destroyBanner();
                showAds.showInterstitialAds(this);
                startActivity(new Intent(WelcomeActivity.this, HomeActivity.class));
                finish();
            }, 100);
        });

        binding.contactBtn.setOnClickListener(view -> {
            try {
                CommonMethods.whatsApp(this);
            } catch (UnsupportedEncodingException | PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
        });
        binding.shareBtn.setOnClickListener(view -> CommonMethods.shareApp(this));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}