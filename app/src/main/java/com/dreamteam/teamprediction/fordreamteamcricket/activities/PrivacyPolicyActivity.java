package com.dreamteam.teamprediction.fordreamteamcricket.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebView;

import com.dreamteam.teamprediction.R;

public class PrivacyPolicyActivity extends AppCompatActivity {
    WebView webView;
    String key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy_policy);
        webView = findViewById(R.id.policy);
        key = getIntent().getStringExtra("key");
        if (key.equals("terms")){
            webView.loadUrl("file:///android_asset/terms_conditions.html");
        }else if (key.equals("policy")){
            webView.loadUrl("file:///android_asset/privacy.html");
        }

    }
}