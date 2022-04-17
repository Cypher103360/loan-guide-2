package com.instantloanguide.allloantips.utils;

import static android.content.ContentValues.TAG;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.instantloanguide.allloantips.activities.HomeActivity;
import com.instantloanguide.allloantips.activities.WelcomeActivity;
import com.instantloanguide.allloantips.models.AdsModel;
import com.instantloanguide.allloantips.models.AdsModelList;
import com.instantloanguide.allloantips.models.ApiInterface;
import com.instantloanguide.allloantips.models.ApiWebServices;
import com.onesignal.OSNotificationOpenedResult;
import com.onesignal.OneSignal;

import java.util.Objects;

import io.paperdb.Paper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyApp extends Application {

    private static final String ONESIGNAL_APP_ID = "55aee219-b5fb-4e0a-9218-3a164aa7b74a";
    public static MyApp mInstance;
    public static String id;
    ApiInterface apiInterface;

    public MyApp() {
        mInstance = this;
    }

    public static void showAdmobBannerAd(Context context, RelativeLayout container) {
        MobileAds.initialize(context);

        id = Paper.book().read(Prevalent.admobBannerAds);
        AdRequest adRequest = new AdRequest.Builder().build();
        AdView adView = new AdView(context);
        container.addView(adView);
        adView.setAdUnitId(id);
        adView.setAdSize(AdSize.BANNER);
        adView.loadAd(adRequest);
        container.setVisibility(View.VISIBLE);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        Paper.init(mInstance);
        fetchAds();
        OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE);
        // OneSignal Initialization
        OneSignal.initWithContext(this);
        OneSignal.setNotificationOpenedHandler(new ExampleNotificationOpenedHandler());
        OneSignal.setAppId(ONESIGNAL_APP_ID);
    }

    private void fetchAds() {
        apiInterface = ApiWebServices.getApiInterface();
        Call<AdsModelList> call = apiInterface.fetchAds("LoanGuide");
        call.enqueue(new Callback<AdsModelList>() {
            @Override
            public void onResponse(@NonNull Call<AdsModelList> call, @NonNull Response<AdsModelList> response) {
                if (response.isSuccessful()) {
                    if (Objects.requireNonNull(response.body()).getData() != null) {
                        for (AdsModel ads : response.body().getData()) {
                            Log.e("adsId", ads.getId() + ads.getNetworkName());

                            Paper.book().write(Prevalent.appId, ads.getAdmobAppKey());
                            Paper.book().write(Prevalent.appLovinId, ads.getAppLovinAppKey());
                            Paper.book().write(Prevalent.openAppAds, ads.getAppOpen());
                            Paper.book().write(Prevalent.admobBannerAds, ads.getAdmobBanner());
                            Paper.book().write(Prevalent.nativeAds, ads.getNativeADs());
                            Paper.book().write(Prevalent.bannerAds, ads.getBanner());
                            Paper.book().write(Prevalent.interstitialAds, ads.getInterstitial());
                            Paper.book().write(Prevalent.networkName, ads.getNetworkName());
                            new AppOpenManager(mInstance, Paper.book().read(Prevalent.openAppAds), mInstance);

                            try {
                                ApplicationInfo ai = getPackageManager().getApplicationInfo(getPackageName(), PackageManager.GET_META_DATA);
                                Bundle bundle = ai.metaData;
                                String myApiKey = bundle.getString("com.google.android.gms.ads.APPLICATION_ID");
                                Log.d(TAG, "Name Found: " + myApiKey);
                                ai.metaData.putString("com.google.android.gms.ads.APPLICATION_ID", Paper.book().read(Prevalent.appId));//you can replace your key APPLICATION_ID here
                                String ApiKey = bundle.getString("com.google.android.gms.ads.APPLICATION_ID");
                                Log.d(TAG, "ReNamed Found: " + ApiKey);
                            } catch (PackageManager.NameNotFoundException e) {
                                Log.e(TAG, "Failed to load meta-data, NameNotFound: " + e.getMessage());
                            } catch (NullPointerException e) {
                                Log.e(TAG, "Failed to load meta-data, NullPointer: " + e.getMessage());
                            }

                            try {
                                ApplicationInfo ai = getPackageManager().getApplicationInfo(getPackageName(), PackageManager.GET_META_DATA);
                                Bundle bundle = ai.metaData;
                                String myApiKey = bundle.getString("applovin.sdk.key");
                                Log.d(TAG, "Name Found: " + myApiKey);
                                ai.metaData.putString("applovin.sdk.key", Paper.book().read(Prevalent.appLovinId));     //you can replace your key APPLICATION_ID here
                                String ApiKey = bundle.getString("applovin.sdk.key");
                                Log.d(TAG, "ReNamed Found: " + ApiKey);
                            } catch (PackageManager.NameNotFoundException e) {
                                Log.e(TAG, "Failed to load meta-data, NameNotFound: " + e.getMessage());
                            } catch (NullPointerException e) {
                                Log.e(TAG, "Failed to load meta-data, NullPointer: " + e.getMessage());
                            }


                        }
                    }
                } else {
                    Log.e("adsError", response.message());
                }

            }

            @Override
            public void onFailure(@NonNull Call<AdsModelList> call, @NonNull Throwable t) {
                Log.d("adsError", t.getMessage());
            }
        });
    }

    public void intent() {
        if (!AppOpenManager.isIsShowingAd) {
            Intent intent = new Intent(getApplicationContext(), WelcomeActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            AppOpenManager.isIsShowingAd = false;
        }
    }

    private class ExampleNotificationOpenedHandler implements OneSignal.OSNotificationOpenedHandler {
        @Override
        public void notificationOpened(OSNotificationOpenedResult result) {
            Intent intent = new Intent(MyApp.this, HomeActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);

        }
    }
}
