package com.instantloanguide.allloantips.utils;

import static android.content.ContentValues.TAG;

import android.app.Activity;
import android.os.Handler;
import android.util.Log;
import android.widget.RelativeLayout;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

import com.applovin.mediation.ads.MaxInterstitialAd;

import java.util.Objects;

import io.paperdb.Paper;

public class ShowAds implements LifecycleObserver {

    Ads ads = new Ads();
    RelativeLayout topAdview, bottomAdview;
    Activity context;

    public ShowAds(Activity activity, RelativeLayout AdViewTop, RelativeLayout AdViewBottom) {
        context = activity;
        topAdview = AdViewTop;
        bottomAdview = AdViewBottom;
    }

    public ShowAds() {

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    public void onStart() {
        showBanner(context, topAdview, bottomAdview);
        Log.d(TAG, "onStart");
    }

    public void showInterstitialAds(Activity context) {
        if ("AdmobWithMeta".equals(Paper.book().read(Prevalent.networkName))) {
            Log.d(TAG, "AdmobWithMeta");
            ads.admobInterstitialAd(context, Paper.book().read(Prevalent.interstitialAds));

        } else if ("IronSourceWithMeta".equals(Paper.book().read(Prevalent.networkName))) {
            Log.d(TAG, "IronSourceWithMeta");
            ads.ironSourceInterstitialAd(context, Paper.book().read(Prevalent.interstitialAds));


        } else if ("AppLovinWithMeta".equals(Paper.book().read(Prevalent.networkName))) {
            Log.d(TAG, "AppLovinWithMeta");
            MaxInterstitialAd maxInterstitialAd = ads.appLovinAdInterstitialAd(context, Paper.book().read(Prevalent.interstitialAds));

            new Handler().postDelayed(() -> {
                if (maxInterstitialAd.isReady()) {
                    maxInterstitialAd.showAd();
                    Log.d(TAG, "ads ready");
                } else {
                    ads.appLovinAdInterstitialAd(context, Paper.book().read(Prevalent.interstitialAds));
                }
            }, 3000);
        } else if ("Meta".equals(Paper.book().read(Prevalent.networkName))) {

            ads.metaInterstitialAd(context, Paper.book().read(Prevalent.interstitialAds));
        }

    }

    public void showBanner(Activity context, RelativeLayout topAdView, RelativeLayout bottomAdView) {

        if (Objects.requireNonNull(Paper.book().read(Prevalent.networkName)).equals("IronSourceWithMeta") || Objects.requireNonNull(Paper.book().read(Prevalent.networkName)).equals("AppLovinWithMeta")) {

            if (topAdView != null && bottomAdView != null) {
                ads.showBannerAd(context, topAdView, Objects.requireNonNull(Paper.book().read(Prevalent.networkName)), Paper.book().read(Prevalent.bannerAds));
                MyApp.showAdmobBannerAd(context, bottomAdView);
            } else if (topAdView != null) {
                ads.showBannerAd(context, topAdView, Objects.requireNonNull(Paper.book().read(Prevalent.networkName)), Paper.book().read(Prevalent.bannerAds));

            } else if (bottomAdView != null) {
                MyApp.showAdmobBannerAd(context, bottomAdView);
            }
        } else {
            if (topAdView != null && bottomAdView != null) {
                ads.showBannerAd(context, bottomAdView, Objects.requireNonNull(Paper.book().read(Prevalent.networkName)), Paper.book().read(Prevalent.bannerAds));
                ads.showBannerAd(context, topAdView, Objects.requireNonNull(Paper.book().read(Prevalent.networkName)), Paper.book().read(Prevalent.bannerAds));

            } else if (topAdView != null) {
                ads.showBannerAd(context, topAdView, Objects.requireNonNull(Paper.book().read(Prevalent.networkName)), Paper.book().read(Prevalent.bannerAds));

            } else if (bottomAdView != null) {
                ads.showBannerAd(context, bottomAdView, Objects.requireNonNull(Paper.book().read(Prevalent.networkName)), Paper.book().read(Prevalent.bannerAds));
            }
        }

    }


}