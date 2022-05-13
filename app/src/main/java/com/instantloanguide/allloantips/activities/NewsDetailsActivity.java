package com.instantloanguide.allloantips.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.button.MaterialButtonToggleGroup;
import com.instantloanguide.allloantips.R;
import com.instantloanguide.allloantips.databinding.ActivityNewsDetailsBinding;
import com.instantloanguide.allloantips.utils.Ads;
import com.instantloanguide.allloantips.utils.ShowAds;

public class NewsDetailsActivity extends AppCompatActivity {
    ActivityNewsDetailsBinding binding;
    String id,newsTitle,newsImage,engDesc,hinDesc;
    MaterialButtonToggleGroup materialButtonToggleGroup;
    Button hindiPreview,englishPreview;
    TextView newsTitleTv,newsDescTv;
    ImageView newsImageView;
    ShowAds showAds;
    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNewsDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        materialButtonToggleGroup = binding.materialButtonToggleGroup;
        hindiPreview = binding.hindiPreview;
        englishPreview = binding.englishPreview;
        newsTitleTv = binding.newsTitle;
        newsDescTv = binding.newsDesc;
        newsImageView = binding.newsImg;

         showAds = new ShowAds(this, binding.adViewTop, binding.adViewBottom);
        getLifecycle().addObserver(showAds);

        id = getIntent().getStringExtra("id");
        newsImage = getIntent().getStringExtra("image");
        newsTitle = getIntent().getStringExtra("title");
        engDesc = getIntent().getStringExtra("engDesc");
        hinDesc = getIntent().getStringExtra("hinDesc");

        binding.backBtn.setOnClickListener(view -> {
            onBackPressed();
        });
        newsImageView.setVisibility(View.VISIBLE);
        newsTitleTv.setVisibility(View.VISIBLE);
        newsDescTv.setVisibility(View.VISIBLE);
        newsTitleTv.setText(newsTitle);
        newsDescTv.setText(hinDesc);

        hindiPreview.setBackgroundColor(ContextCompat.getColor(this,R.color.purple_700));
        hindiPreview.setTextColor(Color.BLACK);

        Glide.with(this).load("https://gedgetsworld.in/Loan_App/news_images/"+newsImage).into(binding.newsImg);


        materialButtonToggleGroup.addOnButtonCheckedListener((group, checkedId, isChecked) -> {
            if (isChecked){
                switch (checkedId){
                    case R.id.hindiPreview:
                        binding.englishPreview.setBackgroundColor(0);
                        binding.englishPreview.setTextColor(Color.parseColor("#FF2B2B2B"));
                        binding.hindiPreview.setBackgroundColor(ContextCompat.getColor(this,R.color.purple_700));
                        binding.hindiPreview.setTextColor(Color.BLACK);
                         showAds = new ShowAds(this, binding.adViewTop, binding.adViewBottom);
                        getLifecycle().addObserver(showAds);
                        binding.newsDesc.setText(hinDesc);
                        break;
                    case R.id.englishPreview:
                        binding.englishPreview.setBackgroundColor(ContextCompat.getColor(this,R.color.purple_700));
                        binding.englishPreview.setTextColor(Color.BLACK);
                        binding.hindiPreview.setBackgroundColor(0);
                        binding.hindiPreview.setTextColor(Color.parseColor("#FF2B2B2B"));
                         showAds = new ShowAds(this, binding.adViewTop, binding.adViewBottom);
                        getLifecycle().addObserver(showAds);

                        binding.newsDesc.setText(engDesc);
                        break;
                }
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        Ads.destroyBanner();
    }
}