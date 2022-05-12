package com.instantloanguide.allloantips.models;

import java.util.List;

public class UrlModelList {
    List<UrlModel> data = null;

    public UrlModelList(List<UrlModel> data) {
        this.data = data;
    }

    public List<UrlModel> getData() {
        return data;
    }
}
