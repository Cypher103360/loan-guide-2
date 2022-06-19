package com.dreamteam.teamprediction.fordreamteamcricket.models;

public class LoanModel {
    String id,images,userName,loanName,loanAmount;

    public LoanModel(String id, String images, String userName, String loanName, String loanAmount) {
        this.id = id;
        this.images = images;
        this.userName = userName;
        this.loanName = loanName;
        this.loanAmount = loanAmount;
    }

    public String getId() {
        return id;
    }

    public String getImages() {
        return images;
    }

    public String getUserName() {
        return userName;
    }

    public String getLoanName() {
        return loanName;
    }

    public String getLoanAmount() {
        return loanAmount;
    }
}
