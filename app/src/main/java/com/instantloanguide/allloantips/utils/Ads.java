package com.instantloanguide.allloantips.utils;

import static android.content.ContentValues.TAG;

import android.app.Activity;
import android.graphics.Color;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleObserver;

import com.applovin.mediation.MaxAd;
import com.applovin.mediation.MaxAdViewAdListener;
import com.applovin.mediation.MaxError;
import com.applovin.mediation.ads.MaxAdView;
import com.applovin.mediation.ads.MaxInterstitialAd;
import com.applovin.sdk.AppLovinSdk;
import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AudienceNetworkAds;
import com.facebook.ads.InterstitialAdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.instantloanguide.allloantips.R;
import com.ironsource.mediationsdk.ISBannerSize;
import com.ironsource.mediationsdk.IronSource;
import com.ironsource.mediationsdk.IronSourceBannerLayout;
import com.ironsource.mediationsdk.logger.IronSourceError;
import com.ironsource.mediationsdk.sdk.BannerListener;
import com.ironsource.mediationsdk.sdk.InterstitialListener;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

import io.paperdb.Paper;

public class Ads implements MaxAdViewAdListener, LifecycleObserver {


    public static IronSourceBannerLayout banner;
    public static InterstitialAd mInterstitialAd;
    static boolean checkAdLoad = false;
    MaxAdView maxAdView;
    MaxInterstitialAd interstitialAd;
    private int retryAttempt;


    public static void destroyBanner() {
        if (Objects.equals(Paper.book().read(Prevalent.networkName), "IronSourceWithMeta")) {
            IronSource.destroyBanner(banner);
            Log.d("destroy", "banner");
        }
    }


    public void showBannerAd(Activity context, RelativeLayout container, String networkName, String bannerAdId) {

        switch (networkName) {
            case "AdmobWithMeta":
                MobileAds.initialize(context);
                AudienceNetworkAds.initialize(context);
                AdRequest adRequest = new AdRequest.Builder().build();
                AdView adView = new AdView(context);
                container.addView(adView);
                adView.setAdUnitId(bannerAdId);
                adView.setAdSize(AdSize.BANNER);
                adView.loadAd(adRequest);
                container.setVisibility(View.VISIBLE);

                break;
            case "IronSourceWithMeta":
                IronSource.getAdvertiserId(context);
                IronSource.shouldTrackNetworkState(context, true);
                AudienceNetworkAds.initialize(context);
                IronSource.init(context, bannerAdId);
                IronSource.setMetaData("Facebook_IS_CacheFlag", "IMAGE");
                banner = IronSource.createBanner(context, ISBannerSize.BANNER);
                container.addView(banner);

                if (banner != null) {
                    // set the banner listener
                    banner.setBannerListener(new BannerListener() {
                        @Override
                        public void onBannerAdLoaded() {
                            Log.d(TAG, "onBannerAdLoaded");
                            container.setVisibility(View.VISIBLE);
                            // since banner container was "gone" by default, we need to make it visible as soon as the banner is ready
                        }

                        @Override
                        public void onBannerAdLoadFailed(IronSourceError error) {
                            Log.d(TAG, "onBannerAdLoadFailed" + " " + error);
                            context.runOnUiThread(container::removeAllViews);
                        }

                        @Override
                        public void onBannerAdClicked() {
                            Log.d(TAG, "onBannerAdClicked");
                        }

                        @Override
                        public void onBannerAdScreenPresented() {
                            Log.d(TAG, "onBannerAdScreenPresented");
                        }

                        @Override
                        public void onBannerAdScreenDismissed() {
                            Log.d(TAG, "onBannerAdScreenDismissed");
                        }

                        @Override
                        public void onBannerAdLeftApplication() {
                            Log.d(TAG, "onBannerAdLeftApplication");
                        }
                    });

                    // load ad into the created banner
                    IronSource.loadBanner(banner);
                } else {
                    Toast.makeText(context, "IronSource.createBanner returned null", Toast.LENGTH_LONG).show();
                }

                break;
            case "AppLovinWithMeta":
                AppLovinSdk.getInstance(context).setMediationProvider("max");
                AppLovinSdk.initializeSdk(context);
                AudienceNetworkAds.initialize(context);

                maxAdView = new MaxAdView(bannerAdId, context);
                maxAdView.setListener(this);
                int width = ViewGroup.LayoutParams.MATCH_PARENT;
                int height = context.getResources().getDimensionPixelSize(R.dimen.banner_height);
                maxAdView.setLayoutParams(new FrameLayout.LayoutParams(width, height));
                maxAdView.setBackgroundColor(Color.WHITE);
                container.addView(maxAdView);
                maxAdView.loadAd();
                //To show a banner, make the following calls:
                maxAdView.startAutoRefresh();

                break;

            case "Meta":
                AudienceNetworkAds.initialize(context);
                com.facebook.ads.AdView adView1 = new com.facebook.ads.AdView(context, bannerAdId, com.facebook.ads.AdSize.BANNER_HEIGHT_50);
                container.addView(adView1);
                adView1.loadAd();
                break;
        }
    }


