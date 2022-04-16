package com.instantloanguide.allloantips.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.instantloanguide.allloantips.databinding.ActivityWelcomeBinding;
import com.instantloanguide.allloantips.utils.Ads;
import com.instantloanguide.allloantips.utils.ShowAds;

public class WelcomeActivity extends AppCompatActivity {

    ActivityWelcomeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityWelcomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ShowAds showAds = new ShowAds(this, binding.adviewTop, binding.adViewBottom);
        getLifecycle().addObserver(showAds);

        binding.startBtn.setOnClickListener(view -> {
            binding.lottieRunning.setVisibility(View.VISIBLE);
            new Handler().postDelayed(() -> {
                Ads.destroyBanner();
                showAds.showInterstitialAds(this);
                startActivity(new Intent(WelcomeActivity.this, HomeActivity.class));
            }, 4000);
        });
    }
}