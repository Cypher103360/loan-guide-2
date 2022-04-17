package com.instantloanguide.allloantips.models;

import com.google.gson.annotations.SerializedName;

public class AdsModel {
    String id, admobAppKey, appLovinAppKey, appOpen, admobBanner, banner, interstitial, networkName;
    @SerializedName("native")
    String nativeADs;

    public AdsModel(String id, String admobAppKey, String appLovinAppKey, String appOpen, String admobBanner, String banner, String interstitial, String networkName, String nativeADs) {
        this.id = id;
        this.admobAppKey = admobAppKey;
        this.appLovinAppKey = appLovinAppKey;
        this.appOpen = appOpen;
        this.admobBanner = admobBanner;
        this.banner = banner;
        this.interstitial = interstitial;
        this.networkName = networkName;
        this.nativeADs = nativeADs;
    }

    public String getId() {
        return id;
    }

    public String getAdmobAppKey() {
        return admobAppKey;
    }

    public String getAppLovinAppKey() {
        return appLovinAppKey;
    }

    public String getAppOpen() {
        return appOpen;
    }

    public String getAdmobBanner() {
        return admobBanner;
    }

    public String getBanner() {
        return banner;
    }

    public String getInterstitial() {
        return interstitial;
    }

    public String getNetworkName() {
        return networkName;
    }

    public String getNativeADs() {
        return nativeADs;
    }
}
