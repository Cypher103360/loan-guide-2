package com.instantloanguide.loanguideadmin.models;

public class NewsModel {

    String id, newsImg, newsTitle, newsEngDesc, newsHinDesc;

    public NewsModel(String id, String newsImg, String newsTitle, String newsEngDesc, String newsHinDesc) {
        this.id = id;
        this.newsImg = newsImg;
        this.newsTitle = newsTitle;
        this.newsEngDesc = newsEngDesc;
        this.newsHinDesc = newsHinDesc;
    }

    public String getId() {
        return id;
    }

    public String getNewsImg() {
        return newsImg;
    }

    public String getNewsTitle() {
        return newsTitle;
    }

    public String getNewsEngDesc() {
        return newsEngDesc;
    }

    public String getNewsHinDesc() {
        return newsHinDesc;
    }
}
