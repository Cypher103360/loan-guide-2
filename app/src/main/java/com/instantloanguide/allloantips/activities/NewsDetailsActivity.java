package com.instantloanguide.allloantips.activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.text.HtmlCompat;

import com.bumptech.glide.Glide;
import com.google.android.material.button.MaterialButtonToggleGroup;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.instantloanguide.allloantips.BuildConfig;
import com.instantloanguide.allloantips.R;
import com.instantloanguide.allloantips.databinding.ActivityNewsDetailsBinding;
import com.instantloanguide.allloantips.models.ApiWebServices;
import com.instantloanguide.allloantips.utils.ShowAds;
import com.ironsource.mediationsdk.IronSource;

import java.io.File;
import java.io.FileOutputStream;

public class NewsDetailsActivity extends AppCompatActivity {
    ActivityNewsDetailsBinding binding;
    String id, newsTitle, newsEngTitle, newsUrl, newsImage, engDesc, hinDesc;
    MaterialButtonToggleGroup materialButtonToggleGroup;
    Button hindiPreview, englishPreview;
    TextView newsTitleTv, newsDescTv;
    ImageView newsImageView;
    ShowAds showAds = new ShowAds();
    FirebaseAnalytics mFirebaseAnalytics;
    Bundle bundle = new Bundle();

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
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        getLifecycle().addObserver(showAds);
        showAds.showTopBanner(this, binding.adViewTop);
        showAds.showBottomBanner(this, binding.adViewBottom);
        id = getIntent().getStringExtra("id");
        newsImage = getIntent().getStringExtra("image");
        newsTitle = getIntent().getStringExtra("title");
        newsEngTitle = getIntent().getStringExtra("engTitle");
        newsUrl = getIntent().getStringExtra("url");
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
        binding.whatsappShare.setOnClickListener(v -> {
            shareData(String.valueOf(HtmlCompat.fromHtml(newsTitle, HtmlCompat.FROM_HTML_MODE_LEGACY)));
        });
        hindiPreview.setBackgroundColor(ContextCompat.getColor(this, R.color.purple_700));
        hindiPreview.setTextColor(Color.BLACK);
        binding.readMoreBtn.setText(R.string.read_more_hindi);

        Glide.with(this).load("https://gedgetsworld.in/Loan_App/news_images/" + newsImage).into(binding.newsImg);


        materialButtonToggleGroup.addOnButtonCheckedListener((group, checkedId, isChecked) -> {
            if (isChecked) {
                switch (checkedId) {
                    case R.id.hindiPreview:
                        binding.whatsappShare.setOnClickListener(v -> {
                            shareData(String.valueOf(HtmlCompat.fromHtml(newsTitle, HtmlCompat.FROM_HTML_MODE_LEGACY)));
                        });
                        binding.readMoreBtn.setText(R.string.read_more_hindi);
                        newsTitleTv.setText(newsTitle);
                        binding.englishPreview.setBackgroundColor(0);
                        binding.englishPreview.setTextColor(Color.parseColor("#FF2B2B2B"));
                        binding.hindiPreview.setBackgroundColor(ContextCompat.getColor(this, R.color.purple_700));
                        binding.hindiPreview.setTextColor(Color.BLACK);
                        showAds.destroyBanner();
                        showAds.showTopBanner(this, binding.adViewTop);
                        showAds.showBottomBanner(this, binding.adViewBottom);
                        binding.newsDesc.setText(hinDesc);
                        break;
                    case R.id.englishPreview:
                        binding.whatsappShare.setOnClickListener(v -> {
                            shareData(String.valueOf(HtmlCompat.fromHtml(newsEngTitle, HtmlCompat.FROM_HTML_MODE_LEGACY)));
                        });
                        newsTitleTv.setText(newsEngTitle);
                        binding.readMoreBtn.setText(R.string.read_more);
                        binding.englishPreview.setBackgroundColor(ContextCompat.getColor(this, R.color.purple_700));
                        binding.englishPreview.setTextColor(Color.BLACK);
                        binding.hindiPreview.setBackgroundColor(0);
                        binding.hindiPreview.setTextColor(Color.parseColor("#FF2B2B2B"));
                        showAds.destroyBanner();
                        showAds.showTopBanner(this, binding.adViewTop);
                        showAds.showBottomBanner(this, binding.adViewBottom);
                        binding.newsDesc.setText(engDesc);
                        break;
                }
            }
        });


        binding.readMoreBtn.setOnClickListener(v -> {
            openWebPage(newsUrl, NewsDetailsActivity.this);
            bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "Read More");
            mFirebaseAnalytics.logEvent("Clicked_On_read_more", bundle);
        });
        binding.newsImg.setOnClickListener(v -> {
            openWebPage(newsUrl, this);
            bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, ApiWebServices.base_url + "news_images/" + newsImage);
            bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, newsTitle);
            bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "Content Image");
            mFirebaseAnalytics.logEvent("Clicked_On_content_image", bundle);

        });
    }

    @SuppressLint("QueryPermissionsNeeded")
    public void openWebPage(String url, Context context) {
        Uri webpage = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
        if (intent.resolveActivity(context.getPackageManager()) != null) {
            startActivity(intent);
        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        showAds.destroyBanner();
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

    private void shareData(String title) {
        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "Share on whatsapp");
        mFirebaseAnalytics.logEvent("Clicked_On_share_on_whatsapp", bundle);
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        File file = new File(this.getExternalCacheDir(), File.separator + "/" + "A To Z News" + ".jpeg");
        BitmapDrawable bitmapDrawable = (BitmapDrawable) binding.newsImg.getDrawable();
        Bitmap bitmap = bitmapDrawable.getBitmap();

        try {
            FileOutputStream outputStream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
            outputStream.flush();
            outputStream.close();
            Intent i = new Intent(Intent.ACTION_SEND);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            i.setType("image/*");
            i.setPackage("com.whatsapp");
            i.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            String shareMessage = title + "\n\n" + "That's Awesome...\uD83D\uDC40 \n\n Install Now!☺☺ \n\n";
            shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID + "\n\n";
            i.putExtra(Intent.EXTRA_TEXT, shareMessage);
            startActivity(Intent.createChooser(i, "Share News from " + this.getString(R.string.app_name)));

        } catch (Exception e) {
            Log.e("ContentValue", e.getMessage());

            try {
                FileOutputStream outputStream = new FileOutputStream(file);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
                outputStream.flush();
                outputStream.close();
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.setType("image/*");
                i.setPackage("com.whatsapp.w4b");
                i.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
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