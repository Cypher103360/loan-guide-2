package com.dreamteam.teamprediction.fordreamteamcricket.activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.text.HtmlCompat;

import com.google.android.material.button.MaterialButtonToggleGroup;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.dreamteam.teamprediction.BuildConfig;
import com.dreamteam.teamprediction.R;
import com.dreamteam.teamprediction.databinding.ActivityTipsDetailBinding;
import com.dreamteam.teamprediction.fordreamteamcricket.utils.ShowAds;
import com.ironsource.mediationsdk.IronSource;

public class TipsDetailActivity extends AppCompatActivity {
    ActivityTipsDetailBinding binding;
    String id, tipsTitle, tipsEngTitle, tipsUrl, engDesc, hinDesc;
    MaterialButtonToggleGroup materialButtonToggleGroup;
    Button hindiPreviewBtn, englishPreviewBtn;
    TextView tipsTitleTv, tipsDescTv;
    WebView tipsWebView;
    ShowAds showAds = new ShowAds();
    FirebaseAnalytics mFirebaseAnalytics;
    Bundle bundle = new Bundle();

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTipsDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        materialButtonToggleGroup = binding.materialButtonToggleGroup;
        hindiPreviewBtn = binding.tipsHindiPreview;
        englishPreviewBtn = binding.tipsEnglishPreview;
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        tipsWebView = binding.tipsData;
        tipsTitleTv = binding.tipsTitle;
        // tipsDescTv = binding.tipsDesc;
        binding.backBtn.setOnClickListener(view -> {
            onBackPressed();
        });
        getLifecycle().addObserver(showAds);
        showAds.showTopBanner(this, binding.adViewTop);
        showAds.showBottomBanner(this, binding.adViewBottom);

        id = getIntent().getStringExtra("id");
        tipsTitle = getIntent().getStringExtra("title");
        tipsEngTitle = getIntent().getStringExtra("engTitle");
        tipsUrl = getIntent().getStringExtra("url");
        hinDesc = getIntent().getStringExtra("hinDesc");
        engDesc = getIntent().getStringExtra("engDesc");

        tipsWebView.loadData(hinDesc, "text/html", "UTF-8");
        tipsWebView.setVisibility(View.VISIBLE);
        tipsTitleTv.setText(tipsTitle);
        // tipsDescTv.setText(hinDesc);
        binding.whatsappShare.setOnClickListener(v -> {
            shareData(String.valueOf(HtmlCompat.fromHtml(tipsTitle, HtmlCompat.FROM_HTML_MODE_LEGACY)));
        });
        binding.readMoreBtn.setText(R.string.read_more_hindi);

        binding.readMoreBtn.setOnClickListener(v -> {
            openWebPage(tipsUrl, TipsDetailActivity.this);
            bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "Read More");
            mFirebaseAnalytics.logEvent("Clicked_On_read_more", bundle);
        });


        hindiPreviewBtn.setBackgroundColor(ContextCompat.getColor(this, R.color.purple_700));
        hindiPreviewBtn.setTextColor(Color.BLACK);

        materialButtonToggleGroup.addOnButtonCheckedListener((group, checkedId, isChecked) -> {
            if (isChecked) {
                switch (checkedId) {
                    case R.id.tipsHindiPreview:
                        tipsTitleTv.setText(tipsTitle);
                        binding.readMoreBtn.setText(R.string.read_more_hindi);
                        binding.whatsappShare.setOnClickListener(v -> {
                            shareData(String.valueOf(HtmlCompat.fromHtml(tipsTitle, HtmlCompat.FROM_HTML_MODE_LEGACY)));
                        });
                        hindiPreviewBtn.setBackgroundColor(ContextCompat.getColor(this, R.color.purple_700));
                        hindiPreviewBtn.setTextColor(Color.BLACK);
                        englishPreviewBtn.setBackgroundColor(0);
                        englishPreviewBtn.setTextColor(Color.parseColor("#FF2B2B2B"));

                        tipsWebView.loadData(hinDesc, "text/html", "UTF-8");
                        tipsWebView.setVisibility(View.VISIBLE);
                        //   tipsDescTv.setText(hinDesc);
                        showAds.destroyBanner();
                        showAds.showTopBanner(this, binding.adViewTop);
                        showAds.showBottomBanner(this, binding.adViewBottom);

                        break;
                    case R.id.tipsEnglishPreview:
                        binding.whatsappShare.setOnClickListener(v -> {
                            shareData(String.valueOf(HtmlCompat.fromHtml(tipsEngTitle, HtmlCompat.FROM_HTML_MODE_LEGACY)));
                        });
                        tipsTitleTv.setText(tipsEngTitle);
                        binding.readMoreBtn.setText(R.string.read_more);
                        englishPreviewBtn.setBackgroundColor(ContextCompat.getColor(this, R.color.purple_700));
                        englishPreviewBtn.setTextColor(Color.BLACK);
                        hindiPreviewBtn.setBackgroundColor(0);
                        hindiPreviewBtn.setTextColor(Color.parseColor("#FF2B2B2B"));

                        tipsWebView.loadData(engDesc, "text/html", "UTF-8");
                        tipsWebView.setVisibility(View.VISIBLE);
                        //   tipsDescTv.setText(engDesc);
                        showAds.destroyBanner();
                        showAds.showTopBanner(this, binding.adViewTop);
                        showAds.showBottomBanner(this, binding.adViewBottom);

                        break;
                }
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
    public void openWebPage(String url, Context context) {
        Uri webpage = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
        if (intent.resolveActivity(context.getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    private void shareData(String title) {
        try {
            Intent i = new Intent(Intent.ACTION_SEND);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            i.setType("text/plain");
            i.setPackage("com.whatsapp");
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            String shareMessage = title + "\n\n" + "That's Awesome...\uD83D\uDC40 \n\n Install Now!☺☺ \n\n";
            shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID + "\n\n";
            i.putExtra(Intent.EXTRA_TEXT, shareMessage);
            startActivity(Intent.createChooser(i, "Share News from " + this.getString(R.string.app_name)));

        } catch (Exception e) {
            Log.e("ContentValue", e.getMessage());

            try {

                Intent i = new Intent(Intent.ACTION_SEND);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.setType("image/*");
                i.setPackage("com.whatsapp.w4b");
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                String shareMessage = title + "\n\n" + "That's Awesome...\uD83D\uDC40 \n\n Install Now!☺☺ \n\n";
                shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID + "\n\n";
                i.putExtra(Intent.EXTRA_TEXT, shareMessage);
                startActivity(Intent.createChooser(i, "Share News from " + this.getString(R.string.app_name)));

            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }

    }
}