package com.dreamteam.teamprediction.fordreamteamcricket.models;

public class NewsModel {

    String id, newsImg, newsTitle, newsEngTitle, url, newsEngDesc, newsHinDesc;

    public NewsModel(String id, String newsImg, String newsTitle, String newsEngTitle, String url, String newsEngDesc, String newsHinDesc) {
        this.id = id;
        this.newsImg = newsImg;
        this.newsTitle = newsTitle;
        this.newsEngTitle = newsEngTitle;
        this.url = url;
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

    public String getNewsEngTitle() {
        return newsEngTitle;
    }

    public String getUrl() {
        return url;
    }

    public String getNewsEngDesc() {
        return newsEngDesc;
    }

    public String getNewsHinDesc() {
        return newsHinDesc;
    }
}
