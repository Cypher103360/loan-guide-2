package com.instantloanguide.allloantips.models;

import java.util.List;

public class LoanAppModelList {

    public List<LoanAppModel> data = null;

    public LoanAppModelList(List<LoanAppModel> data) {
        this.data = data;
    }

    public List<LoanAppModel> getData() {
        return data;
    }
}
