package com.dreamteam.teamprediction.fordreamteamcricket.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.dreamteam.teamprediction.databinding.ActivityLoanAmountBinding;
import com.dreamteam.teamprediction.fordreamteamcricket.utils.Prevalent;
import com.dreamteam.teamprediction.fordreamteamcricket.utils.ShowAds;
import com.ironsource.mediationsdk.IronSource;

import io.paperdb.Paper;

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

        getLifecycle().addObserver(showAds);
        binding.textView15.setText(Paper.book().read(Prevalent.title));
        binding.ownTextUrl.setOnClickListener(view -> {
            openWebPage(Paper.book().read(Prevalent.url));
        });
        binding.submitBtn.setOnClickListener(view -> {
            showAds.destroyBanner();
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