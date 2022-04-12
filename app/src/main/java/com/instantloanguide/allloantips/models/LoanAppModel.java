package com.instantloanguide.allloantips.models;

public class LoanAppModel {
    String id;
    int appImage;
    String appName;
    String interest,amount,age,requirememt;

    public LoanAppModel(String id, int appImage, String appName, String interest, String amount, String age, String requirememt) {
        this.id = id;
        this.appImage = appImage;
        this.appName = appName;
        this.interest = interest;
        this.amount = amount;
        this.age = age;
        this.requirememt = requirememt;
    }

    public String getId() {
        return id;
    }

    public int getAppImage() {
        return appImage;
    }

    public String getAppName() {
        return appName;
    }

    public String getInterest() {
        return interest;
    }

    public String getAmount() {
        return amount;
    }

    public String getAge() {
        return age;
    }

    public String getRequirememt() {
        return requirememt;
    }
}
