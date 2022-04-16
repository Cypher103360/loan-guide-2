package com.instantloanguide.allloantips.models;

public class TipsModel {

    String id, tipsTitle,tipsEngDesc,tipsHinDesc;

    public TipsModel(String id, String tipsTitle, String tipsEngDesc, String tipsHinDesc) {
        this.id = id;
        this.tipsTitle = tipsTitle;
        this.tipsEngDesc = tipsEngDesc;
        this.tipsHinDesc = tipsHinDesc;
    }

    public String getId() {
        return id;
    }

    public String getTipsTitle() {
        return tipsTitle;
    }

    public String getTipsEngDesc() {
        return tipsEngDesc;
    }

    public String getTipsHinDesc() {
        return tipsHinDesc;
    }
}
