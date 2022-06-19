package com.dreamteam.teamprediction.fordreamteamcricket.models;

import java.util.List;

public class LoanModelList {
    private List<LoanModel> data = null;

    public LoanModelList(List<LoanModel> data) {
        this.data = data;
    }

    public List<LoanModel> getData() {
        return data;
    }
}
