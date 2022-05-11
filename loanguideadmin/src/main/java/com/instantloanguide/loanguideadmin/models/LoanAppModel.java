package com.instantloanguide.loanguideadmin.models;

public class LoanAppModel {

    String id, loanId, img, title, interest, amount, age, requirement, url;

    public LoanAppModel(String id, String loanId, String img, String title, String interest, String amount, String age, String requirement, String url) {
        this.id = id;
        this.loanId = loanId;
        this.img = img;
        this.title = title;
        this.interest = interest;
        this.amount = amount;
        this.age = age;
        this.requirement = requirement;
        this.url = url;
    }

    public String getId() {
        return id;
    }

    public String getLoanId() {
        return loanId;
    }

    public String getImg() {
        return img;
    }

    public String getTitle() {
        return title;
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

    public String getRequirement() {
        return requirement;
    }

    public String getUrl() {
        return url;
    }
}