    public void admobInterstitialAd(Activity context, String interstitialId) {
        MobileAds.initialize(context);
        AudienceNetworkAds.initialize(context);
        // Initialize an InterstitialAd.
        AdRequest adRequest = new AdRequest.Builder().build();

        InterstitialAd.load(context, interstitialId, adRequest,
                new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                        // The mInterstitialAd reference will be null until
                        // an ad is loaded.
                        mInterstitialAd = interstitialAd;
                        mInterstitialAd.show(context);
                        Log.i(TAG, "onAdLoaded");
                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        // Handle the error
                        Log.i(TAG, loadAdError.getMessage());
                        mInterstitialAd = null;
                    }
                });

    }

    public MaxInterstitialAd appLovinAdInterstitialAd(Activity context, String interstitialId) {
        AppLovinSdk.getInstance(context).setMediationProvider("max");
        AppLovinSdk.initializeSdk(context);
        AudienceNetworkAds.initialize(context);


        interstitialAd = new MaxInterstitialAd(interstitialId, context);
        interstitialAd.setListener(this);
        // Load the first ad
        interstitialAd.loadAd();
        if (interstitialAd.isReady()) {
            interstitialAd.showAd();
            Toast.makeText(context, "ads  ready", Toast.LENGTH_SHORT).show();

        }
        return interstitialAd;
    }

    public void ironSourceInterstitialAd(Activity context, String interstitialId) {
        IronSource.getAdvertiserId(context);
        //Network Connectivity Status
        IronSource.shouldTrackNetworkState(context, true);
        AudienceNetworkAds.initialize(context);

        IronSource.init(context, interstitialId);
        IronSource.loadInterstitial();
        IronSource.setMetaData("Facebook_IS_CacheFlag", "IMAGE");
        IronSource.showInterstitial();
        IronSource.setInterstitialListener(new InterstitialListener() {
            @Override
            public void onInterstitialAdReady() {
                IronSource.showInterstitial();

            }

            @Override
            public void onInterstitialAdLoadFailed(IronSourceError ironSourceError) {

                Log.d(TAG, ironSourceError.getErrorMessage());
            }

            @Override
            public void onInterstitialAdOpened() {
            }

            @Override
            public void onInterstitialAdClosed() {

            }

            @Override
            public void onInterstitialAdShowSucceeded() {

            }

            @Override
            public void onInterstitialAdShowFailed(IronSourceError ironSourceError) {

            }

            @Override
            public void onInterstitialAdClicked() {

            }
        });
    }


    public void metaInterstitialAd(Activity context, String interstitialId) {
        AudienceNetworkAds.initialize(context);
        com.facebook.ads.InterstitialAd interstitialAd = new com.facebook.ads.InterstitialAd(context, interstitialId);
        // Create listeners for the Interstitial Ad
        InterstitialAdListener interstitialAdListener = new InterstitialAdListener() {
            @Override
            public void onInterstitialDisplayed(Ad ad) {
                // Interstitial ad displayed callback
                Log.e(TAG, "Interstitial ad displayed.");
            }

            @Override
            public void onInterstitialDismissed(Ad ad) {
                // Interstitial dismissed callback
                Log.e(TAG, "Interstitial ad dismissed.");
            }

            @Override
            public void onError(Ad ad, AdError adError) {
                // Ad error callback
                Log.e(TAG, "Interstitial ad failed to load: " + adError.getErrorMessage());
            }

            @Override
            public void onAdLoaded(Ad ad) {
                // Interstitial ad is loaded and ready to be displayed
                Log.d(TAG, "Interstitial ad is loaded and ready to be displayed!");
                // Show the ad
                interstitialAd.show();
            }

            @Override
            public void onAdClicked(Ad ad) {
                // Ad clicked callback
                Log.d(TAG, "Interstitial ad clicked!");
            }

            @Override
            public void onLoggingImpression(Ad ad) {
                // Ad impression logged callback
                Log.d(TAG, "Interstitial ad impression logged!");
            }
        };

        // For auto play video ads, it's recommended to load the ad
        // at least 30 seconds before it is shown
        interstitialAd.loadAd(
                interstitialAd.buildLoadAdConfig()
                        .withAdListener(interstitialAdListener)
                        .build());
    }


    // maxAds
    @Override
    public void onAdExpanded(MaxAd ad) {

    }

    @Override
    public void onAdCollapsed(MaxAd ad) {

    }

    @Override
    public void onAdLoaded(MaxAd ad) {
        retryAttempt = 0;
        maxAdView.setVisibility(View.VISIBLE);

    }

    @Override
    public void onAdDisplayed(MaxAd ad) {

    }

    @Override
    public void onAdHidden(MaxAd ad) {

    }

    @Override
    public void onAdClicked(MaxAd ad) {

    }

    @Override
    public void onAdLoadFailed(String adUnitId, MaxError error) {
        Log.d(TAG, "applovin" + error.getMessage());
        retryAttempt++;
        long delayMillis = TimeUnit.SECONDS.toMillis((long) Math.pow(2, Math.min(6, retryAttempt)));

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (interstitialAd != null) {

                    interstitialAd.loadAd();
                }
            }
        }, delayMillis);
    }

    @Override
    public void onAdDisplayFailed(MaxAd ad, MaxError error) {
        if (interstitialAd != null) {
            interstitialAd.loadAd();
        }
    }

}
