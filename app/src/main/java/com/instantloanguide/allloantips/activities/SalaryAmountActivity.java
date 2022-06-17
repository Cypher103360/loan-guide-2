package com.instantloanguide.allloantips.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.instantloanguide.allloantips.databinding.ActivitySalaryAmountBinding;
import com.instantloanguide.allloantips.utils.Prevalent;
import com.instantloanguide.allloantips.utils.ShowAds;
import com.ironsource.mediationsdk.IronSource;

import io.paperdb.Paper;

public class SalaryAmountActivity extends AppCompatActivity {
    String id;
    ActivitySalaryAmountBinding binding;
    ShowAds showAds = new ShowAds();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySalaryAmountBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        getLifecycle().addObserver(showAds);

        binding.backBtn.setOnClickListener(view -> {
            onBackPressed();
        });

        binding.textView15.setText(Paper.book().read(Prevalent.title));
        binding.ownTextUrl.setOnClickListener(view -> {
            openWebPage(Paper.book().read(Prevalent.url));
        });
        id = getIntent().getStringExtra("id");
        switch (id) {
            case "salaried":
                binding.actTitle.setText("How much salary you get?");
                break;
            case "self":
                binding.actTitle.setText("Business earning per year");
                break;
            case "student":
            case "unemployed":
                binding.actTitle.setText("Earning in a month?");
                break;
        }

        binding.submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAds.destroyBanner();
                showAds.showInterstitialAds(SalaryAmountActivity.this);

                if (TextUtils.isEmpty(binding.salaryEdt.getText().toString())) {
                    Toast.makeText(SalaryAmountActivity.this, "Please enter your salary", Toast.LENGTH_SHORT).show();
                } else {
                    startActivity(new Intent(SalaryAmountActivity.this, PurposeForLoanActivity.class));
                }
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