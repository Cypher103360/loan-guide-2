package com.instantloanguide.allloantips.models;

import java.util.List;

public class TipsModelList {

    List<TipsModel> data =null;

    public TipsModelList(List<TipsModel> data) {
        this.data = data;
    }

    public List<TipsModel> getData() {
        return data;
    }
}
