package com.dreamteam.teamprediction.fordreamteamcricket.models;

public class TipsModel {

    String id, tipsTitle,tipsEngTitle,tipsUrl,tipsEngDesc,tipsHinDesc;

    public TipsModel(String id, String tipsTitle, String tipsEngTitle, String tipsUrl, String tipsEngDesc, String tipsHinDesc) {
        this.id = id;
        this.tipsTitle = tipsTitle;
        this.tipsEngTitle = tipsEngTitle;
        this.tipsUrl = tipsUrl;
        this.tipsEngDesc = tipsEngDesc;
        this.tipsHinDesc = tipsHinDesc;
    }

    public String getId() {
        return id;
    }

    public String getTipsTitle() {
        return tipsTitle;
    }

    public String getTipsEngTitle() {
        return tipsEngTitle;
    }

    public String getTipsUrl() {
        return tipsUrl;
    }

    public String getTipsEngDesc() {
        return tipsEngDesc;
    }

    public String getTipsHinDesc() {
        return tipsHinDesc;
    }
}
