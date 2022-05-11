package com.instantloanguide.loanguideadmin.viewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class LoanAppModelFactory implements ViewModelProvider.Factory {

    String id;
    Application application;

    public LoanAppModelFactory(String id, Application application) {
        this.id = id;
        this.application = application;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new LoanAppViewModel(application, id);
    }
}
