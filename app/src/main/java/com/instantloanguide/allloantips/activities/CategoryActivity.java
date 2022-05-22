package com.instantloanguide.allloantips.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import com.instantloanguide.allloantips.R;
import com.instantloanguide.allloantips.databinding.ActivityCategoryBinding;
import com.instantloanguide.allloantips.models.ApiInterface;
import com.instantloanguide.allloantips.models.ApiWebServices;
import com.instantloanguide.allloantips.models.UrlModel;
import com.instantloanguide.allloantips.models.UrlModelList;
import com.instantloanguide.allloantips.utils.Ads;
import com.instantloanguide.allloantips.utils.ShowAds;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryActivity extends AppCompatActivity {
    
    ActivityCategoryBinding binding;
    ShowAds showAds = new ShowAds();
    String emiCalUrl;
    ApiInterface apiInterface;
    Map<String,String> map = new HashMap<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCategoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        apiInterface = ApiWebServices.getApiInterface();
         showAds = new ShowAds(this, binding.adViewTop, binding.adViewBottom);
        getLifecycle().addObserver(showAds);

        map.put("title", "emi_cal");
        fetchEmiCalUrl();
        binding.backBtn.setOnClickListener(view -> {
            onBackPressed();
        });

        binding.personalLoanCard.setOnClickListener(view -> {
            Ads.destroyBanner();
            showAds.showInterstitialAds(this);
            Intent intent = new Intent(CategoryActivity.this,LoanAppsActivity.class);
            intent.putExtra("title","Personal Loan Apps");
            intent.putExtra("key","personal_loan");
            startActivity(intent);
        });
        binding.bankLoanCard.setOnClickListener(view -> {
            Ads.destroyBanner();
            showAds.showInterstitialAds(this);
            Intent intent = new Intent(CategoryActivity.this,LoanAppsActivity.class);
            intent.putExtra("title","Bank Loan Apps");
            intent.putExtra("key","bank_loan");
            startActivity(intent);
        });
        binding.businessLoanCard.setOnClickListener(view -> {
            Ads.destroyBanner();
            showAds.showInterstitialAds(this);
            Intent intent = new Intent(CategoryActivity.this,LoanAppsActivity.class);
            intent.putExtra("title","Business Loan Apps");
            intent.putExtra("key","business_loan");
            startActivity(intent);
        });
        binding.homeLoanCard.setOnClickListener(view -> {
            Ads.destroyBanner();
            showAds.showInterstitialAds(this);
            Intent intent = new Intent(CategoryActivity.this,LoanAppsActivity.class);
            intent.putExtra("title","Home Loan Apps");
            intent.putExtra("key","home_loan");
            startActivity(intent);
        });
        binding.aadharLoanCard.setOnClickListener(view -> {
            Ads.destroyBanner();
            showAds.showInterstitialAds(this);
            Intent intent = new Intent(CategoryActivity.this,LoanAppsActivity.class);
            intent.putExtra("title","Aadhar Loan Apps");
            intent.putExtra("key","aadhar_loan");
            startActivity(intent);
        });
        binding.studentLoanCard.setOnClickListener(view -> {
            Ads.destroyBanner();
            showAds.showInterstitialAds(this);
            Intent intent = new Intent(CategoryActivity.this,LoanAppsActivity.class);
            intent.putExtra("title","Student Loan Apps");
            intent.putExtra("key","student_loan");
            startActivity(intent);
        });
        binding.emiCalculatorCard.setOnClickListener(view -> {
            openWebPage(emiCalUrl);
        });
    }

    private void fetchEmiCalUrl() {
        Call<UrlModelList> call = apiInterface.fetchUrls(map);
        call.enqueue(new Callback<UrlModelList>() {
            @Override
            public void onResponse(@NonNull Call<UrlModelList> call, @NonNull Response<UrlModelList> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    if (response.body().getData() != null) {
                        for (UrlModel urlModel : response.body().getData()) {
                            emiCalUrl = urlModel.getUrl();
                        }
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<UrlModelList> call, @NonNull Throwable t) {

            }
        });
    }

    @SuppressLint("QueryPermissionsNeeded")
    public void openWebPage(String url) {
        Uri webpage = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Ads.destroyBanner();
        finish();
    }
}