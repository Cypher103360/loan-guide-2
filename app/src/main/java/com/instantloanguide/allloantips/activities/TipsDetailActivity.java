package com.instantloanguide.allloantips.activities;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.material.button.MaterialButtonToggleGroup;
import com.instantloanguide.allloantips.R;
import com.instantloanguide.allloantips.databinding.ActivityTipsDetailBinding;
import com.instantloanguide.allloantips.utils.Ads;
import com.instantloanguide.allloantips.utils.ShowAds;

public class TipsDetailActivity extends AppCompatActivity {
    ActivityTipsDetailBinding binding;
    String id, tipsTitle, engDesc, hinDesc;
    MaterialButtonToggleGroup materialButtonToggleGroup;
    Button hindiPreviewBtn, englishPreviewBtn;
    TextView tipsTitleTv, tipsDescTv;
    WebView tipsWebView;

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTipsDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        materialButtonToggleGroup = binding.materialButtonToggleGroup;
        hindiPreviewBtn = binding.tipsHindiPreview;
        englishPreviewBtn = binding.tipsEnglishPreview;
        tipsWebView = binding.tipsData;
        tipsTitleTv = binding.tipsTitle;
        // tipsDescTv = binding.tipsDesc;
        binding.backBtn.setOnClickListener(view -> {
            onBackPressed();
        });
        ShowAds showAds = new ShowAds(this, binding.adViewTop, binding.adViewBottom);
        getLifecycle().addObserver(showAds);

        id = getIntent().getStringExtra("id");
        tipsTitle = getIntent().getStringExtra("title");
        hinDesc = getIntent().getStringExtra("hinDesc");
        engDesc = getIntent().getStringExtra("engDesc");

        tipsWebView.loadData(hinDesc, "text/html", "UTF-8");
        tipsWebView.setVisibility(View.VISIBLE);
        tipsTitleTv.setText(tipsTitle);
        // tipsDescTv.setText(hinDesc);

        hindiPreviewBtn.setBackgroundColor(ContextCompat.getColor(this, R.color.purple_700));
        hindiPreviewBtn.setTextColor(Color.BLACK);

        materialButtonToggleGroup.addOnButtonCheckedListener((group, checkedId, isChecked) -> {
            if (isChecked) {
                switch (checkedId) {
                    case R.id.tipsHindiPreview:
                        hindiPreviewBtn.setBackgroundColor(ContextCompat.getColor(this, R.color.purple_700));
                        hindiPreviewBtn.setTextColor(Color.BLACK);
                        englishPreviewBtn.setBackgroundColor(0);
                        englishPreviewBtn.setTextColor(Color.parseColor("#FF2B2B2B"));

                        tipsWebView.loadData(hinDesc, "text/html", "UTF-8");
                        tipsWebView.setVisibility(View.VISIBLE);
                        //   tipsDescTv.setText(hinDesc);
                        break;
                    case R.id.tipsEnglishPreview:
                        englishPreviewBtn.setBackgroundColor(ContextCompat.getColor(this, R.color.purple_700));
                        englishPreviewBtn.setTextColor(Color.BLACK);
                        hindiPreviewBtn.setBackgroundColor(0);
                        hindiPreviewBtn.setTextColor(Color.parseColor("#FF2B2B2B"));

                        tipsWebView.loadData(engDesc, "text/html", "UTF-8");
                        tipsWebView.setVisibility(View.VISIBLE);
                        //   tipsDescTv.setText(engDesc);
                        break;
                }
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